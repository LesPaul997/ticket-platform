package org.hello.spring.mvc.dc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.Note;
import org.hello.spring.mvc.db.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class NoteService {

	@Autowired
	private NoteRepository repo;

	public Note getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public List<Note> getAll() {
		return repo.findAll();
	}
	
	public Optional<Note> getOptionalById(Integer id) {
		return repo.findById(id);
	}
	
	public void save(@Valid Note Note) {
		repo.save(Note);
	}
	
	public Note create(Note Note) {
		return repo.save(Note);
	}
	
	public Note update(Note Note) {
		Note.setUpdatedAt(LocalDateTime.now());
		return repo.save(Note);
	}
	
	public void delete(Note NoteToDelete) {
		repo.delete(NoteToDelete);
	}
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
}
