package com.exam.test_api.controllers;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test_api.model.User;
import com.exam.test_api.services.UserService;

@RestController
public class UserController {

	private UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/")
	public String home() {
		return "Welcome! this is a test api";
	}
	
	@GetMapping("/api/user/{id}")
	public ResponseEntity<Optional<User>>  retriveUserbyId(@PathVariable Long id){
		
		Optional<User> user = userService.retrieveUserById(id);
		
		if(user.isPresent()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("/api/retrive-userid/{username}")
	public ResponseEntity<Long> retrieveUserByUsername(@PathVariable String username){
		Optional<User> user = userService.retriveUserByUserName(username);
		if(user.isPresent()) {
			return new ResponseEntity<>(user.get().getId(), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@GetMapping("/api/user")
	public ResponseEntity<List<User>> retriveAllUsers() {
		
		try {
			return new ResponseEntity<>(userService.retrieveAllUsers(), HttpStatus.OK);			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/api/create-user")
	public ResponseEntity<?> createUser(@RequestBody User user){
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		if(userService.createUser(user)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
		}
	}
	
	
	@DeleteMapping("/api/user/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
		if(userService.deleteUserById(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

}
