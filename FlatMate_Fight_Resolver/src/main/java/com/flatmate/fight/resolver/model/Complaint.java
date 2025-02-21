package com.flatmate.fight.resolver.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "complaint")
public class Complaint {
	//fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false, length = 300)
	private String description;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String severity;
	
	@Column(nullable = false)
	private LocalDateTime timestamp = LocalDateTime.now();
	
	private int upvote = 0;
	
	private int downvote = 0;
	
	private boolean resolved = false;
	
	private String punishment;
	
	@Column(nullable = false)
	private String flatCode;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	//Constructors
	public Complaint() {}
	public Complaint(Long id, String title, String description, String type, String severity, LocalDateTime timestamp,
			int upvote, int downvote, User user,boolean resolved) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.type = type;
		this.severity = severity;
		this.timestamp = timestamp;
		this.upvote = upvote;
		this.downvote = downvote;
		this.user = user;
		this.resolved = resolved;
	}

	//Getters and setters
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getUpvote() {
		return upvote;
	}
	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}
	public int getDownvote() {
		return downvote;
	}
	public void setDownvote(int downvote) {
		this.downvote = downvote;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isResolved() {
		return resolved;
	}
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
	public String getFlatCode() {
		return flatCode;
	}
	public void setFlatCode(String flatCode) {
		this.flatCode = flatCode;
	}
	public String getPunishment() {
		return punishment;
	}
	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}
	
}
