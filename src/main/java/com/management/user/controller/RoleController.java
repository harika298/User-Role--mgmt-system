package com.management.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.user.entities.Role;
import com.management.user.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/insertRole", consumes = { MediaType.APPLICATION_JSON_VALUE })
	private HttpStatus insertRole(@RequestBody Role role) {
		boolean isRoleinserted = roleService.insertorUpdateRole(role);
		if (!isRoleinserted)
			return HttpStatus.EXPECTATION_FAILED;
		return HttpStatus.OK;
	}
	
	@PutMapping(value = "/updateRole", consumes = { MediaType.APPLICATION_JSON_VALUE })
	private HttpStatus updateRole(@RequestBody Role role) {
		boolean isRoleinserted = roleService.insertorUpdateRole(role);
		if (!isRoleinserted)
			return HttpStatus.EXPECTATION_FAILED;
		return HttpStatus.OK;
	}

}
