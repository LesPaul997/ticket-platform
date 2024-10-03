package org.hello.spring.mvc.db.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.hello.spring.mvc.db.repo.UserRepository;
import org.hello.spring.mvc.db.model.User;

@Service
public class DataBaseUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	Optional<User> user = userRepository.findByUsername(username);
	if (user.isPresent()) {
		return new DataBaseUserDetails(user.get());
	} else {
		throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}
}
