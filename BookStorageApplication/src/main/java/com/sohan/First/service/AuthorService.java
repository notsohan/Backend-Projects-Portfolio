package com.sohan.First.service;

import com.sohan.First.domain.Entities.AuthorEntity;
import com.sohan.First.domain.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity createAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();

    Optional<AuthorEntity> getAuthor(Long id);

    boolean ifExist(Long id);

    AuthorEntity partialService(Long id, AuthorEntity authorEntity);

    void delete(Long id);
}
