package com.sohan.First.repositories;

import com.sohan.First.domain.Entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByName(String name);
}
