package com.example.experiment.character;

import com.example.experiment.character.controller.CharacterController;
import com.example.experiment.character.response.CharacterResponse;
import com.example.experiment.character.service.CharacterService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
@AutoConfigureMockMvc(addFilters = false)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    private Cookie csrfCookie;

    CharacterResponse testCharacter = CharacterResponse.builder().build();

    @Test
    void shouldGetAllCharacters() throws Exception {
        when(characterService.getAllCharacters()).thenReturn(List.of(testCharacter));

        mockMvc.perform(get("/api/character/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
