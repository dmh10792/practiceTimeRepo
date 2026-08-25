package com.example.experiment.character.repository;

import com.example.experiment.character.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterJpaRepository extends JpaRepository<CharacterEntity, Long> {
    CharacterEntity findByName(String name);

    // findAll() is automatically provided by JPA

}