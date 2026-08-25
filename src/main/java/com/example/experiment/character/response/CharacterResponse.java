package com.example.experiment.character.response;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class CharacterResponse {
    private Long id;
    private String name;
    private Integer age;
    private String sex;
    private String species;

    private int position;
}
