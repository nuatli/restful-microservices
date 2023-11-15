package com.nuatli.rest.webservices.restfulwebservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.*;

public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		// 1) All requests should be authenticated
		http.authorizeRequests(
			auth -> auth.anyRequest().authenticated()	
		);
		// 2) If a request is not authenticated, a web page is shown
		http.httpBasic(withDefaults());
		
		// 3) If a request is not authenticated, a web page is shown
		http.csrf().disable();
		return http.build(); 
	}

}
