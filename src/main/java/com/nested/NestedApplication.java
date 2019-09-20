package com.nested;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class NestedApplication {

	public static void main(String[] args) {
		SpringApplication.run(NestedApplication.class, args);
	}

	@SuppressWarnings("deprecation")
	@Bean
	public WebMvcConfigurerAdapter corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			  public void addCorsMappings(CorsRegistry registry) {
 
				registry.addMapping("/signin").allowedOrigins("http://localhost:4200");
				registry.addMapping("/signin").allowCredentials(true);
			
				registry.addMapping("/signup").allowedOrigins("http://localhost:4200");
				registry.addMapping("/signup").allowCredentials(true);
		
				registry.addMapping("/signout").allowedOrigins("http://localhost:4200");
				registry.addMapping("/signout").allowCredentials(true);
			
				registry.addMapping("/post/save").allowedOrigins("http://localhost:4200");
				registry.addMapping("/post/save").allowCredentials(true);
				
				registry.addMapping("/comment/save").allowedOrigins("http://localhost:4200");
				registry.addMapping("/comment/save").allowCredentials(true);
				
				registry.addMapping("/like/save").allowedOrigins("http://localhost:4200");
				registry.addMapping("/like/save").allowCredentials(true);
				
				registry.addMapping("/post/edit").allowedOrigins("*");
				registry.addMapping("/post/edit").allowCredentials(true);
				
				registry.addMapping("/comment/edit").allowedOrigins("http://localhost:4200");
				registry.addMapping("/comment/edit").allowCredentials(true);
				
				registry.addMapping("/profile/edit").allowedOrigins("http://localhost:4200");
				registry.addMapping("/profile/edit").allowCredentials(true);
				
				registry.addMapping("/view").allowedOrigins("http://localhost:4200");
				registry.addMapping("/view").allowCredentials(true);
				
				registry.addMapping("/myposts/view").allowedOrigins("http://localhost:4200");
				registry.addMapping("/myposts/view").allowCredentials(true);
				
				registry.addMapping("/find/title/{title}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/find/title/{title}").allowCredentials(true);
				
				registry.addMapping("/find/body/{body}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/find/body/{body}").allowCredentials(true);
				
				registry.addMapping("/find/user/{empName}").allowedOrigins("*");
				registry.addMapping("/find/user/{empName}").allowCredentials(true);
				
				registry.addMapping("/forgotPassword").allowedOrigins("http://localhost:4200");
				registry.addMapping("/forgotPassword").allowCredentials(true);
				
				registry.addMapping("/post/delete/{postId}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/post/delete/{postId}").allowCredentials(true);
				
				registry.addMapping("/comment/delete/{commentId}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/comment/delete/{commentId}").allowCredentials(true);
				
				registry.addMapping("/like/delete/{likeId}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/like/delete/{likeId}").allowCredentials(true);
				
				registry.addMapping("/likesCount/{postId}").allowedOrigins("http://localhost:4200");
				registry.addMapping("/likesCount/{postId}").allowCredentials(true);
				
			  }
		};
	}
	
}
