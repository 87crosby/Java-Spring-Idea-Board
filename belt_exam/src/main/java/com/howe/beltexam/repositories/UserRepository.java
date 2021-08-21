package com.howe.beltexam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.howe.beltexam.models.User;



@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	// Get all info from users
	List<User> findAll();
	
	// Get entry by email
	Optional<User> findByEmail(String email);
	
	// Get entry by ID
	Optional<User> findById(Long id);
	
	// Delete entry by ID
	void deleteById(Long id);
	
	// Saves data about entry
	<U extends User> U save(U user);

}
