package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.dc.service.OperatorService;
import org.hello.spring.mvc.dc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@Autowired
	OperatorService operatorService;
	
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
	
	
	
}
