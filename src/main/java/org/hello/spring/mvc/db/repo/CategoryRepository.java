package org.hello.spring.mvc.db.repo;

import org.hello.spring.mvc.db.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
