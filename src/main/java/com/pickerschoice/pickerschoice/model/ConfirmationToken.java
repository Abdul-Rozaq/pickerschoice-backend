package com.pickerschoice.pickerschoice.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="confirmation_tokens")
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String token;

	@Column(nullable = false, name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(nullable = false, name = "expires_at")
	private LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);

	@Column(name = "confirmed_at")
	private LocalDateTime confirmedAt = null;
	
	@ManyToOne
	@JoinColumn(name= "customer_id")
	private Customer customer;

	public ConfirmationToken() {
	}

	public ConfirmationToken(String token, Customer customer) {
		this.token = token;
		this.customer = customer;
	}

	public ConfirmationToken(int id, String token, LocalDateTime createdAt, LocalDateTime expiresAt,
			LocalDateTime confirmedAt, Customer customer) {
		this.id = id;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.confirmedAt = confirmedAt;
		this.customer = customer;
	}
	
	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
			LocalDateTime confirmedAt, Customer customer) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.confirmedAt = confirmedAt;
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "ConfirmationToken{" +
				"id=" + id +
				", token='" + token + '\'' +
				", createdAt=" + createdAt +
				", expiresAt=" + expiresAt +
				", confirmedAt=" + confirmedAt +
				", customer=" + customer +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
