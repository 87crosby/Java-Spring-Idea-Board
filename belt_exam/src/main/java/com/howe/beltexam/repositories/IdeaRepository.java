package com.howe.beltexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.howe.beltexam.models.Idea;



@Repository
public interface IdeaRepository extends CrudRepository<Idea,Long> {

}
