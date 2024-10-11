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
	public String show(Authentication authentication, Model model) {
		
		model.addAttribute("user", userService.getByUsername(authentication.getName()));
		return "/users/index";
	}
	
	// Update
	@PostMapping("/edit")
	public String update(Authentication authentication, RedirectAttributes attributes) {
		
		User userToUpdate = userService.getByUsername(authentication.getName());
		boolean userStatus = userToUpdate.isStatus();
		
		if (userToUpdate.getInProgress() > 0) {
			attributes.addFlashAttribute("notSuccessMessage", "Errore, hai altri ticket aperti!'");
			return "redirect:/users/index";
		}
		
		userToUpdate.setStatus(!userStatus);
		userService.save(userToUpdate);
		
		return "redirect:/users/index";
	}
	
}
