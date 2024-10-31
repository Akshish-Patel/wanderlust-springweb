package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bean.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);
	public User findByEmail(String email);

}
