package com.example.controller;

import com.example.model.Todo;
import com.example.service.PersonService;
import com.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private PersonService personService;

    @GetMapping("/allTodos")
    public List<Todo> findAll() {
        //personService.save();
        return todoService.findAll();
    }

    @PostMapping("/saveTodo")
    public Todo save(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

    @GetMapping("/todoByTitle/{title}")
    public Todo findByTitle(@PathVariable("title") String title) {
        return todoService.findByTitle(title);
    }

    @GetMapping("/todosByNamed/{searchTerm}")
    public List<Todo> findBySearchTermNamed(@PathVariable("searchTerm") String searchTerm) {
        return todoService.findBySearchTermNamed(searchTerm);
    }

    @GetMapping("/todosByNamedNative/{searchTerm}")
    public List<Todo> findBySearchTermNamedNative(@PathVariable("searchTerm") String searchTerm) {
        return todoService.findBySearchTermNamedNative(searchTerm);
    }

    @GetMapping("/todos")
    public Page<Todo> findAllPaged(Pageable pageable) {
        return todoService.findAllPaged(pageable);
    }

    @GetMapping("/pagedTodos")
    public List<Todo> getPaymentByMemberId(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") final Integer pageNumber,@RequestParam(value = "pageSize", required = false, defaultValue = "50") final Integer pageSize) {
        PageRequest pageReq = new PageRequest(pageNumber, pageSize);
        Page<Todo> page = todoService.findAllPaged(pageReq);
        return page.getContent();
    }
}
