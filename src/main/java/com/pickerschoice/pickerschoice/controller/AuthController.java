package com.pickerschoice.pickerschoice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pickerschoice.pickerschoice.dto.RegistrationRequest;
import com.pickerschoice.pickerschoice.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth/api/v1")
@AllArgsConstructor
public class AuthController {

	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerNewUser(@RequestBody RegistrationRequest request) {
		return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
	}

	@GetMapping("/registration/confirm")
	public String confirmToken(@RequestParam("token") String token) {
		return authService.confirmToken(token);
	}
}
