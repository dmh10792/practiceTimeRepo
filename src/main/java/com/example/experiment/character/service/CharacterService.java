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

        return convertEntityToResponse(entity);
    }

    public List<CharacterResponse> getAllCharacters() {
      return characterJpaRepository.findAll().stream()
        .map(this::convertEntityToResponse).toList();
    }

    public CharacterResponse createCharacter(CharacterResponse characterDTO) {

        CharacterEntity savedEntity =  characterJpaRepository.save(convertResponseToEntity(characterDTO));

        return convertEntityToResponse(savedEntity);
    }

    private CharacterResponse convertEntityToResponse(CharacterEntity entity) {
        return CharacterResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .sex(entity.getSex())
                .species(entity.getSpecies())
                .position(entity.getPosition())
                .build();
    }

    private CharacterEntity convertResponseToEntity(CharacterResponse characterDTO) {
        return CharacterEntity.builder()
                .id(characterDTO.getId())
                .name(characterDTO.getName())
                .age(characterDTO.getAge())
                .sex(characterDTO.getSex())
                .species(characterDTO.getSpecies())
                .position(characterDTO.getPosition())
                .build();
    }
}
