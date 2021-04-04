package com.pickerschoice.pickerschoice.security;

public enum AppUserPermission {
	PRODUCT_READ("product:read"),
	PRODUCT_WRITE("product:write"),
	ORDER_READ("order:read"),
	ORDER_WRITE("order:write");
	
	private final String permission;

	private AppUserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
