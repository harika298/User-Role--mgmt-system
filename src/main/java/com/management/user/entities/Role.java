package com.management.user.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role implements Serializable {
	private static final long serialVersionUID = -5525359165179861924L;

	@Id
	private int roleId;

	private String name;
	private String description;
	private Date createdDate;
	private Date modifiedDate;

	@ManyToMany(mappedBy = "roles", cascade= {CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JsonBackReference
	private Set<User> users;

}