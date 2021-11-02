package com.management.user.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.management.user.dao.UserDao;
import com.management.user.entities.Role;
import com.management.user.entities.User;
import com.management.user.exceptions.UnauthorizedException;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserDao dao;

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles =null;
	    User user = dao.findByUserName(username);
	    Optional<Role> role = user.getRoles().stream().filter(n-> n.getName().equalsIgnoreCase("admin") || 
	    		n.getName().equalsIgnoreCase("write")).findAny();
	    if(user!=null && role!=null) {
	     if(role.isPresent()) {
	    	 roles = Arrays.asList(new SimpleGrantedAuthority(role.get().getName()));
	     }else {
	    	 roles = new ArrayList<>();
	     }
	    	return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), roles);
	    }
		throw new UnauthorizedException(username);
	}
	
	
	public UserDetails loadUserByUsername_old(String username) {
	    User user = dao.findByUserName(username);
	    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), null);
	    
	}

}
