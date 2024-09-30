package com.exam.test_api.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test_api.jwt.security.JwtTokenService.JwtResponse;
import com.exam.test_api.jwt.security.JwtTokenService.JwtTokenRequest;

@RestController
public class JwtAuthenticationResource {

	private AuthenticationManager authenticationManager;
	
	private JwtTokenService jwtTokenService;
	
	public JwtAuthenticationResource(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
	}

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtTokenRequest jwtTokenRequest) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(jwtTokenRequest.username(),
				jwtTokenRequest.password());

		var authentication = authenticationManager.authenticate(authenticationToken);
		var token = jwtTokenService.createToken(authentication);
		return new JwtResponse(token);
	}
}
