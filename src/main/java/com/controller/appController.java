package com.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Bean.Listing;
import com.repository.ListingRepo;
import com.utils.CustomError;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class appController {

//root
	@GetMapping("/")
	public String root(@RequestParam(value = "login", required = false) String login, HttpServletRequest request, Model model) {
		
//		check user is login or not
		if((login == "false" || login == null) && request.getRemoteUser() == null) {
			System.out.println("99999999999999 false or null");
			model.addAttribute("login", "false");
		}else{
			System.out.println("99999999999999 true");
			model.addAttribute("login", "true");
		}
		return "root";
	}
	
//	** is use to get any mapping in spring boot
	@RequestMapping("**")
	public String allMapping() throws CustomError {

		throw new CustomError("404 Page not Found!!");
	}
	
	@Autowired
	ListingRepo listingrepo;

	ArrayList<Listing> listings = null;

	


	@ExceptionHandler(value = CustomError.class)
	public String exceptionHandler(CustomError err, Model model, HttpServletRequest request) {

//		System.out.println(err);
		if(request.getRemoteUser() == null) {
			model.addAttribute("login", "false");
		}else {
			model.addAttribute("login", "true");
		}
		model.addAttribute("err", err);
		return "error";
//		return null;
	}
}
