package com.school.controller;

import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONFilter;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.dto.AuthenticationRequest;
import com.school.dto.AuthenticationResponse;
import com.school.jwtFilter.JwtService;
import com.school.model.User;
import com.school.repositories.UserRepository;
import com.school.services.AdminServiceImpl;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class AuthenticationController {
	
// 1 Method***************************
//		@Autowired
//		private AdminServiceImpl adminServiceImpl;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	
	
	
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	
	@GetMapping("/Login")
	public String getData() {
		
		return "Hello";
	}
	
	
// 1 Method***************************
	
//	@PostMapping("/admin")
//	public String createAuthenticationToken(@RequestBody User user) {
//		 
//		
//		return adminServiceImpl.verify(user);
//	}
	

// 2 Method***************************
	
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response)throws IOException, JSONException{
		
		try {
		
			// it will verify that user is valid or not 
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		
		}catch(BadCredentialsException e) {
			
			throw new BadCredentialsException("Incorrect Username and password");
		
		} catch (DisabledException e) {
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not created");
			return;
		}
		
		
		// In userDetails we store email as userDetails
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		
		// set token into header and store into local storage
		Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
		
		//After validation it will generate token.
		final String jwt = jwtService.generateToken(userDetails.getUsername());
		
		// set token into header and store into local storage
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId())
														.put("role", optionalUser.get().getRole()) 
														.toString());
		}
		
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,X-Pingother, Origin,X-Requested-With, Content-Type,Accept, X-Custom-header");
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		
//		return new AuthenticationResponse(jwt);  return type=> AuthenticationResponse
	}
	

}
