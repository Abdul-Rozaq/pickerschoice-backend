package com.pickerschoice.pickerschoice.dto;

import com.pickerschoice.pickerschoice.security.AppUserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CustomerDto {
	private int customerId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String email;
	private AppUserRole appUserRole;
	private Boolean enabled;

}
