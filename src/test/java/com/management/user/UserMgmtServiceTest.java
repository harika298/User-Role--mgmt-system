package com.management.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.management.user.dao.UserDao;
import com.management.user.entities.User;
import com.management.user.models.UserModel;
import com.management.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserMgmtServiceTest {
	
	@Mock
	private UserDao dao;
	
	UserService userService;
	
	@BeforeEach
	void init() {
		userService = new UserService();
	}

	 @Test
	 public void saveUser() {
		 	UserModel userModel = new UserModel();
		 	userModel.setUserId(1001);
		 	userModel.setFirstName("harika");
		 	userModel.setPassword("admin@4321");
		 	userModel.setUserName("harika1234");
		 	ModelMapper modelMapper = new ModelMapper();
		 	User user = modelMapper.map(userModel, User.class);
	        // providing knowledge
	        when(dao.save(any(User.class))).thenReturn(user);

	        User savedUser = dao.save(user);
	        assertThat(savedUser.getFirstName()).isNotNull();
	 }
	 
	 	
}
