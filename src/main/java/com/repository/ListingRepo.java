package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bean.Listing;

public interface ListingRepo extends JpaRepository<Listing, Integer>{

}
