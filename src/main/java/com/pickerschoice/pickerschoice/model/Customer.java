package com.pickerschoice.pickerschoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickerschoice.pickerschoice.security.AppUserRole;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
 * step 1
*/

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name= "email")
    private String email;
    
    @Column(name= "phone")
    private String phone;
    
    @Column(name= "address")
    private String address;
    
    @Column(name= "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    
    private Boolean locked = false;
    private Boolean enabled = false;

    @OneToMany(
            mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Order> orders;
    
    public Customer(String firstName, String lastName, String email, 
    		String phone, String address, String password, AppUserRole appUserRole) 
    {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.appUserRole = appUserRole;
	}
    public Customer() {
    }

    public Customer(int customerId, String firstName, String lastName, String email, String phone, String address,
			String password, AppUserRole appUserRole, Boolean locked, Boolean enabled) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.appUserRole = appUserRole;
		this.locked = locked;
		this.enabled = enabled;
	}
	@Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", appUserRole=" + appUserRole +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public AppUserRole getAppUserRole() {
		return appUserRole;
	}
	public void setAppUserRole(AppUserRole appUserRole) {
		this.appUserRole = appUserRole;
	}
	public void createOrder(Order order) {
        if (orders == null) orders = new ArrayList<>();

        orders.add(order);
        order.setCustomer(this);
    }
}
