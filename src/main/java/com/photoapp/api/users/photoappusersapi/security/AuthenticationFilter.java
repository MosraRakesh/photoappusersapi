package com.photoapp.api.users.photoappusersapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.api.users.photoappusersapi.dto.UsersDto;
import com.photoapp.api.users.photoappusersapi.service.UsersService;
import com.photoapp.api.users.photoappusersapi.ui.request.model.CreateUserLoginModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UsersService usersservice;
	private Environment environment;
	
	@Autowired
	public AuthenticationFilter(UsersService usersservice,
			Environment environment,AuthenticationManager authenticationManger) {
		this.usersservice=usersservice;
		this.environment=environment;
		super.setAuthenticationManager(authenticationManger);
	}

	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			CreateUserLoginModel cred = new ObjectMapper().readValue(req.getInputStream(), CreateUserLoginModel.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(cred.getEmailId(), cred.getPassword(), new ArrayList<>())

			);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
			FilterChain chain,Authentication auth) {
		
		String userName=((User) auth.getPrincipal()).getUsername();
		UsersDto userDto=usersservice.getUserByEmailId(userName);
		
		String token=Jwts.builder()
				.setSubject(userDto.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.compact();
		
		res.addHeader("token", token);
		res.addHeader("userId", userDto.getUserId());
		
	}
}
