package com.sohan.First.service;

import com.sohan.First.domain.Entities.BooksEntity;

import java.util.List;
import java.util.Optional;

public interface BooksService {

    BooksEntity createUpdateBook(String isbn, BooksEntity books);
    List<BooksEntity> listBooks();

    Optional<BooksEntity> getOne(String isbn);

    boolean ifExist(String isbn);

    BooksEntity partialUpdate(String isbn, BooksEntity booksEntity);

    void delete(String isbn);
}
