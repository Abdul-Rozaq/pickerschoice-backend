package com.pickerschoice.pickerschoice.service;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pickerschoice.pickerschoice.model.ConfirmationToken;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.ConfirmationTokenRepository;
import com.pickerschoice.pickerschoice.security.AppUserRole;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

	@Mock
	private ConfirmationTokenRepository confirmationTokenRepository;

	private ConfirmationTokenService confirmationTokenService;
	
	@BeforeEach
	public void setup() {
		confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
	}

	@Test
	@DisplayName("Should save token to the database")
	public void shouldSaveToken() {
		Customer customer = new Customer(1, "First name", "Last name", "customer@email", "090283664837", "Address", "Pass", AppUserRole.USER, false, false);
		ConfirmationToken confirmationTokenRequest = new ConfirmationToken("token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), null, customer);
		ConfirmationToken confirmationToken = new ConfirmationToken(1, "token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), null, customer);
		
		Mockito.when(confirmationTokenService.saveConfirmationToken(confirmationTokenRequest)).thenReturn(confirmationToken);
		
		ConfirmationToken actualResponse = confirmationTokenService.saveConfirmationToken(confirmationTokenRequest);
		Mockito.verify(confirmationTokenRepository, Mockito.times(1)).save(confirmationTokenRequest);
		
		Assertions.assertThat(actualResponse.getId()).isEqualTo(confirmationToken.getId());
		Assertions.assertThat(actualResponse.getToken()).isEqualTo(confirmationToken.getToken());
		Assertions.assertThat(actualResponse.getCustomer().getCustomerId()).isEqualTo(confirmationToken.getCustomer().getCustomerId());
	}
}
