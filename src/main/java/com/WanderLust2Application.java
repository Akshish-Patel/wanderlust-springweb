package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.config.CustomAuthenticationSuccessHandler;
import com.filter.CookieFilter;

@SpringBootApplication
public class WanderLust2Application {

	public static void main(String[] args) {
		SpringApplication.run(WanderLust2Application.class, args);

	}

	@Bean
	public CustomAuthenticationSuccessHandler getcusAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
