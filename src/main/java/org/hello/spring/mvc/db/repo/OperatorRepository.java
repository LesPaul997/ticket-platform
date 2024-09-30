package org.hello.spring.mvc.db.repo;

import org.hello.spring.mvc.db.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Integer>{

	
}
