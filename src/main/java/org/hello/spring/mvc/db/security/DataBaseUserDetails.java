package org.hello.spring.mvc.db.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.hello.spring.mvc.db.model.Role;
import org.hello.spring.mvc.db.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class DataBaseUserDetails implements UserDetails{

private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	
	private final Integer id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;

	public DataBaseUserDetails(User user) {
		
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		
		// Inizializza l'insieme delle autorizzazioni con un HashSet vuoto, cicliamo attraverso i ruoli dell'utente
		// Per ogni ruolo dell'utente viene creata una nuova autorizzazione e aggiunta all'insieme authorities
		authorities = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	}
	
	@Override
	// Questo metodo restituisce l'insieme di autorizzazioni dell'utente. Viene utilizzato da Spring Security per determinare i ruoli dell'utente
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	
	@Override
	public String getPassword() {
		
		return this.password;
	}
	
	@Override
	public String getUsername() {
		
		return this.username;
	}

}
