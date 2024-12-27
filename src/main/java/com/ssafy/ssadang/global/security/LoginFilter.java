package com.ssafy.ssadang.global.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
	    // json 파싱
		StringBuilder jsonBuilder = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            jsonBuilder.append(line);
	        }
	    } catch (IOException e) {
			// TODO Auto-generated catch block
	    	throw new AuthenticationServiceException("Invalid request body or JSON format.", e);
		}
	    
	    String jsonString = jsonBuilder.toString();
	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, String> credentials;
		try {
			credentials = objectMapper.readValue(jsonString, Map.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationServiceException("Invalid request body or JSON format.", e);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationServiceException("Invalid request body or JSON format.", e);
		}
		
	    String username = credentials.get("email");
	    String password = credentials.get("password");
	    
	    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
	    return authenticationManager.authenticate(authToken);
	}
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

    }
}
