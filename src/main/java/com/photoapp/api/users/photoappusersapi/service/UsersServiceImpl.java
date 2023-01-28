package com.photoapp.api.users.photoappusersapi.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.api.users.photoappusersapi.data.UsersEntity;
import com.photoapp.api.users.photoappusersapi.data.UsersRepository;
import com.photoapp.api.users.photoappusersapi.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository usersRepository;
	
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository,BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.usersRepository=usersRepository;
		this.bcryptPasswordEncoder=bcryptPasswordEncoder;
	}

	@Override
	public UsersDto createUser(UsersDto userDto) {
		ModelMapper modelMap=new ModelMapper();
		
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(bcryptPasswordEncoder.encode(userDto.getPassword()));
		modelMap.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		 UsersEntity userEntity= modelMap.map(userDto, UsersEntity.class);
		 usersRepository.save(userEntity);
		 UsersDto savedDto=modelMap.map(userEntity, UsersDto.class);
		return savedDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UsersEntity userEntity=usersRepository.findByEmailId(username);
	
	if(userEntity == null) throw new UsernameNotFoundException(username);
	
		return new User(userEntity.getEmailId(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList());
	}

	@Override
	public UsersDto getUserByEmailId(String emailId) {
		UsersEntity userEntity=usersRepository.findByEmailId(emailId);
		
		if(userEntity == null) throw new UsernameNotFoundException(emailId);
		
		return new ModelMapper().map(userEntity, UsersDto.class);
	}

}
