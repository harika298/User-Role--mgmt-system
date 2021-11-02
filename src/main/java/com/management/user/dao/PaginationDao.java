package com.management.user.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.management.user.entities.User;

@Repository
public interface PaginationDao extends PagingAndSortingRepository<User,Integer> {
	
}
