package com.example.repository;

import com.example.model.hibernateInheritance.tableperclass.Person1;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person1, Long>{

}
