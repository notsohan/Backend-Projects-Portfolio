package com.sohan.First.repositories;

import com.sohan.First.domain.Entities.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepos extends JpaRepository<BooksEntity, String> {
    Optional<BooksEntity> findByIsbn(String isbn);
}
