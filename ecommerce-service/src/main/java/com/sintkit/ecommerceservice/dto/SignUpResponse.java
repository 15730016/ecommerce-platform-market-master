package com.sintkit.ecommerceservice.dto;

import lombok.Value;

@Value
public class SignUpResponse {
	private boolean using2FA;
	private String qrCodeImageDB;
}
