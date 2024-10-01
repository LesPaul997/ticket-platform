package org.hello.spring.mvc.db.repo;

import java.util.Optional;

import org.hello.spring.mvc.db.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Integer>{

	public Optional<Operator> findByUsername(String title);
}
