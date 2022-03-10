package com.sintkit.ecommerceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sintkit.ecommerceservice.model.User;
import com.sintkit.ecommerceservice.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);
}
