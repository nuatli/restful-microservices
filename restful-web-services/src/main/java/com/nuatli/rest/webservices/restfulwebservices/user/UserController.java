package com.nuatli.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	private UserDaoService service;
	
	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id){
		return service.findUserWithId(id);
	}
	
	/* Basit
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		service.save(user);
		
		return ResponseEntity.created(null).build();
	}
	*/
	
	@PostMapping("/users") //Oluşturulan userın locationı headerda veriliyor.
	public ResponseEntity<User> createUser(@RequestBody User user){
		User savedUser = service.save(user);
		URI location = 
				ServletUriComponentsBuilder.fromCurrentRequest()
										   .path("/{id}")
										   .buildAndExpand(savedUser.getId())
										   .toUri();
		return ResponseEntity.created(location).build();
	}
	
}
