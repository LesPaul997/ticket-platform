package org.hello.spring.mvc.db.repo;

import org.hello.spring.mvc.db.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
