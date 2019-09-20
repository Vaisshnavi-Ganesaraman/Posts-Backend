package com.nested;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
	private String title;
	private String body;	
	
	private String postedBy;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Comment> comments;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Like> likes;

	@Transient
	private Integer ufk;

	@ManyToOne(cascade = CascadeType.REMOVE)
	private User user;

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post() {

	}

	public int getPostId() {
		return postId;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public Post(Integer postId, String title,String postedBy, String body, List<Comment> comments, List<Like> likes, Integer ufk, User user) {
		this.postId = postId;
		this.title = title;
		this.body = body;
		this.postedBy=postedBy;
		this.comments = comments;
		this.ufk = ufk;
		this.user = user;
		this.likes=likes;
	}

	public String getBody() {
		return body;
	}

	public Post(Integer postId, String title, String body, Integer ufk, User user) {
		this.postId = postId;
		this.title = title;
		this.body = body;
		this.ufk = ufk;
		this.user = user;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Integer getUfk() {
		return ufk;
	}

	public void setUfk(Integer ufk) {
		this.ufk = ufk;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + ", body=" + body + ", ufk=" + ufk + "]";
	}

}