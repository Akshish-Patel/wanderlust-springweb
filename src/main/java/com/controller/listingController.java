package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bean.Listing;
import com.Bean.User;
import com.Dto.ListingDto;
import com.repository.ListingRepo;
import com.repository.UserRepo;
import com.services.MapboxService;
import com.services.cloudinaryService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.filter.customfilter;
import com.utils.CustomError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse.List;

@Controller
@RequestMapping("/listing")
public class listingController {

	@Autowired
	ListingRepo listingrepo;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	cloudinaryService cloudinaryService;
	
	@Autowired
	MapboxService mapboxService;
	 
	Boolean Userlogin = false;
	Boolean Userowner = false;
	
	ArrayList<Listing> listings = null;

//	home
	@GetMapping("")
	public String allListings(HttpServletRequest request, Model model, @RequestParam(value = "login", required = false) String login) {
		Iterable<Listing> listingsitr = listingrepo.findAll();
//		System.out.println(listingsitr);
		Iterator<Listing> itr = listingsitr.iterator();

		listings = new ArrayList();
		while (itr.hasNext()) {
//			System.out.println(itr.next().getId());
			listings.add(itr.next());
//			System.out.println(itr.next().getId());
		}
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			System.out.println("99999999999999 false or null");
			model.addAttribute("login", "false");
		}else{
			System.out.println("99999999999999 true");
			model.addAttribute("login", "true");
		}
		
		model.addAttribute("listings", listings);
		return "home";
	}

//	new user

	@GetMapping("/new")
	public String getNew(@RequestParam(value = "login", required = false) String login, HttpServletRequest request, Model model) {
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			System.out.println("99999999999999 false or null");
			model.addAttribute("login", "false");
		}else{
			System.out.println("99999999999999 true");
			model.addAttribute("login", "true");
		}
		return "new";
	}

	
	@PostMapping("/new")	   //@ModelAttribute ListingDto form is use to get files and other field from form's
	public String setNew(@Valid @ModelAttribute ListingDto form, BindingResult result, RedirectAttributes redirectAttributes,Model model, HttpServletRequest request) throws CustomError, IOException {
		

			if (result.hasErrors()) {

				String errMsg = "";
				for (ObjectError err : result.getAllErrors()) {
			
					throw new CustomError(err.getDefaultMessage());
				}

			}
		
		String coordinate = mapboxService.getCoordinates(form.getLocation()).toString().replace("[", "").replace("]", "");
		String fileUrl = cloudinaryService.uploadFileToFolder(form.getImage(), "wanderlust_DEV_spring");
		System.out.println(coordinate);
		//		
		Listing listing = new Listing();
		listing.setTitle(form.getTitle());
		listing.setDescription(form.getDescription());
		listing.setPrice(form.getPrice());
		listing.setLocation(form.getLocation());
		listing.setCountry(form.getCountry());
		listing.setCoordinate(coordinate);
		if (fileUrl == null || fileUrl.trim() == "") {
			listing.setImage(
					"https://images.unsplash.com/photo-1489516408517-0c0a15662682?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
		}else {
			listing.setImage(fileUrl);
		}
		
		User owner  = userRepo.findByEmail(request.getRemoteUser());
		System.out.println(listing.getImage());
		listing.setUser(owner);
		listingrepo.save(listing);
		redirectAttributes.addFlashAttribute("success", "New Listing Created!");
		return "redirect:/listing";
		
	}

//	show
	@GetMapping("/{id}/show")
	public String showListing(@PathVariable() Integer id, RedirectAttributes redirectAttributes,Model model, @RequestParam(value = "login", required = false) String login, HttpServletRequest request) {
		Optional<Listing> listingOptional = listingrepo.findById(id);
//		System.out.println("listing====== "+listingOptional.isEmpty());
		if(listingOptional.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Listing you requested for dose not exist!!");
			return "redirect:/listing";
		}
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			System.out.println("99999999999999 false or null");
			model.addAttribute("login", "false");
		}else{
//			check for user is login or nor
			System.out.println("99999999999999 true");
			model.addAttribute("login", "true");
			Userlogin = true;
			
			
//			check for which user is login
			User currUser = userRepo.findByEmail(request.getRemoteUser());
			User owner = listingrepo.findById(id).get().getUser();

			if(currUser.equals(owner))
			{
				Userowner = true;
				model.addAttribute("owner", "true");
				model.addAttribute("currentUser", currUser);	
			}else {
				Userowner = false;
				model.addAttribute("owner", "false");
				model.addAttribute("currentUser", currUser);
			}
//			System.out.println("curr user ==== "+currUse);
//			System.out.println("owners user ==== "+listingrepo.findById(id).get().getUser();
		}
		
		model.addAttribute("listing", listingOptional.get());
		return "show";
	}


