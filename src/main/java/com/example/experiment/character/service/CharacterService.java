package com.example.experiment.character.service;

import com.example.experiment.character.entity.CharacterEntity;
import com.example.experiment.character.response.CharacterResponse;
import com.example.experiment.character.repository.CharacterJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CharacterService {

    private final CharacterJpaRepository characterJpaRepository;

    public CharacterResponse getCharacterByName(String name) {
        CharacterEntity entity = characterJpaRepository.findByName(name);

        return CharacterResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .sex(entity.getSex())
                .species(entity.getSpecies())
                .position(entity.getPosition())
                .build();
    }

    public List<CharacterResponse> getAllCharacters() {
      return characterJpaRepository.findAll().stream()
        .map(
          entity -> CharacterResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .age(entity.getAge())
            .sex(entity.getSex())
            .species(entity.getSpecies())
            .position(entity.getPosition())
            .build()
        ).toList();
    }
}
