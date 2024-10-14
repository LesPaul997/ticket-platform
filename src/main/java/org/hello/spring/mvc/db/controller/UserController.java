package org.hello.spring.mvc.db.controller;

import java.util.List;

import org.hello.spring.mvc.db.model.User;
import org.hello.spring.mvc.db.repo.UserRepository;
import org.hello.spring.mvc.dc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SuppressWarnings("unused")


@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	// Show
	@GetMapping("/index")
	public String index(Authentication authentication, Model model) {
		
		model.addAttribute("user", userService.getByUsername(authentication.getName()));
		return "/users/index";
	}
	
	
	@GetMapping("/show")
    public String showUserInfo(Model model, Authentication authentication) {
        // Recupera l'utente autenticato
        User loggedUser = userService.getByUsername(authentication.getName());

        // Aggiungi l'utente al modello
        model.addAttribute("user", loggedUser);

        // Restituisci la pagina 'show.html' dentro la cartella 'users'
        return "/users/show";
    }

	
	// Update
	@PostMapping("/edit")
	public String update(Authentication authentication, RedirectAttributes attributes, Model model) {
	    User userToUpdate = userService.getByUsername(authentication.getName());
	    boolean userStatus = userToUpdate.isStatus();
	    
	    // Verifichiamo se l'operatore ha ticket non completati
	    long openTickets = userToUpdate.getTickets().stream().filter(ticket -> !ticket.getStatus().equalsIgnoreCase("completato")).count();
	    
	    if (openTickets > 0) {
	        // Mantieni l'utente su `show.html` e mostra un messaggio di errore
	        model.addAttribute("user", userToUpdate); // Re-inserisci l'utente nel modello
	        model.addAttribute("errorMessage", "Errore: non puoi impostarti come 'non disponibile' finché hai ticket aperti.");
	        return "/users/show";  
	    }
	    
	    // Cambiamo lo stato dell'utente solo se non ha ticket aperti
	    userToUpdate.setStatus(!userStatus);
	    userService.save(userToUpdate);
	    
	    // Se l'aggiornamento va a buon fine, reindirizza alla pagina `index` con un messaggio di successo
	    attributes.addFlashAttribute("successMessage", "Stato aggiornato con successo!");
	    return "redirect:/users/index";
	}




	
}
