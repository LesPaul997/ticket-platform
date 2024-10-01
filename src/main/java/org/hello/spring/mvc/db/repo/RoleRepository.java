package org.hello.spring.mvc.db.repo;

import org.hello.spring.mvc.db.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
