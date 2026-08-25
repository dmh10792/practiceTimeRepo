package com.example.experiment.character;

import com.example.experiment.character.entity.CharacterEntity;
import com.example.experiment.character.repository.CharacterJpaRepository;
import com.example.experiment.character.response.CharacterResponse;
import com.example.experiment.character.service.CharacterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @InjectMocks
    CharacterService characterService;

    @Mock
    CharacterJpaRepository characterJpaRepository;

    CharacterEntity testCharacter1 = CharacterEntity.builder()
            .id(1L)
            .name("Testor 1")
            .age(22)
            .position(1)
            .species("Tau")
            .sex("Tau M")
            .build();

    CharacterEntity testCharacter2 = CharacterEntity.builder()
            .id(2L)
            .name("Testor 2")
            .age(25)
            .position(2)
            .species("Eldar")
            .sex("Eldar W")
            .build();

    @Test
    void shouldGetAllCharacters() {
        when(characterJpaRepository.findAll()).thenReturn(List.of(testCharacter1, testCharacter2));

        List<CharacterResponse> response = characterService.getAllCharacters();

        assertThat(response.size()).isEqualTo(2);
        assertThat(response.getFirst().getName()).isEqualTo(testCharacter1.getName());
        assertThat(response.get(1).getName()).isEqualTo(testCharacter2.getName());
    }
}
