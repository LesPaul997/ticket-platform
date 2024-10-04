package org.hello.spring.mvc.dc.service;

import java.util.List;
import java.util.Optional;

import org.hello.spring.mvc.db.model.Category;
import org.hello.spring.mvc.db.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public Category getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public Optional<Category> getOptionalById(Integer id) {
		return repo.findById(id);
	}
	
	public List<Category> getAll() {
		return repo.findAll();
	}
	
	public void save(@Valid Category ticket) {
		repo.save(ticket);
	}
	
	public Category create(Category ticket) {
		return repo.save(ticket);
	}
	
	public void delete(Category categoryToDelete) {
		repo.delete(categoryToDelete);
	}
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
}
