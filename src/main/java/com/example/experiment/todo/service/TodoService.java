package com.example.experiment.todo.service;

import com.example.experiment.todo.entity.TodoEntity;
import com.example.experiment.todo.repository.TodoJpaRepository;
import com.example.experiment.todo.response.TodoDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TodoService {

  private final TodoJpaRepository todoJpaRepository;

  public TodoDto getTodo(Long id) {
    log.info("In TodoService, getting todo by id {}", id);
    return todoJpaRepository.findById(id)
      .map(entity -> TodoDto.builder()
        .id(entity.getId())
        .task(entity.getTask())
        .done(entity.getDone())
        .build())
      .orElse(null);
  }

  public List<TodoDto> getAllTodos() {
    log.info("In TodoService, getting all todos");
    return todoJpaRepository.findAll().stream()
      .map(
        entity -> TodoDto.builder()
          .id(entity.getId())
          .task(entity.getTask())
          .done(entity.getDone())
          .build()
      ).toList();
  }

  public TodoEntity createTodo(TodoDto todo) {
    TodoEntity objectToCreate = TodoEntity.builder()
        .id(todo.getId())
          .task(todo.getTask())
            .done(false)
              .build();

    log.info("In TodoService, creating todo {}", objectToCreate.toString());
    return todoJpaRepository.save(objectToCreate);
  }

  public TodoEntity toggleTodo(Long id) {
    TodoEntity entity = todoJpaRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Todo not found"));

    entity.setDone(!entity.getDone());

    return todoJpaRepository.save(entity);
  }
}
