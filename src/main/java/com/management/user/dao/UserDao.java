package com.management.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.user.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByUserName(String username);
}
