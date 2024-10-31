package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bean.Review;

public interface ReviewRepo extends JpaRepository<Review, String>  
{

}
