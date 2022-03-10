package com.sintkit.ecommerceservice.config;

import com.sintkit.ecommerceservice.dto.SocialProvider;
import com.sintkit.ecommerceservice.model.Role;
import com.sintkit.ecommerceservice.model.User;
import com.sintkit.ecommerceservice.repository.RoleRepository;
import com.sintkit.ecommerceservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		// Create initial roles
		Role userRole = createRoleIfNotFound(Role.ROLE_USER);
		Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
		Role modRole = createRoleIfNotFound(Role.ROLE_MODERATOR);
		createUserIfNotFound(Set.of(userRole, adminRole, modRole));
		alreadySetup = true;
	}

	@Transactional
	void createUserIfNotFound(Set<Role> roles) {
		User user = userRepository.findByEmail("nhamthiensi@gmail.com");
		if (user == null) {
			user = new User();
			user.setDisplayName("SINT Kit");
			user.setEmail("nhamthiensi@gmail.com");
			user.setPassword(passwordEncoder.encode("S@igon1990!@#"));
			user.setRoles(roles);
			user.setProvider(SocialProvider.LOCAL.getProviderType());
			user.setEnabled(true);
			Date now = new Date();
			long longTimeNow = now.getTime();
			user.setCreatedDate(longTimeNow);
			user.setModifiedDate(longTimeNow);
			user = userRepository.save(user);
		}
	}

	@Transactional
	Role createRoleIfNotFound(final String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = roleRepository.save(new Role(name));
		}
		return role;
	}
}
