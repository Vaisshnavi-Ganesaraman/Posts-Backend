package com.nested;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Integer> {

	List<Post> findByUser(User user);

	Post findByPostId(int id);

	@Query("select p from posts p where p.title like %:title%") 
	List<Post> findByTitleContaining(@Param("title") String title);
	
	@Query("select p from posts p where p.body like %:body%") 
	List<Post> findByBodyContaining(@Param("body") String body);


}
