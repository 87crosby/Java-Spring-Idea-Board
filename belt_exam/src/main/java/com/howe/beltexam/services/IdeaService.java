package com.howe.beltexam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howe.beltexam.models.Idea;
import com.howe.beltexam.repositories.IdeaRepository;


@Service
public class IdeaService {
	
	@Autowired
    private IdeaRepository ideaRepo;
	
	public List<Idea> findAllIdeas(){
		return (List<Idea>) this.ideaRepo.findAll();
	}
	
	public Idea createIdea(Idea idea) {
		return this.ideaRepo.save(idea);
	}
	
	public Idea getIdea(Long id) {
		return this.ideaRepo.findById(id).orElse(null);
	}
	
	public Idea updateIdea(Idea idea) {
		return this.ideaRepo.save(idea);
	}
	
	public void deleteIdea(Long id) {
		this.ideaRepo.deleteById(id);
	}

}
