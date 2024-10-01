package org.hello.spring.mvc.db.controller;

import org.hello.spring.mvc.dc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/createRoles")
	public String createRoles() {
		roleService.createRoles();
		return "Ruolo creato!";
	}
}
