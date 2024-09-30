package org.hello.spring.mvc.db.controller;

import java.util.List;

import org.hello.spring.mvc.db.model.Operator;
import org.hello.spring.mvc.db.repo.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class OperatorController {
	
	@Autowired
	private OperatorRepository repo;

	@GetMapping("/")
	public String index(Model model) {
		
		List<Operator> operators = repo.findAll();
		model.addAttribute("operators", operators);
		return "index";
	}
}
