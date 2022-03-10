package com.sintkit.ecommerceservice.dto;

/**
 *
 * @since 26/3/18
 */
public enum SocialProvider {

	FACEBOOK("facebook"), GOOGLE("google"), LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}

}
