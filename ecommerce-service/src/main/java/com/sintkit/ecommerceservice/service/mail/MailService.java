package com.sintkit.ecommerceservice.service.mail;

import com.sintkit.ecommerceservice.model.User;

public interface MailService {

	void sendVerificationToken(String token, User user);
}
