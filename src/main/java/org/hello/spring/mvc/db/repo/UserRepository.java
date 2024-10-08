package org.hello.spring.mvc.db.repo;

import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String title);
	
	List<User> findByStatusTrue();

}
