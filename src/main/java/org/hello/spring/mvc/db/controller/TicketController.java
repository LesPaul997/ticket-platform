package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.dc.service.UserService;

import java.util.List;

import org.hello.spring.mvc.db.model.Ticket;
import org.hello.spring.mvc.dc.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
		
	// STORE
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("operators", userService.getAll());
			return "/tickets/create";
		}

		ticketService.save(ticketForm);
		attributes.addFlashAttribute("successMessage", "ticket " + ticketForm.getId() + " creato con successo");

		return "redirect:/tickets";
	}

	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {

		Ticket ticketToEdit = ticketService.getById(id);
		
		model.addAttribute("ticket", ticketToEdit);
		model.addAttribute("operators", userService.getAll());

		return "tickets/edit";
	}
		
	
	// UPDATE
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("operators", userService.getAll());
			return "/tickets/edit";
		}

		ticketService.save(ticketForm);
		attributes.addFlashAttribute("successMessage", "ticket " + ticketForm.getId() + " modificato con successo");

		return "redirect:/tickets";
	}
		
	// DELETE
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {
		
		ticketService.deleteById(id);
		attributes.addFlashAttribute("successMessage", "ticket #" + id + " eliminato con successo");
		
		return "redirect:/tickets";
	}

}
