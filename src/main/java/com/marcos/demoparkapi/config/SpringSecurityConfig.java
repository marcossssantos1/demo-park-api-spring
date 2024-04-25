package com.marcos.demoparkapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.marcos.demoparkapi.jwt.JwtAuthenticationEntryPoint;
import com.marcos.demoparkapi.jwt.JwtAutherizationFilter;



@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
	        return httpSecurity
	                .csrf(csrf -> csrf.disable())
	                .formLogin(formLogin -> formLogin.disable())
	                .httpBasic(basic -> basic.disable())
	                .authorizeHttpRequests(auth  ->
	                        auth.requestMatchers(HttpMethod.POST, "api/v1/users").permitAll()
	                                .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
	                                .anyRequest()
	                                .authenticated()
	                ).addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
	                .sessionManagement(
	                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                ).exceptionHandling(
	                        ex -> ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
	                .build();	
	    }

	    @Bean
	    public JwtAutherizationFilter jwtAuthorizationFilter(){
	        return new JwtAutherizationFilter();
	    }
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
	        return  authenticationConfiguration.getAuthenticationManager();
	    }

}
