package org.hello.spring.mvc.db.repo;

import java.util.List;

import org.hello.spring.mvc.db.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByTitleContainingOrderByTitle(String title);
}