//	update
	@GetMapping("/{id}/update")
	public String getEdit(@PathVariable() Integer id, RedirectAttributes redirectAttributes,Model model, @RequestParam(value = "login", required = false) String login, HttpServletRequest request) {
		Optional<Listing> listingOptional = listingrepo.findById(id);
//		System.out.println(listingOptional.get());
		if(listingOptional.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Listing you requested for dose not exist!!");
			return "redirect:/listing";
		}
		
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			System.out.println("99999999999999 false or null");
			model.addAttribute("login", "false");
		}else{
			System.out.println("99999999999999 true");
			model.addAttribute("login", "true");
		}
		
		if(Userlogin && Userowner)
		{
			model.addAttribute("listing", listingOptional.get());
			return "edit";
		}else {
			redirectAttributes.addFlashAttribute("error", "You are not the owner of this listnig");
			return "redirect:/listing/"+id+"/show";
		}
		
	}


	@PostMapping("/{id}/update")
	public String setEdit(@Valid ListingDto form, @PathVariable() Integer id, RedirectAttributes redirectAttributes,Model model) throws CustomError, IOException{
		
			if(Userlogin && Userowner)
			{
				
				Listing listing = listingrepo.findById(id).get();
				listing.setTitle(form.getTitle());
				listing.setDescription(form.getDescription());
				listing.setPrice(form.getPrice());
				listing.setLocation(form.getLocation());
				listing.setCountry(form.getCountry());
				listing.setUser(listing.getUser());
	
	
				if(form.getImage().getSize() > 0) {
					String fileUrl = cloudinaryService.uploadFileToFolder(form.getImage(), "wanderlust_DEV_spring");
					listing.setImage(fileUrl);
				}
				
				String coordinate = mapboxService.getCoordinates(form.getLocation()).toString().replace("[", "").replace("]", "");
				listing.setCoordinate(coordinate);
				
				listingrepo.save(listing);
				redirectAttributes.addFlashAttribute("success", "Listing Updated!!");
				return "redirect:/listing/" + id + "/show";
			}else {
				redirectAttributes.addFlashAttribute("error", "You are not the owner of this listnig");
				return "redirect:/listing/"+id+"/show";
			}
		
		
		
}

//	delete
	@GetMapping("/{id}/delete")
	public String deleteListing(@PathVariable Integer id, RedirectAttributes redirectAttributes,Model model) {
		
		if(Userlogin && Userowner)
		{
			listingrepo.deleteById(id);
			redirectAttributes.addFlashAttribute("success", "Listing Deleted!!");
			return "redirect:/listing";
		}else {
			redirectAttributes.addFlashAttribute("error", "You are not the owner of this listnig");
			return "redirect:/listing/"+id+"/show";
		}
	
	}
	

	@ExceptionHandler(value = CustomError.class)
	public String exceptionHandler(CustomError err, Model model) {
		
		System.out.println("llllllllllllllllllllllll    "+err);
		model.addAttribute("err", err.getMessage());
		return "error";
//		return null;
	}
}
