package com.exam.test_api.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.exam.test_api.model.User;
import com.exam.test_api.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public void registeUser(User user) {
		if (userRepository.findByUsername(user.getUsername()) != null){
			userRepository.save(user);
		}
	}
	
	public boolean deleteUserById(Long id) {		
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Optional<User> retrieveUserById(Long id){
		return userRepository.findById(id);
	}
	
	public Optional<User> retriveUserByUserName(String username){
		return userRepository.findByUsername(username);
	}
	
	public List<User> retrieveAllUsers(){
		
		return userRepository.findAll();
	}
	
	public boolean createUser(User user) {
			
		boolean userExists = userRepository.existsByUsernameIgnoreCase(user.getUsername());
		
		if(!userExists) {
			userRepository.save(user);
			return true;
		}
		return false;
	}
	



}
