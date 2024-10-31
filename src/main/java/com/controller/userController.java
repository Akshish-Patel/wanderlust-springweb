package com.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bean.User;
import com.repository.UserRepo;
import com.utils.CustomError;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
		
	
	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		System.out.println("============before "+request.getRequestURL());
		if(request.getRemoteUser() == null) {
			model.addAttribute("login", "false");
		}else {
			model.addAttribute("login", "true");
		}
		redirectAttributes.addFlashAttribute("error", "You must have to Login!!");
		return "login";
	}
	
	@GetMapping("/invalid")
	public String invalid(RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("error", "Invalid Username or Password!!");
		return "redirect:/login";
	}
	
	@GetMapping("/signup")
	public String getSignup(HttpServletRequest request, Model model) {
		
		if(request.getRemoteUser() == null) {
			model.addAttribute("login", "false");
		}else{
			model.addAttribute("login", "true");
		}
		return "signup";
	}
	
	@PostMapping("/signup")
	public String Signup(User user, RedirectAttributes redirectAttributes,Model model) {
		
		User repitUser = userRepo.findByUsername(user.getUsername());
		User repitUserbyEmail = userRepo.findByEmail(user.getEmail());
		if(repitUser == null && repitUserbyEmail == null) {
			System.out.println(repitUser);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println(user.getPassword());
			userRepo.save(user);
			redirectAttributes.addFlashAttribute("success", "Welcome to Wanderlust!");
			return "redirect:/listing";
		}else{
			if(!(repitUser == null)) {
				redirectAttributes.addFlashAttribute("error", "A user with the given username is already registered");
			}else {
				redirectAttributes.addFlashAttribute("error", "A user with the given Email is already registered");
			}
			
			return "redirect:/signup";
		}
	}
	
	@GetMapping("/successLogin")
	public String SuccessLogin(RedirectAttributes redirectAttributes, HttpServletRequest request) {
	
		System.out.println("============after ");
		redirectAttributes.addFlashAttribute("success", "Welcome back to Wanderlust!!");
		return "redirect:/listing";
	}
	
	@GetMapping("/userlogout")
	public String logout(RedirectAttributes redirectAttributes) {
	
		redirectAttributes.addFlashAttribute("success", "Your are logged out!!");
		return "redirect:/listing";
	}
	
	@ExceptionHandler(value = CustomError.class)
	public String exceptionHandler(CustomError err, Model model) {
		
//		System.out.println(err);
		model.addAttribute("err", err.getMessage());
		return "error";
//		return null;
	}
	
}
