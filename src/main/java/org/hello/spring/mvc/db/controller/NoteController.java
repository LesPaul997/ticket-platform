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
		// Il codice verifica se l'utente autenticato ha il ruolo "OPERATOR" e se non possiede il ticket specifico
		// Se entrambe queste condizioni sono vere, il sistema reindirizza l'utente a una pagina di errore 
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OPERATOR")) && !userService.getByUsername(authentication.getName()).getTickets().contains(ticketById)) {
			return "/pages/authenticationError";
		}
		
		// Crea una nuova nota associata a un ticket e a un utente autenticato e poi la passa al modello per essere visualizzata 
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
		
		// Recupera la nota dal database tramite il suo ID e la assegna alla variabile noteToEdit
		Note noteToEdit = noteService.getById(id);
		
		// Controlla se l'utente autenticato è il proprietario della nota. Questo viene fatto recuperando tutte le note associate all'utente e verificando se la nota da modificare è tra queste
		if (!userService.getByUsername(authentication.getName()).getNotes().contains(noteToEdit)) {
			return "/pages/authenticationError";
		}
		
		model.addAttribute("note", noteService.getById(id));
		return "/notes/edit";
	}
	
	// Update
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		// Se ci sono errori si ritorna alla edit
		if (bindingResult.hasErrors()) {
			return "/notes/edit";
		}
		
		// Se non ci sono errori, la nota viene aggiornata nel database
		noteService.save(noteForm);
		// Dopo aver salvato la nota viene recuperato l'ID del ticket associato alla nota
		int ticketID = noteForm.getTicket().getId();
		ticketService.update(ticketService.getById(ticketID));
		
		attributes.addFlashAttribute("successMessage", "nota #" + ticketID + "modificato con successo!");
		return ("redirect:/tickets/show/" + ticketID);
	}
	
	// Delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, Authentication authentication, RedirectAttributes attributes) {
		
		// Recupera la nota dal database tramite il suo ID usando il servizio e la assegna alla variabile
		Note noteToDelete = noteService.getById(id);
		int ticketID = noteToDelete.getTicket().getId();
		
		// Una volta eliminata la nota, il ticket associato viene aggiornato (come per esempio data e ora)
		noteService.delete(noteToDelete);
		ticketService.update(ticketService.getById(ticketID));
		
		attributes.addFlashAttribute("successMessage", "nota #" + ticketID + "eliminata con successo!");
		return ("redirect:/tickets/show/" + ticketID);
	}
}
