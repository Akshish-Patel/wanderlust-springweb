package com.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.Bean.Listing;
import com.repository.ListingRepo;


public class TestApp extends Exception {

	public TestApp(String messege) {
		super(messege);
	}

	public static void main(String[] args) {
		
		Date date = new Date();
		
		System.out.println(date);
		
	}
}
