package com.Bean;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Review 
{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String reviewId;
	
	@NotBlank(message = "Comment is Reqired!!")
	private String comment;
	
	@jakarta.validation.constraints.NotNull(message = "Rating is Reqired!!")
	private Integer rating;
	
	private Date createdAt = new Date();

	@ManyToOne
	@JoinColumn(name = "listing_id")
	private Listing listing;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User user;
	
	
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public String getId() {
		return reviewId;
	}

	public void setId(String id) {
		reviewId = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	

}
