package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.dc.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.db.model.User;
import org.hello.spring.mvc.dc.service.CategoryService;
import org.hello.spring.mvc.dc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


// Home index
@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping()
	public String index(Authentication authentication, Model model) {
		
		// Il blocco di codice verifica se l'utente autenticato ha il ruolo di "ADMIN". 
		// Se sì, recupera tutti i ticket e li rende disponibili nella vista /tickets/index, permettendo così all'amministratore di visualizzare tutti i ticket nel sistema.
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			model.addAttribute("tickets", ticketService.getAll());
			return "/tickets/index";
		}
		
		model.addAttribute("tickets", userService.getByUsername(authentication.getName()).getTickets());
		return "/tickets/index";
	}
	
	// Ricerca per ID SHOW
	@GetMapping("/show/{id}")
	public String show(@PathVariable int id, Authentication authentication, Model model) {
	    
	    // Recupera il ticket tramite il suo ID
	    Ticket ticketToShow = ticketService.getById(id);
	    
	    // Controlla se l'utente è un operatore e se il ticket non gli appartiene
	    boolean isOperator = authentication.getAuthorities().stream()
	                        .anyMatch(a -> a.getAuthority().equals("OPERATOR"));
	    boolean userOwnsTicket = userService.getByUsername(authentication.getName()).getTickets()
	                           .contains(ticketToShow);
	    
	    // Se l'utente è un operatore e non possiede il ticket, mostra un errore
	    if (isOperator && !userOwnsTicket) {
	        return "/pages/authError";
	    }

	    // Aggiungi il ticket al modello e mostra la vista
	    model.addAttribute("ticket", ticketToShow);
	    return "/tickets/show";
	}
	
	// SEARCH
	@GetMapping("/search")
	public String search(@RequestParam String title, Authentication authentication,  Model model) {
		
		// Recupera i ticket ordinati per titolo
		List<Ticket> orderedTickets = ticketService.getTitleWithOrderByTitle(title);
		
		// Controlla se l'utente è un amministratore
		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
		
		// Se è un amministratore, restituisci tutti i ticket
		if (isAdmin) {
			model.addAttribute("tickets", orderedTickets);
			return "/tickets/index";
		}
		
		// Se non è un amministratore filtra i ticket posseduti dall'utente
		List<Ticket> ownedTickets = userService.getByUsername(authentication.getName()).getTickets();
		
		// Filtra i ticket ordinati che l'utente possiede
		List<Ticket> ownedOrderedTickets = orderedTickets.stream().filter(ownedTickets::contains).collect(Collectors.toList());
		
		model.addAttribute("tickets", ownedOrderedTickets);
		return "/tickets/index";
	}
	
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
	    
	    // Crea un nuovo ticket con status predefinito "da fare"
	    Ticket newTicket = new Ticket();
	    newTicket.setStatus("da fare");

	    // Aggiungi il ticket e altre entità al modello, inclusi solo gli operatori disponibili
	    model.addAttribute("ticket", newTicket);
	    model.addAttribute("operators", userService.getAvailableOperators());
	    model.addAttribute("categories", categoryService.getAll());

	    return "/tickets/create";
	}


	// STORE
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, 
	                    Model model, RedirectAttributes attributes, @AuthenticationPrincipal UserDetails currentUser) {

	    // Controlla se ci sono errori di validazione
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("operators", userService.getAvailableOperators());
	        model.addAttribute("categories", categoryService.getAll());
	        return "/tickets/create";
	    }

	    // Controlla se l'operatore selezionato è disponibile
	    User assignedOperator = userService.getById(ticketForm.getUser().getId());
	    if (!assignedOperator.isStatus()) {
	        bindingResult.rejectValue("user", "error.ticket", "L'operatore selezionato non è disponibile.");
	        model.addAttribute("operators", userService.getAvailableOperators());
	        model.addAttribute("categories", categoryService.getAll());
	        return "/tickets/create";
	    }

	    // Recupera l'utente attualmente loggato e assegna il ticket
	    User user = userService.getByUsername(currentUser.getUsername());
	    ticketForm.setUser(user);

	    // Salva il ticket
	    ticketService.save(ticketForm);

	    // Aggiungi un messaggio di successo e reindirizza alla lista dei ticket
	    attributes.addFlashAttribute("successMessage", "Ticket #" + ticketForm.getId() + " creato con successo");

	    return "redirect:/tickets";
	}


	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Authentication authentication, Model model) {

	    // Recupera il ticket tramite ID
	    Ticket ticketToEdit = ticketService.getById(id);
	    
	    // Controlla se l'utente è un operatore e non possiede il ticket
	    boolean isOperator = authentication.getAuthorities().stream()
	                        .anyMatch(a -> a.getAuthority().equals("OPERATOR"));
	    boolean userOwnsTicket = userService.getByUsername(authentication.getName()).getTickets()
	                           .contains(ticketToEdit);
	    
	    // Se l'utente è un operatore e non possiede il ticket, mostra un errore
	    if (isOperator && !userOwnsTicket) {
	        return "/pages/authError";
	    }

	    // Aggiungi il ticket, gli operatori e le categorie al modello
	    model.addAttribute("ticket", ticketToEdit);
	    model.addAttribute("operators", userService.getAll());
	    model.addAttribute("categories", categoryService.getAll());

	    return "/tickets/edit";
	}

		
	
	// UPDATE
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, 
	                     Model model, RedirectAttributes attributes) {

	    // Controlla se ci sono errori di validazione
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("operators", userService.getAll());
	        //model.addAttribute("categories", categoryService.getAll());
	        return "/tickets/edit";
	    }

	    // Salva il ticket aggiornato
	    ticketService.save(ticketForm);

	    // Aggiungi un messaggio di successo e reindirizza alla pagina di dettaglio del ticket
	    attributes.addFlashAttribute("successMessage", "Ticket #" + ticketForm.getId() + " aggiornato con successo");

	    return "redirect:/tickets/show/" + ticketForm.getId();
	}
		
	// DELETE
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {

	    // Elimina il ticket tramite il suo ID
	    ticketService.deleteById(id);

	    // Aggiungi un messaggio di successo e reindirizza alla lista dei ticket
	    attributes.addFlashAttribute("successMessage", "Ticket #" + id + " eliminato con successo");

	    return "redirect:/tickets";
	}

}
