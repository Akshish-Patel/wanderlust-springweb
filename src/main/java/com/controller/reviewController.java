package com.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bean.Listing;
import com.Bean.Review;
import com.Bean.User;
import com.repository.ListingRepo;
import com.repository.ReviewRepo;
import com.repository.UserRepo;
import com.utils.CustomError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/listing/{id}/review")
public class reviewController
{
	
	@Autowired
	ListingRepo listingRepo;
	
	@Autowired
	ReviewRepo reviewRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	HttpServletRequest request;


	String login; 
	Boolean Userlogin = false;
	Boolean Userauthor = false;
	
	@PostMapping("")
	public String addListing(@Valid Review review, BindingResult result, @PathVariable()Integer id,  RedirectAttributes redirectAttributes,Model model) throws CustomError
	{
		review.setId(UUID.randomUUID().toString());
		
		if (result.hasErrors()) {
			for (ObjectError err : result.getAllErrors()) {
				throw new CustomError(err.getDefaultMessage());
			}

		}
				
//		get review listing and add into reviewlisting
		Listing listing = listingRepo.findById(id).get();
		java.util.List<Review> reviewList = (java.util.List<Review>) listing.getReviewList();
		reviewList.add(review);
		
//		set reviewlist into listing sche,a
		listing.setReviewList(reviewList);
//		set listing into review schema
		review.setListing(listing);
		
//		set user to review
		User author  = userRepo.findByEmail(request.getRemoteUser());
		review.setUser(author);
		
		listingRepo.save(listing);
		redirectAttributes.addFlashAttribute("success", "New Review Created!!");
		return "redirect:/listing/"+id+"/show";
	}
	
	@GetMapping("/{listingId}")
	public String deleteListing(@PathVariable() Integer id,@PathVariable() String listingId, RedirectAttributes redirectAttributes)
	{
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			Userlogin = false;
		}else{
//			
			Userlogin = true;
			
			
//			check for which user is login
			User currUser = userRepo.findByEmail(request.getRemoteUser());
			User author = reviewRepo.findById(listingId).get().getUser();
 
			if(currUser.equals(author))
			{
				Userauthor = true;	
			}else {
				Userauthor = false;
			}
//			System.out.println("curr user ==== "+currUse);
//			System.out.println("owners user ==== "+listingrepo.findById(id).get().getUser();
		}

		if(Userlogin && Userauthor)
		{
			Listing listing = listingRepo.findById(id).get();
			java.util.List<Review> reviewList = (java.util.List<Review>) listing.getReviewList();
			
//			delete review from the reviewlist of listing
			for(int i=0;i<reviewList.size();i++)
			{
				if(listingId.matches(reviewList.get(i).getId())) 
				{
					reviewList.remove(i);
				}
			}
			
//			set new deleted list into listing object
			listing.setReviewList(reviewList);
			
			Review review = reviewRepo.findById(listingId).get();

//			delete review from table		
			reviewRepo.delete(review);
//			update reviewlist of listing table 
			listingRepo.save(listing);
			redirectAttributes.addFlashAttribute("success", "Review Deleted!!");
			return "redirect:/listing/"+id+"/show";

		}else {
			redirectAttributes.addFlashAttribute("error", "You are not the author of this review");
			return "redirect:/listing/"+id+"/show";
		}
			
}
	
	@ExceptionHandler(value = CustomError.class)
	public String exceptionHandler(CustomError err, Model model) {
		
//		System.out.println(err);
		model.addAttribute("err", err);
//		return "error";
		return null;
	}
}
