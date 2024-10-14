package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserRepository repo;
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("name", "Mario");
		return "/pages/home";
	}
	
}
