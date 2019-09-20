package com.nested;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nested.Status;
import com.nested.User;

@RestController
@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class AppController {

	@Autowired
	PostRepository postRepo;

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	LikeRepository likeRepo;

	@Autowired
	UsersRepository userRepo;

	// SignUp, Signin, Signout,

	@PostMapping("/signup")
	public User signUp(HttpServletRequest req, @RequestBody User user) {

		User result = null;
		try {
			result = userRepo.save(user);

			if (result == null)
				return null;
			HttpSession session = req.getSession(true);
			session.setAttribute("userId", result.getId());
			session.setAttribute("userName", result.getEmpName());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/signin")
	public User signIn(HttpServletRequest req, @RequestBody User user) {
		User temp = userRepo.findById((int) user.getId());

		if (temp == null)
			return null;

		if (temp.getPassword().equals(user.getPassword())) {
			HttpSession session = req.getSession(true);
			session.setAttribute("userId", temp.getId());
			session.setAttribute("userName", temp.getEmpName());
			return temp;
		}
		return null;
	}

	@PostMapping("/signout")
	public Status logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session != null && session.getAttribute("userId") != null) {
			session.invalidate();
			return new Status(true);
		}
		return new Status(false);
	}

	// View All Post/Comment/Like, My Post/Comment/Like

	@GetMapping("/view")
	public List<Post> getAllposts(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;
		List<Post> posts = new ArrayList<Post>();
		Iterable<Post> iterable = postRepo.findAll();
		for (Post post : iterable) {
			posts.add(post);
		}
		return posts;
	}

	@GetMapping("/myposts/view")
	public List<Post> getMyposts(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return null;
		}
		int id = (int) session.getAttribute("userId");
		List<Post> posts = postRepo.findByUser(new User(id, "", "", "", 0, ""));
		return posts;
	}

	// Save Post, Comment, Like

	@PostMapping("/post/save")
	public Post savePost(HttpServletRequest req, @RequestBody Post post) {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;

		if (session.getAttribute("userId").equals(post.getUfk())) {
			post.setUser(new User());
			post.getUser().setId(post.getUfk());
			post.setPostedBy((String) session.getAttribute("userName"));
			return postRepo.save(post);
		}
		return null;
	}

	@PostMapping("/comment/save")
	public Comment saveComment(HttpServletRequest req, @RequestBody Comment comment) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;

		comment.setPost(new Post());
		comment.getPost().setPostId(comment.getFk());
		comment.setCommentedBy((String) session.getAttribute("userName"));
		return commentRepo.save(comment);
	}

	@PostMapping("/like/save")
	public Like saveLike(HttpServletRequest req, @RequestBody Like like) {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;

		like.setPost(new Post());
		like.getPost().setPostId(like.getPfk());
		like.setLikedBy((String) session.getAttribute("userName"));
		return likeRepo.save(like);
	}

	// Edit Post, Comment

	@PutMapping("/post/edit")
	public Post editPost(HttpServletRequest req, @RequestBody Post post) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;

		if (session.getAttribute("userId").equals(post.getUfk())) {
			post.setUser(new User());
			post.getUser().setId(post.getUfk());
			return postRepo.save(post);
		}
		return null;
	}

	@PutMapping("/comment/edit")
	public Comment editComment(HttpServletRequest req, @RequestBody Comment comment) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;
		String uname = (String) session.getAttribute("userName");
		if (uname.equals(comment.getCommentedBy())) {
			comment.setPost(new Post());
			comment.getPost().setPostId(comment.getFk());
			return commentRepo.save(comment);
		}

		return null;
	}

	// Delete Post, Comment, Like

	@DeleteMapping("/post/delete/{postId}")
	public Status deletePost(HttpServletRequest req, @PathVariable Integer postId) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return new Status(false);

		Post post = postRepo.findByPostId(postId);

		if (session.getAttribute("userId").equals(post.getUser().getId())) {

			List<Comment> comments = commentRepo.findByPostPostId(postId);
			for (Comment comment : comments) {
				commentRepo.delete(new Comment(comment.getCommentId(), 0, "", null, ""));
			}

			List<Like> likes = likeRepo.findByPostPostId(postId);
			for (Like like : likes) {
				likeRepo.delete(new Like(like.getLikeId(), 0, null, ""));
			}
			postRepo.delete(new Post(postId, "", "", 0, null));
			return new Status(true);
		}
		return new Status(false);
	}

	@DeleteMapping("comment/delete/{commentId}")
	public Status deleteComment(HttpServletRequest req, @PathVariable int commentId) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return new Status(false);

		Comment comment = commentRepo.findByCommentId(commentId);

		if (session.getAttribute("userName").equals(comment.getCommentedBy())) {
			commentRepo.delete(new Comment(commentId, 0, "", null, ""));
			return new Status(true);
		}
		return new Status(false);
	}

	@DeleteMapping("like/delete/{likeId}")
	public Status deletePost1(HttpServletRequest req, @PathVariable int likeId) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return new Status(false);

		Like like = likeRepo.findByLikeId(likeId);

		if (session.getAttribute("userName").equals(like.getLikedBy())) {
			likeRepo.delete(new Like(likeId, 0, null, ""));
			return new Status(true);
		}
		return new Status(false);
	}

	// Search By Title, userName

	@GetMapping("/find/title/{title}")
	public List<Post> findTitle(HttpServletRequest req, @PathVariable String title) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return null;
		}
		List<Post> posts = postRepo.findByTitleContaining(title);
		return posts;
	}

	@GetMapping("/find/body/{body}")
	public List<Post> findBody(HttpServletRequest req, @PathVariable String body) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return null;
		}
		List<Post> posts = postRepo.findByBodyContaining(body);
		return posts;
	}

	// Display User Profile & Search By UserName

	@GetMapping("/find/user/{empName}")
	public User findUser(HttpServletRequest req, @PathVariable String empName) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return null;
		}
		User user = userRepo.findByEmpName(empName);
		return user;
	}

	// Editing User Profile

	@PostMapping("/profile/edit")
	public User editProfile(HttpServletRequest req, @RequestBody User user) {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null)
			return null;

		if (session.getAttribute("userId").equals(user.getId())) {
			User temp = userRepo.findById((int) user.getId());
			temp.setPassword(user.getPassword());
			temp.setEmail(user.getEmail());
			temp.setPhone(user.getPhone());
			return userRepo.save(temp);
		}
		return null;
	}

	@PostMapping("/forgotPassword")
	public User ForgotPassword(@RequestBody User user) {
		User temp = userRepo.findById((int) user.getId());
		if (temp == null||(temp.getPassword().equals(user.getPassword())))
			return null;
		
		if((temp.getMessage()).equals(user.getMessage()))
			{
			temp.setPassword(user.getPassword());
			return userRepo.save(temp);
			}
		return null;
	}

	@GetMapping("/likesCount/{postId}")
	public Long findLikesCount(HttpServletRequest req, @PathVariable Integer postId) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			return 0L;
		}
		return likeRepo.countByPost(new Post(postId, "", "", 0, null));

	}

}
