package com.sintkit.ecommerceservice.service.useraccount;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.sintkit.ecommerceservice.dto.LocalUser;
import com.sintkit.ecommerceservice.dto.SignUpRequest;
import com.sintkit.ecommerceservice.exception.UserAlreadyExistAuthenticationException;
import com.sintkit.ecommerceservice.model.User;

/**
 *
 * @since 26/3/18
 */
public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	void createVerificationTokenForUser(User user, String token);

	boolean resendVerificationToken(String token);

	String validateVerificationToken(String token);
}
