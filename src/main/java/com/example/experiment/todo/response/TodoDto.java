package com.example.experiment.todo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
@AllArgsConstructor
public class TodoDto {
  private Long id;
  private String task;
  private Boolean done;
}
