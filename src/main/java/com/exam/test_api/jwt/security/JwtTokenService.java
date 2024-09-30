package com.exam.test_api.jwt.security;

import java.time.Instant;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;



@Service
public class JwtTokenService {
	
	@Autowired
	JwtEncoder jwtEncoder;
	
	public String createToken(Authentication authentication) {
		var jwtClaimSet = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(30 * 30))
				.subject(authentication.getName())
				.claim("scope", createScope(authentication))
				.build();
		
		JwtEncoderParameters parameters = JwtEncoderParameters.from(jwtClaimSet);
		return jwtEncoder.encode(parameters).getTokenValue();
	}

	public String createScope(Authentication authentication) {
		
		return authentication
				.getAuthorities()
				.stream()
				.map(a -> a.getAuthority())
				.collect(Collectors.joining(" "));
	}	

	public record JwtResponse(String token) {}

	public record JwtTokenRequest(String username, String password) {}

}



