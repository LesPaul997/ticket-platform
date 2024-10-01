package org.hello.spring.mvc.db.controller;

import java.util.List;

import org.hello.spring.mvc.db.model.User;
import org.hello.spring.mvc.db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserRepository repo;

	@GetMapping("/index")
	public String index(Model model) {
		
		List<User> operators = repo.findAll();
		model.addAttribute("users", operators);
		return "/users/index";
	}
}
