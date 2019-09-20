package com.nested;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	Post findByPost(Post post);

	Comment findByCommentId(int commentId);

	List<Comment> findByPostPostId(Integer postId);

}
