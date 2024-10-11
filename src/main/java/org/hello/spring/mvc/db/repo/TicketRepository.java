package org.hello.spring.mvc.db.repo;

import java.util.List;

import org.hello.spring.mvc.db.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByTitleContainingOrderByTitle(String title);
	
	List<Ticket> findByStatus(String status);
	
	List<Ticket> findByCategory_Name(String categoryName);
	
	List<Ticket> findByUserId(Integer userId);

}
