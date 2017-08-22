package com.example.repository;

import com.example.model.Todo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagedTodoRepository extends PagingAndSortingRepository<Todo, Long>{

}
