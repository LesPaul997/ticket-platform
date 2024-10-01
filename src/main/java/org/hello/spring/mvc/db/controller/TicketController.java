package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.dc.service.UserService;

import java.util.List;

import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.dc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Home index
@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@Autowired
	UserService userService;
	
	@GetMapping()
	public String index(Model model) {
		
		model.addAttribute("tickets", ticketService.getAll());
		return "/tickets/index";
	}
	
	// Ricerca per ID
	@GetMapping("/show/{id}")
	public String pizzaDetails(@PathVariable int id, Model model) {

		// consegna al model di una specifica ennupla pizza tramite ID
		model.addAttribute("ticket", ticketService.getById(id));
		
		return "/tickets/show";
	}
	
	// SEARCH
	@GetMapping("/search")
	public String search(@RequestParam String title, Model model) {
		
		List<Ticket> orderedTickets = ticketService.getTitleWithOrderByTitle(title);
		model.addAttribute("tickets", orderedTickets);

		return "/tickets/index";
	}
	
	// CREATE
		@GetMapping("/create")
		public String create(Model model) {

		// creazione nuovo ticket a cui viene settato lo status predefinito "da fare" prima della consegna al model
		Ticket newTicket = new Ticket();
		newTicket.setStatus("da fare");
		
		model.addAttribute("ticket", newTicket);
		model.addAttribute("operators", userService.getAll());
		
		return "/tickets/create";
	}
	
}
