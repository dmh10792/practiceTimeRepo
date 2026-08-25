package com.example.experiment.character;

import com.example.experiment.character.controller.CharacterController;
import com.example.experiment.character.response.CharacterResponse;
import com.example.experiment.character.service.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
@AutoConfigureMockMvc(addFilters = false)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    @Autowired
    ObjectMapper objectMapper;

    CharacterResponse testCharacter = CharacterResponse.builder()
            .name("Testor 1")
            .age(25)
            .position(1)
            .species("Eldar")
            .sex("Eldar W")
            .build();

    @Test
    void shouldGetAllCharacters() throws Exception {
        when(characterService.getAllCharacters()).thenReturn(List.of(testCharacter));

        mockMvc.perform(get("/api/character/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateNewCharacter() throws Exception {
        CharacterResponse returnedCharacter = testCharacter.toBuilder().id(1L).build();
        when(characterService.createCharacter(any(CharacterResponse.class))).thenReturn(returnedCharacter);

        mockMvc.perform(post("/api/character")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testCharacter)))
                .andExpect(status().isCreated());

        verify(characterService, times(1)).createCharacter(any(CharacterResponse.class));
    }
}
