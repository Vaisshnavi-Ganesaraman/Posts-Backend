package com.nested;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Boolean> {

	Like findByLikeId(Integer likeId);

	Long countByPost(Post post);

	List<Like> findByPostPostId(Integer postId);

}
