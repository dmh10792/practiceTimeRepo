package com.example.experiment.todo.controller;

import com.example.experiment.todo.entity.TodoEntity;
import com.example.experiment.todo.response.TodoDto;
import com.example.experiment.todo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
  private final TodoService todoService;

  @GetMapping("/all")
  public List<TodoDto> getAllTodos() {
    log.info("In TodoController, getting all todos");
    return todoService.getAllTodos();
  }

  @PostMapping("/create")
  public TodoDto createTodo(
    @RequestBody TodoDto newTodo
  ) {
    TodoEntity createdTodo = todoService.createTodo(newTodo);
    return TodoDto.builder()
      .id(createdTodo.getId())
      .task(createdTodo.getTask())
      .done(createdTodo.getDone())
      .build();
  }

  @PostMapping("/toggle")
  public TodoDto toggleTodo(
    @RequestBody Long id
  ) {
    TodoEntity clickedTodo = todoService.toggleTodo(id);
    return TodoDto.builder()
      .id(clickedTodo.getId())
      .task(clickedTodo.getTask())
      .done(clickedTodo.getDone())
      .build();
  }
}
