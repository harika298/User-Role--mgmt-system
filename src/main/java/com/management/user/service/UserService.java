package com.management.user.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.management.user.dao.UserDao;
import com.management.user.entities.Role;
import com.management.user.entities.User;
import com.management.user.exceptions.BusinessException;
import com.management.user.exceptions.ResourceAlreadyExists;
import com.management.user.exceptions.ResourceNotFoundException;
import com.management.user.models.ResponseModel;

@Service
@Transactional
@Cacheable(cacheNames="userCache", key="#id")  
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	public void insertOrUpdateUser(User user) {
		try {
			userDao.save(user);
			logger.info("user has successfully into DB ==>" + user);
		} catch (Exception ex) {
			throw new ResourceAlreadyExists("resource already exists...");
		}
	}
	int userId;
	public void updateUser(User user) {
		try {
			userId = user.getUserId();
			Optional<User> userDetails = userDao.findById(userId);

			Set<Role> roles = userDetails.get().getRoles();
			roles.addAll(user.getRoles());
			userDetails.get().setRoles(roles);
			userDao.save(userDetails.get());
			logger.info("user has successfully updated into DB ==>" + user);
		} catch (Exception ex) {
			throw new BusinessException("Exception occured while updating user to DB...");
		}
	}

	public void deleteUserRoleById(User user) {
		userId = user.getUserId();
		Set<Role> roles = user.getRoles();
		Optional<User> userDetails = userDao.findById(userId);
		Set<Role> tmpRoles = new HashSet<>();
		for (Role eachRole : roles) {
			for (Role currRole : userDetails.get().getRoles()) {
				if (currRole.getRoleId() != eachRole.getRoleId()) {
					tmpRoles.add(currRole);
				}
			}
		}

		userDetails.get().setRoles(tmpRoles);

		userDao.save(userDetails.get());
	}

	public void deleteUserById(int id) {
		try {
			userDao.deleteById(id);
		} catch (BusinessException ex) {
			throw new BusinessException("Exception Occured while deleting id from DB" + id);
		}
	}

	public Optional<User> getUserById(int id) {
		try {
			Optional<User> user= userDao.findById(id);
			if(user==null || user.isEmpty() || user.get()==null) 
				throw new ResourceNotFoundException("Resource not found for req" + id);
		return user;
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

	public Set<Role> getUserRolesById(int id) {
		try {
			Set<Role> roles = userDao.findById(id).get().getRoles();
			if(roles.isEmpty()) throw new ResourceNotFoundException("Resource not found for req" + id);
		return roles;
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

	public Page<User> getAllUsers(Pageable pageable) {
		// TODO Auto-generated method stub
		return userDao.findAll(pageable);
	}
	
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	public ResponseEntity<ResponseModel> setResponse(String message, HttpStatus status) {
		ResponseModel response = new ResponseModel();
		response.setStatus(status);
		response.setTimeStamp(LocalDateTime.now());
		response.setMessage(message);
		return new ResponseEntity<ResponseModel>(response, status);

	}
}
