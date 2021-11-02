package com.management.user.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="APP_USER")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="APP_USER")
public class User implements Serializable{
	private static final long serialVersionUID = 4910225916550731448L;
	
	@Id
	private int userId;
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String address;
	private Long phoneNumber;
	private Date createdDate;
	private Date modifiedDate;
	
	//merge - while creating a user if we want to associate with a role, it will create an role if not present
	//persist - while creating a user if we want to associate with a role, throws an exception EntityNotFOundException (Exception occured while inserting user to DB...org.springframework.orm.jpa.JpaObjectRetrievalFailureException: Unable to find com.management.user.entities.Role with id 1002; nested exception is javax.persistence.EntityNotFoundException: Unable to find com.management.user.entities.Role with id 1002)
	//detach - if we delete any user the associated role, the role will be detached automatically
	//all - if we do any CRUD operation it is applicable to the associated object as well
	
	//Here we have to assign a role for specific user
	//Im using Persist because all my roles were created before i create and a user and assign appropriate role for the user.
	//So if we try to assign a role which is not present for any specific user it should throw an error.
	//Lazy - we don't want to load the Role Object until it is called for better memory management
	@ManyToMany(cascade= {CascadeType.PERSIST},fetch=FetchType.EAGER)
	@JoinTable(name="user_roles", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name="role_id")})
	private Set<Role> roles;

	
}
