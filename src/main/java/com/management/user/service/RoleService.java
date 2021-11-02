package com.management.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.user.dao.RoleDao;
import com.management.user.entities.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public boolean insertorUpdateRole(Role role) {
		boolean isRoleInserted = false;
		try {
			roleDao.save(role);
			isRoleInserted = true;
		} catch (Exception ex) {
			System.out.println("Exception occured while inserting Role into DB..." + ex);
		}
		return isRoleInserted;
	}

}
