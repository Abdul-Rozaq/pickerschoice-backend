package com.pickerschoice.pickerschoice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;
import com.pickerschoice.pickerschoice.security.AppUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
	
	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	
	private CustomerRepository customerRepository;

	// SPRING SECURITY FETCH USER FOR AUTHENTICATION
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer customer = customerRepository.findByEmail(email).orElseThrow(
				() -> new AppAuthException(String.format(USER_NOT_FOUND_MSG, email))
		);
		
		return new AppUser(
					customer.getPassword(),
					customer.getEmail(),
					customer.getAppUserRole().getGrantedAuthorities(),
					true,
					true,
					true,
					customer.getEnabled()
		);
	}
}
