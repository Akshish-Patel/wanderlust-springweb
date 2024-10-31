package com.Dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Bean.Review;
import com.Bean.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ListingDto {

	
	private Integer id;
	
	@NotBlank(message = "Title is required!!")
	private String title;
	
	@NotBlank(message = "Description is required!!")
	private String description;
	
	@NotNull(message = "Image file is required")
	private MultipartFile image;
	
//	@org.hibernate.validator.constraints.NotBlank(message = "Price is required!!")
	@jakarta.validation.constraints.NotNull(message = "Price is required!!")
	private Integer Price;
	
	@NotBlank(message = "Location is required!!")
	private String location;
	
	@NotBlank(message = "Country is required!!")
	private String country;
	
	private List<Review> reviewList;
	
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Review> getReviewList() {
		return reviewList;
	}
	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public Integer getPrice() {
		return Price;
	}
	public void setPrice(Integer price) {
		Price = price;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
