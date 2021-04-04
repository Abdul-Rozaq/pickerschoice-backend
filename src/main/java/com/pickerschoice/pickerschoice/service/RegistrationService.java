package com.pickerschoice.pickerschoice.service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.ConfirmationToken;
import com.pickerschoice.pickerschoice.security.AppUserRole;

import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.dto.RegistrationRequest;
import com.pickerschoice.pickerschoice.model.Customer;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

	private AppUserService appUserService;
	private ConfirmationTokenService confirmationTokenService;

	public RegistrationService(AppUserService appUserService,
							   ConfirmationTokenService confirmationTokenService) {
		this.appUserService = appUserService;
		this.confirmationTokenService = confirmationTokenService;
	}

	// RECEIVE A REGISTRATION REQUEST AND SEND IT TO APPUSER SERVICE FOR PERSISTENCE
	// RETURN A TOKEN IF SUCCESSFUL
	public String register(RegistrationRequest request) {
		return appUserService.signUpUser(new Customer(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPhone(),
				request.getAddress(),
				request.getPassword(),
				AppUserRole.USER
		));
	}

	
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
		
		if (confirmationToken.getConfirmedAt() != null)
			throw new AppAuthException("Email already confirmed");

		LocalDateTime expiresAt = confirmationToken.getExpiresAt();
		if (expiresAt.isBefore(LocalDateTime.now()))
			throw new AppAuthException("Token already expired");

		confirmationToken.setToken(token);
		appUserService.enableAppUser(confirmationToken.getCustomer().getEmail());
		return "confirmed";
	}
}
