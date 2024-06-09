package com.knf.dev.demo;

import com.knf.dev.demo.entity.Todo;
import com.knf.dev.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoServiceApplication {

    private final TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(TodoServiceApplication.class, args);
    }

    //Load dummy data
    @Bean
    public CommandLineRunner commandLineRunner() {
        if (todoRepository.count() == 0) {
            Todo todo = Todo.builder()
                    .email("alpha@knf.com")
                    .gender("Male")
                    .firstName("Alpha")
                    .lastName("Pro")
                    .build();
            return (args) -> todoRepository.save(todo);
        }
        return (args) -> System.out.println();
    }
}
