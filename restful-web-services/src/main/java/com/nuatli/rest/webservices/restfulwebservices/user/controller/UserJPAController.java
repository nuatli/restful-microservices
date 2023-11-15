package com.nuatli.rest.webservices.restfulwebservices.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nuatli.rest.webservices.restfulwebservices.post.Post;
import com.nuatli.rest.webservices.restfulwebservices.post.PostRepository;
import com.nuatli.rest.webservices.restfulwebservices.user.User;
import com.nuatli.rest.webservices.restfulwebservices.user.UserNotFoundException;
import com.nuatli.rest.webservices.restfulwebservices.user.UserRepository;

@RestController
public class UserJPAController {
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	public UserJPAController(UserRepository userRepository,PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	/**  USERS  **/
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) { //Java 11'de user.isEmpty() kullanılacak
			
			throw new UserNotFoundException("id: "+id);
		}
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users") //Oluşturulan userın locationı headerda veriliyor.
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
										   .path("/{id}")
										   .buildAndExpand(savedUser.getId())
										   .toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	
	/**  POSTS  **/
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveUserPosts(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) { //Java 11'de user.isEmpty() kullanılacak
			
			throw new UserNotFoundException("id: "+id);
		}
		
		return user.get().getPosts();
		
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> createUserPosts(@PathVariable int id,@Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) { //Java 11'de user.isEmpty() kullanılacak
			
			throw new UserNotFoundException("id: "+id);
		}
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				   .path("/{id}")
				   .buildAndExpand(savedPost.getId())
				   .toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	
}
