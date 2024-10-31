package com.config;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Bean.User;
import com.repository.UserRepo;


@Component
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		logic created by us
		User user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}else {			
			return new customUser(user);
		}
	}

}
