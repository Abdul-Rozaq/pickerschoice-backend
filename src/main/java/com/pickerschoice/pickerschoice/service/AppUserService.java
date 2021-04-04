package com.pickerschoice.pickerschoice.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.ConfirmationToken;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;
import com.pickerschoice.pickerschoice.security.AppUser;

@Service
public class AppUserService implements UserDetailsService {
	
	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	private final static String USER_ALREADY_EXISTS = "user with email %s already exist";
	
	private CustomerRepository customerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private ConfirmationTokenService confirmationtokenService;

	public AppUserService(CustomerRepository customerRepository,
						  BCryptPasswordEncoder bCryptPasswordEncoder,
						  ConfirmationTokenService confirmationtokenService) {
		this.customerRepository = customerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationtokenService = confirmationtokenService;
	}

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

	// REGISTER A NEW USER AND GENERATE A VERIFICATION TOKEN
	@Transactional
	public String signUpUser(Customer user) {
		// IF USER ALREADY EXISTS, THROW AN ERROR
		boolean userExists = customerRepository.findByEmail(user.getEmail()).isPresent();
		if (userExists) 
			throw new AppAuthException(String.format(USER_ALREADY_EXISTS, user.getEmail()));
		
		
		// ENCODE THE PASSWORD FROM REQUEST AND SAVE USER TO THE DATABASE
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		customerRepository.save(user);
		
		// GENERATE A TOKEN AND SAVE TOKEN TO THE DATABASE
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
		ConfirmationToken savedConfirmationToken = confirmationtokenService.saveConfirmationToken(confirmationToken);

		return savedConfirmationToken.getToken();
	}

	// ACTIVATE USER BY SETTING ENABLED TO TRUE
	@Transactional
	public int enableAppUser(String email) {
		return customerRepository.enableAppUser(email);
	}
}
