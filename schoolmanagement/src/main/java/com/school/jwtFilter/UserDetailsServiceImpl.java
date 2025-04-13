package com.school.jwtFilter;



import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.model.User;
import com.school.repositories.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// write logic to get user from DB
		Optional<User> userOptional = userRepository.findByEmail(email);
		
		if(userOptional.isEmpty()) {
			
//			System.out.println("User Not Found");
			throw new UsernameNotFoundException("User Not Found");
		}
		
		
		return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),userOptional.get().getPassword(),new ArrayList<>());
	
			
	}
	


}

