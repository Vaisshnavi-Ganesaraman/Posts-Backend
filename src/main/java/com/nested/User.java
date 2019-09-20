package com.nested;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "users")
public class User {

	@Id
	@Column(unique = true)
	private Integer id;

	@Column(unique = true)
	private String empName;

	private String password;
	
	private String message;

	@NotNull
	@Email
	@Size(max = 30)
	@Column(unique = true)
	private String email;

	
	@NotNull
	@Column(name = "phone", unique = true)
	private Integer phone;
	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;

	public User() {

	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

//	@JsonIgnore
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public User(Integer id, String empName, String password, @NotNull @Email @Size(max = 30) String email,
			@NotNull Integer phone,String message) {
		this.id = id;
		this.empName = empName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.message=message;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", empName=" + empName + ", password=" + password + ", email=" + email + ", phone="
				+ phone + "posts=" +posts +"]";
	}

}
