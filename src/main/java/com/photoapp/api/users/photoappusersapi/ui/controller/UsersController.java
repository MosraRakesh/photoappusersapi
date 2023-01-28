package com.photoapp.api.users.photoappusersapi.ui.controller;

import javax.validation.Valid;
import javax.ws.rs.core.Response.Status;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.api.users.photoappusersapi.dto.UsersDto;
import com.photoapp.api.users.photoappusersapi.service.UsersService;
import com.photoapp.api.users.photoappusersapi.ui.request.model.CreateUserRequestModel;
import com.photoapp.api.users.photoappusersapi.ui.response.model.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsersService userService;

	@GetMapping("/status/check")
	public String Status() {
		return "working on port::"+env.getProperty("local.server.port");
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel createUser) {
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UsersDto userDto=  modelMapper.map(createUser, UsersDto.class);
		UsersDto userRespDto=userService.createUser(userDto);
		CreateUserResponseModel response=modelMapper.map(userRespDto, CreateUserResponseModel.class);
		return  ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
