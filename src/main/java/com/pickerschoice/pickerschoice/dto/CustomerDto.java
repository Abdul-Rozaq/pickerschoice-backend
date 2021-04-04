package com.pickerschoice.pickerschoice.dto;

import com.pickerschoice.pickerschoice.security.AppUserRole;

public class CustomerDto {
	private int customerId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String email;
	private AppUserRole appUserRole;
	private Boolean enabled;

	public CustomerDto(int customerId, String firstName, String lastName, String phone, String address, String email,
			AppUserRole appUserRole, Boolean enabled) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.appUserRole = appUserRole;
		this.enabled = enabled;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AppUserRole getAppUserRole() {
		return appUserRole;
	}
	public void setAppUserRole(AppUserRole appUserRole) {
		this.appUserRole = appUserRole;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
