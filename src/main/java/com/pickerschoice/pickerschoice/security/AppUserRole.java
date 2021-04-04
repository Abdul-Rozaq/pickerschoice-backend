package com.pickerschoice.pickerschoice.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum AppUserRole {
	USER(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(
			AppUserPermission.PRODUCT_READ, 
			AppUserPermission.PRODUCT_WRITE, 
			AppUserPermission.ORDER_READ,
			AppUserPermission.ORDER_WRITE));
	
	private final Set<AppUserPermission> appUserPermissions;

	private AppUserRole(Set<AppUserPermission> appUserPermissions) {
		this.appUserPermissions = appUserPermissions;
	}

	public Set<AppUserPermission> getAppUserPermissions() {
		return appUserPermissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions = getAppUserPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return permissions;
	}
	
}
