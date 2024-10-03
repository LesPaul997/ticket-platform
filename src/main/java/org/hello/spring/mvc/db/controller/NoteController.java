package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.db.model.Note;
import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.dc.service.NoteService;
import org.hello.spring.mvc.dc.service.TicketService;
import org.hello.spring.mvc.dc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	UserService userService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	NoteService noteService;
	
	// Create
	@GetMapping("/create/{id}")
	public String create(@PathVariable int id, Model model) {
		
		// Ticket associato all'id
		Ticket ticketById = ticketService.getById(id);
		
		// Ticket e Utente del ticket da passare al model
		Note newNote = new Note();
		newNote.setTicket(ticketById);
		
		model.addAttribute("note", newNote);
		
		return "/notes/create";
	}
	
	// Store
	public String store(@Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return "/notes/create";
		}
		noteService.save(noteForm);
		int ticketID = noteForm.getTicket().getId();
		
		ticketService.update(ticketService.getById(ticketID));
		attributes.addFlashAttribute("successMessage", "nota sul ticket #" + ticketID + "creata con successo");
		
		return ("redirect:/tickets/show/" + ticketID);
	}
}
