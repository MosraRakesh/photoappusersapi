package com.photoapp.api.users.photoappusersapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.api.users.photoappusersapi.dto.UsersDto;


public interface UsersService extends UserDetailsService{

	public UsersDto createUser(UsersDto userDto);
	public UsersDto getUserByEmailId(String emailId);
	public UsersDto getUserDetailsById(String userId);
}
