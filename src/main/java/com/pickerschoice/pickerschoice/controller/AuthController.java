package com.pickerschoice.pickerschoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pickerschoice.pickerschoice.dto.RegistrationRequest;
import com.pickerschoice.pickerschoice.service.RegistrationService;

@RestController
@RequestMapping("/")
public class AuthController {

	private RegistrationService registrationService;
	
	@Autowired	
	public AuthController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping("auth/api/v1/register")
	public ResponseEntity<String> registerNewUser(@RequestBody RegistrationRequest request) {
		return new ResponseEntity<>(registrationService.register(request), HttpStatus.CREATED);
	}

	@GetMapping("auth/api/v1/registration/confirm")
	public String confirmToken(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
}
