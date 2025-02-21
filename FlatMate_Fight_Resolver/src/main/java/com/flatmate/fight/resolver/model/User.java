package com.flatmate.fight.resolver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	//fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true,nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String flatCode;
	
	private int karmaPoints = 0;
	
	
	//constructors
	public User() {}
	public User(String username, String password, String email, String flatcode) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.flatCode = flatcode;
	}
	
	//Getters and setters
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlatCode() {
		return flatCode;
	}

	public void setFlatCode(String flatcode) {
		this.flatCode = flatcode;
	}
	public int getKarmaPoints() {
		return karmaPoints;
	}
	public void setKarmaPoints(int karmaPoints) {
		this.karmaPoints = karmaPoints;
	}
}
