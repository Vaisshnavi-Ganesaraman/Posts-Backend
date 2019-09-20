package com.nested;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer likeId;

	@Transient
	private Integer pfk;

	@OneToOne
	private Post post;
	
	private String likedBy;

	public Like() {

	}

	public String getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(String likedBy) {
		this.likedBy = likedBy;
	}

	public Integer getLikeId() {
		return likeId;
	}

	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}

	public Integer getPfk() {
		return pfk;
	}

	public void setPfk(Integer pfk) {
		this.pfk = pfk;
	}

	@JsonIgnore
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Like(Integer likeId, Integer pfk, Post post, String likedBy) {
		super();
		this.likeId = likeId;
		this.pfk = pfk;
		this.post = post;
		this.likedBy=likedBy;
	}

	@Override
	public String toString() {
		return "Like [likeId=" + likeId + ", pfk=" + pfk + ", likedBy=" + likedBy + "]";
	}

}
