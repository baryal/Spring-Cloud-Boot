package com.example.service;

import com.example.model.Todo;
import com.example.repository.PagedTodoRepository;
import com.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private PagedTodoRepository pagedTodoRepository;

    @PostConstruct
    private void addTodos() {
        for(int i = 1; i <= 30; i++){
            Todo todo = new Todo();
            todo.setTitle("Test" + i);
            todo.setDescription("Description" + i);
            save(todo);
        }
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo save(Todo todo) {
        todo.setCreationTime(LocalDateTime.now());
        todo.setModificationTime(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    public Todo findByTitle(String title) {
        return todoRepository.findByTitle(title);
    }

    public List<Todo> findBySearchTermNamed(String searchTerm) {
        return todoRepository.findBySearchTermNamed(searchTerm);
    }

    public List<Todo> findBySearchTermNamedNative(String searchTerm) {
        return todoRepository.findBySearchTermNamedNative(searchTerm);
    }

    public Page<Todo> findAllPaged(Pageable pageable) {
        return pagedTodoRepository.findAll(pageable);
    }
}
