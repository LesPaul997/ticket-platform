package org.hello.spring.mvc.db.repo;

import org.hello.spring.mvc.db.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{

}
