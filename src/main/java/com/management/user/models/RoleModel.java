package com.management.user.models;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleModel {
	
	private int roleId;
	private String name;
	private String description;
	private Date createdDate;
	private Date modifiedDate;

	
}