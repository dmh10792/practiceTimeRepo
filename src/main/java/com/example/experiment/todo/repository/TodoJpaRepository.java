package com.example.experiment.todo.repository;

import com.example.experiment.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {

}
