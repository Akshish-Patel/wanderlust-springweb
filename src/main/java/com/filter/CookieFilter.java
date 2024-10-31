package com.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.IOException;

public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast the response to HttpServletResponse
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Create a new cookie
        Cookie customCookie = new Cookie("login", "false");
        customCookie.setPath("/");
        customCookie.setHttpOnly(true); // Set HttpOnly if needed
        customCookie.setSecure(true); // Set Secure if needed
        customCookie.setMaxAge(60 * 60 * 24); // Set expiry in seconds (1 day)

        // Add the cookie to the response
        httpServletResponse.addCookie(customCookie);

        // Continue the request-response flow
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
