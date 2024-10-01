package org.hello.spring.mvc.dc.service;

import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.Operator;
import org.hello.spring.mvc.db.repo.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {

	@Autowired
	private OperatorRepository repo;

	public Operator getById(Integer id) {
		
		return repo.findById(id).get();
	}
	
	public Operator getByUsername(String username) {
		
		return repo.findByUsername(username).get();
	}
	
	public Optional<Operator> getOptionalById(Integer id) {
		return repo.findById(id);
	}
	
	public List<Operator> getAll() {
	
		return repo.findAll();
	}
	
	public void save(Operator operator) {
		
		repo.save(operator);
	}
}
