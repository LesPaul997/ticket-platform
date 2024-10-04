package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.db.model.Note;
import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.dc.service.NoteService;
import org.hello.spring.mvc.dc.service.TicketService;
import org.hello.spring.mvc.dc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String create(@PathVariable int id, Authentication authentication, Model model) {
		
		// Ticket associato all'id
		Ticket ticketById = ticketService.getById(id);
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OPERATOR")) && !userService.getByUsername(authentication.getName()).getTickets().contains(ticketById)) {
			return "/pages/authenticationError";
		}
		
		// Ticket e Utente del ticket da passare al model
		Note newNote = new Note();
		newNote.setTicket(ticketById);
		newNote.setUser(userService.getByUsername(authentication.getName()));
		
		model.addAttribute("note", newNote);
		return "/notes/create";
	}
	
	// Store
	@PostMapping("/create")
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
	
	// Edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Authentication authentication, Model model) {
		
		// Passiamo sempre un errore se l'utente non è autorizzato
		Note noteToEdit = noteService.getById(id);
		
		if (!userService.getByUsername(authentication.getName()).getNotes().contains(noteToEdit)) {
			return "/pages/authenticationError";
		}
		
		model.addAttribute("note", noteService.getById(id));
		return "/notes/edit";
	}
	
	// Update
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return "/notes/edit";
		}
		
		// Quando si modifica il ticket deve modificarsi anche l'orario
		noteService.save(noteForm);
		int ticketID = noteForm.getTicket().getId();
		ticketService.update(ticketService.getById(ticketID));
		
		attributes.addFlashAttribute("successMessage", "nota #" + ticketID + "modificato con successo!");
		return ("redirect:/tickets/show/" + ticketID);
	}
	
	// Delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, Authentication authentication, RedirectAttributes attributes) {
		
		Note noteToDelete = noteService.getById(id);
		int ticketID = noteToDelete.getTicket().getId();
		
		// Quando si modifica il ticket deve modificarsi anche l'orario
		noteService.delete(noteToDelete);
		ticketService.update(ticketService.getById(ticketID));
		
		attributes.addFlashAttribute("successMessage", "nota #" + ticketID + "eliminata con successo!");
		return ("redirect:/tickets/show/" + ticketID);
	}
}
