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
	
	// Il Service viene chiamato durante l'autenticazione per recuperare i dettagli dell'utente in base al suo nome utente
	// Il parametro username rappresenta il nome utente inserito dall'utente che si sta cercando di autenticare
	// Se il nome utente non viene trovato, viene lanciata l'eccezione UsernameNotFoundException
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	// Il risultato viene avvolto in un Optional per gestire il caso in cui l'utente non esista
	Optional<User> user = userRepository.findByUsername(username);
	if (user.isPresent()) {
		return new DataBaseUserDetails(user.get());
	} else {
		throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}
}
