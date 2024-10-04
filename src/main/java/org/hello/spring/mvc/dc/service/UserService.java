package org.hello.spring.mvc.dc.service;

import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.User;
import org.hello.spring.mvc.db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public User getById(Integer id) {
		
		return repo.findById(id).get();
	}
	
	public User getByUsername(String username) {
		
		return repo.findByUsername(username).get();
	}
	
	public Optional<User> getOptionalById(Integer id) {
		return repo.findById(id);
	}
	
	public List<User> getAll() {
	
		return repo.findAll();
	}
	
	public void save(User operator) {
		
		repo.save(operator);
	}
	
	public List<User> getAvailableOperators() {
	    return repo.findByStatusTrue();
	}

}
