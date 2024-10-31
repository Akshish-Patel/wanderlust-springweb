package com.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Listing 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Title is required!!")
	private String title;
	
	@Column(length = 500)
	@NotBlank(message = "Description is required!!")
	private String description;
	
	@Column(length = 500)
	private String image;
	
//	@org.hibernate.validator.constraints.NotBlank(message = "Price is required!!")
	@jakarta.validation.constraints.NotNull(message = "Price is required!!")
	private Integer Price;
	
	@NotBlank(message = "Location is required!!")
	private String location;
	
	@NotBlank(message = "Country is required!!")
	private String country;
	
	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
	private List<Review> reviewList;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	private User user;
	
	private String coordinate;
	
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
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
