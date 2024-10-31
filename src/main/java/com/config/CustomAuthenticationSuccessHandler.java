package com.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 DefaultSavedRequest savedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

		 if (savedRequest != null) {
	            String targetUrl = savedRequest.getRequestURL();
	            System.out.println("============Redirecting to target URL: " + targetUrl);
	            // You can perform any additional logic here, such as logging or modifying the URL
	        }

	        // Call the parent handler to proceed with the default behavior
	        super.onAuthenticationSuccess(request, response, authentication);
	}
}
