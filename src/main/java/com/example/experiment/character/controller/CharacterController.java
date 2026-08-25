package com.example.experiment.character.controller;

import com.example.experiment.character.response.CharacterResponse;
import com.example.experiment.character.service.CharacterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/character")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<CharacterResponse> getCharacterbyName(@RequestParam String name) {
        log.info("In CharacterController, getting character by name {}", name);
        return ResponseEntity.ok(characterService.getCharacterByName(name));
    }

    @GetMapping("/all")
    public List<CharacterResponse> getAllCharacters() {
      log.info("In CharacterController, getting all characters");
      return characterService.getAllCharacters();
    }
}
