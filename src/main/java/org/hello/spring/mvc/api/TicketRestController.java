package org.hello.spring.mvc.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.dc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController // Dice a Spring che questa classe fornisce API RESTful
@CrossOrigin // Chiunque chiede, anche fuori dal mio dominio la può prendere
@RequestMapping("/api/tickets")
public class TicketRestController {

	@Autowired
	TicketService ticketService;
	
	// Endpoint principale
	@GetMapping
	public List<Ticket> index(@RequestParam(required = false) String title, 
	                          @RequestParam(required = false) String category,
	                          @RequestParam(required = false) String status) {
	    List<Ticket> result = ticketService.getAll();

	    if (title != null && !title.isEmpty()) {
	        result = result.stream().filter(ticket -> ticket.getTitle().contains(title)).collect(Collectors.toList());
	    }
	    if (category != null && !category.isEmpty()) {
	        result = result.stream().filter(ticket -> ticket.getCategory().getName().equalsIgnoreCase(category)).collect(Collectors.toList());
	    }
	    if (status != null && !status.isEmpty()) {
	        result = result.stream().filter(ticket -> ticket.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
	    }

	    return result;
	}
	
	// Endpoint per ottenere un ticket tramite ID
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> show(@PathVariable int id) {
		
		Optional<Ticket> ticket = ticketService.getOptionalById(id);
		
		// Se il ticket esiste restituisce una ResponseEntity con lo stato 200 OK e il ticket nel corpo della risposta
		if (ticket.isPresent()) {
			return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
		}
		
		// Se non esiste restituisce una ResponseEntity con lo stato 404 Not Found
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	// Filtra per categoria
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Ticket>> filterByCategory(@PathVariable String category) {
	    // Confronta esattamente il nome della categoria, gestendo eventuali spazi
	    List<Ticket> tickets = ticketService.getAll();
	    List<Ticket> filteredTickets = tickets.stream()
	        .filter(ticket -> ticket.getCategory().getName().equalsIgnoreCase(category))
	        .collect(Collectors.toList());
	    
	    if (filteredTickets.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<>(filteredTickets, HttpStatus.OK);
	}
	
	// Filtra per stato
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Ticket>> filterByStatus(@PathVariable String status) {
	    List<Ticket> tickets = ticketService.getAll();
	    List<Ticket> filteredTickets = tickets.stream()
	        .filter(ticket -> ticket.getStatus().equalsIgnoreCase(status))
	        .collect(Collectors.toList());  
	    
	    if (filteredTickets.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    return new ResponseEntity<>(filteredTickets, HttpStatus.OK);
	}
	
}
