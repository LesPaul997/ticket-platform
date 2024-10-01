package org.hello.spring.mvc.dc.service;

import org.hello.spring.mvc.db.model.Role;
import org.hello.spring.mvc.db.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	public RoleRepository roleRepository;

	public void createRoles() {
		Role adminRole = new Role("ADMIN");
		Role operatorRole = new Role("OPERATOR");
		
		roleRepository.save(adminRole);
		roleRepository.save(operatorRole);
	}

}
