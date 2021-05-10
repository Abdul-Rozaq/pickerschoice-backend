package com.pickerschoice.pickerschoice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pickerschoice.pickerschoice.security.AppUserRole;

@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notification_id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private Customer customer;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "createdAt")
	private LocalDateTime createdAt;
	
	@Column(name = "isRead")
	private Boolean isRead;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "recipient")
	private AppUserRole recipient;

	public Notification() {
	}

	public Notification(Customer customer, String message, LocalDateTime createdAt, Boolean isRead,
			AppUserRole recipient) {
		super();
		this.customer = customer;
		this.message = message;
		this.createdAt = createdAt;
		this.isRead = isRead;
		this.recipient = recipient;
	}

	public Notification(int notification_id, Customer customer, String message, LocalDateTime createdAt, Boolean isRead,
			AppUserRole recipient) {
		super();
		this.notification_id = notification_id;
		this.customer = customer;
		this.message = message;
		this.createdAt = createdAt;
		this.isRead = isRead;
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		return "Notification [notification_id=" + notification_id + ", message=" + message + ", createdAt=" + createdAt
				+ ", isRead=" + isRead + ", notificationType=" + recipient + "]";
	}

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public AppUserRole getNotificationType() {
		return recipient;
	}

	public void setNotificationType(AppUserRole recipient) {
		this.recipient = recipient;
	}
	
	
}
