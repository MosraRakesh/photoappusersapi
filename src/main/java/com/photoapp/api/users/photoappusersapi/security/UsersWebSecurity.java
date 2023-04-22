package com.photoapp.api.users.photoappusersapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.photoapp.api.users.photoappusersapi.service.UsersService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class UsersWebSecurity extends WebSecurityConfigurerAdapter {
	

	private UsersService usersService;
	
	private Environment environment;
	
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	public UsersWebSecurity(UsersService usersService,Environment environment,BCryptPasswordEncoder bcryptPasswordEncoder){
		this.usersService=usersService;
		this.environment=environment;
		this.bcryptPasswordEncoder=bcryptPasswordEncoder;
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//it will be enabled when we are using @enablewebsecurity ,since we are going to use jwt token we need to disable cross side request forgiery
		http.csrf().disable();
	
		
		//allows all requests to the methods in controller mapping /users
		http.authorizeRequests().antMatchers("/**").permitAll()
		.antMatchers(HttpMethod.GET,"/actuator/health").permitAll()
		.antMatchers(HttpMethod.GET,"/actuator/circuitbreakervents").permitAll()
		.and()
		.addFilter(getAuthenticationFilter());//adding auth filter
		//it block h2 console to disable it we use below piece of code
		http.headers().frameOptions().disable();
		;
		//allows all requests to the methods coming from below ipaddress 
		//http.authorizeRequests().antMatchers("/**").hasIpAddress("ipaddress");
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter=new AuthenticationFilter(usersService,environment,authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());//auth manager
		authenticationFilter.setFilterProcessesUrl("/users/login");
		return authenticationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(usersService).passwordEncoder(bcryptPasswordEncoder);
	}

}
