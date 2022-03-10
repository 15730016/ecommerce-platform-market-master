package com.sintkit.ecommerceservice.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
	private String accessToken;
	private boolean authenticated;
	private UserInfo user;
}
