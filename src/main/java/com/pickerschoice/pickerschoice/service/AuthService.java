package com.pickerschoice.pickerschoice.service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.ConfirmationToken;
import com.pickerschoice.pickerschoice.security.AppUserRole;

import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.dto.RegistrationRequest;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final static String USER_ALREADY_EXISTS = "user with email %s already exist";

	private ConfirmationTokenService confirmationTokenService;
	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;

	// RECEIVE A REGISTRATION REQUEST AND SEND IT TO APPUSER SERVICE FOR PERSISTENCE
	// RETURN A TOKEN IF SUCCESSFUL
	public String register(RegistrationRequest request) {
		// IF USER ALREADY EXISTS, THROW AN ERROR
		boolean userExists = customerRepository.findByEmail(request.getEmail()).isPresent();
		if (userExists) 
			throw new AppAuthException(String.format(USER_ALREADY_EXISTS, request.getEmail()));
		
		Customer user = customerRepository.save(new Customer(
								request.getFirstName(), request.getLastName(),
								request.getEmail(), request.getPhone(),
								request.getAddress(), passwordEncoder.encode(request.getPassword()),
								AppUserRole.USER)
						);
		
		return generateToken(user);
	}

	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
		
		if (confirmationToken.getConfirmedAt() != null)
			throw new AppAuthException("Email already confirmed");

		LocalDateTime expiresAt = confirmationToken.getExpiresAt();
		if (expiresAt.isBefore(LocalDateTime.now()))
			throw new AppAuthException("Token already expired");

		confirmationToken.setToken(token);
		enableAppUser(confirmationToken.getCustomer().getEmail());
		confirmationTokenService.updateConfirmation(token);
		
		return "confirmed";
	}
	
	public Customer getCurrentCustomer() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerRepository
				.findByEmail(email)
				.orElseThrow(() -> new AppAuthException(String.format(USER_ALREADY_EXISTS, email)));
	}
	
	// Save A NEW USER AND GENERATE A VERIFICATION TOKEN
	@Transactional
	private String generateToken(Customer user) {
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
		ConfirmationToken savedConfirmationToken = confirmationTokenService.saveConfirmationToken(confirmationToken);

		return savedConfirmationToken.getToken();
	}
	
	// ACTIVATE USER BY SETTING ENABLED TO TRUE
	@Transactional
	private int enableAppUser(String email) {
		return customerRepository.enableAppUser(email);
	}
}
