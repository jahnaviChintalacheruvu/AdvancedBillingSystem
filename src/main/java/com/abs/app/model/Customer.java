package com.abs.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String email;
	private String name;
	private String password;
	private String age;
	private String gender;
	private String adminEmail;
	private String role;
	private String mobileNumber;
	private String doorNo;
	private String street;
	private String city;
	private String zipCode;
}
