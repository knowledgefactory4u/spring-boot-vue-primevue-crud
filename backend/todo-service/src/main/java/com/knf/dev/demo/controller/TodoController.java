package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.Todo;
import com.knf.dev.demo.exception.ResourceNotFoundException;
import com.knf.dev.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;


    @GetMapping
    public ResponseEntity<List<Todo>> getAllTdo() {
        return ResponseEntity.ok(todoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable(value = "id")
                                 Long id) throws ResourceNotFoundException {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Todo not found for this id :: " + id));
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {

        return ResponseEntity.ok(todoRepository.save(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable(value = "id")
                                           Long id, @RequestBody Todo todoDto)
            throws ResourceNotFoundException {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Todo not found for this id :: " + id));

        todo.setEmail(todoDto.getEmail());
        todo.setLastName(todoDto.getLastName());
        todo.setFirstName(todoDto.getFirstName());
        todo.setGender(todoDto.getGender());
        todo.setId(id);
        final Todo updateTodo = todoRepository.save(todo);
        return ResponseEntity.ok(updateTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTodo(@PathVariable(value = "id")
                                              Long id) throws ResourceNotFoundException {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Todo not found for this id :: " + id));

        todoRepository.delete(todo);

        return ResponseEntity.ok(true);
    }
}