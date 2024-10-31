package com.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers("/", "/successLogin", "style.css?continue", "/listing/{id}/show", "/listing", "/listing/{listingId}/review", "/signup", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
		.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/invalid")
				.defaultSuccessUrl("/successLogin", true)
//				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/userlogout")
				.deleteCookies("JSESSIONID")
				.permitAll();
//		
//		csrf().disable()
//        .authorizeRequests(authorizeRequests ->
//            authorizeRequests
//                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // Allow access to static resources
//                .anyRequest().authenticated()
//        )
//        .formLogin(formLogin ->
//            formLogin
//                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/default", true)
//                .failureUrl("/login?error=true")
//        )
//        .logout(logout ->
//            logout
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//        );
		return http.build();
	}
}
