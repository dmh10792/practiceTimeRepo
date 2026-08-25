package com.example.experiment.todo.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "todo")
public class TodoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String task;
  private Boolean done;

  void complete() {
    setDone(true);
  }

  void uncomplete() {
    setDone(false);
  }

}
