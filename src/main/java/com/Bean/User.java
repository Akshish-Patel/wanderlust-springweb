package com.Bean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertFalse.List;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	@Column(unique = true)
	private String email;
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private java.util.List<Listing> listingList;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private java.util.List<Review> reviewList;
	
	
	public java.util.List<Review> getReviewList() {
		return reviewList;
	}
	public void setReviewList(java.util.List<Review> reviewList) {
		this.reviewList = reviewList;
	}
	public java.util.List<Listing> getListingList() {
		return listingList;
	}
	public void setListingList(java.util.List<Listing> listingList) {
		this.listingList = listingList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
