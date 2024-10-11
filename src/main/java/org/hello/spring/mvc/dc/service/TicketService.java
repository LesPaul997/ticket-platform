package org.hello.spring.mvc.dc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.db.model.User;
import org.hello.spring.mvc.db.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repo;
	
	public Ticket getById(Integer id) {
		
		return repo.findById(id).get();
	}
	
	public Optional<Ticket> getOptionalById(Integer id) {
		
		return repo.findById(id);
	}
	
	public List<Ticket> getAll() {
		
		return repo.findAll();
	}
	
	public List<Ticket> getByTitleWithOrderByTitle(String title) {
		
		return repo.findByTitleContainingOrderByTitle(title);
	}
	
	public List<Ticket> getTicketsByOperator(User operator) {
	    return repo.findByUserId(operator.getId()); 
	}

	public Ticket create(Ticket ticket) {
		
		return repo.save(ticket);
	}
	
	public void save(@Valid Ticket ticket) {
		
		repo.save(ticket);
	}

	public Ticket update(Ticket ticket) {
		
		ticket.setUpdatedAt(LocalDateTime.now());
		return repo.save(ticket);
	}
	
	public void deleteById(int id) {
		
		repo.deleteById(id);
	}
	
	// Metodo per filtrare per stato
	public List<Ticket> getByStatus(String status) {
	    return repo.findByStatus(status);
	}

	// Metodo per filtrare per categoria
	public List<Ticket> getByCategoryName(String categoryName) {
	    return repo.findByCategory_Name(categoryName);
	}

}