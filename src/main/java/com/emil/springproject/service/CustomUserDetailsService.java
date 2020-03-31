package com.emil.springproject.service;

import com.emil.springproject.beans.User;
import com.emil.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		if (usernameOrEmail.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		Optional<User> userOptional = userService.findUserByEmail(usernameOrEmail);

		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User " + usernameOrEmail + " not found");
		}

		User user = userOptional.get();

		return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
				getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		Role role = user.getRole();
//		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//		return authorities;
		List emptyList = Collections.emptyList();
		return emptyList;
	}

}
