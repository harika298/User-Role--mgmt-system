package com.management.user.models;

import java.sql.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.management.user.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {
	
    private int userId;
    
	@NotNull
	@Size(max=30)
	private String userName;
    @NotNull
	@Size(min=8, max=16)
	private String password;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String address;
	private Long phoneNumber;
	private Date createdDate;
	private Date modifiedDate;
	private Set<Role> roles;
}