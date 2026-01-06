package com.sohan.First.service.impl;

import com.sohan.First.domain.Entities.AuthorEntity;
import com.sohan.First.domain.Entities.BooksEntity;
import com.sohan.First.repositories.AuthorRepo;
import com.sohan.First.repositories.BookRepos;
import com.sohan.First.service.BooksService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BooksServiceImpl implements BooksService {

    private BookRepos bookRepos;
    private AuthorRepo authorRepo;

    public BooksServiceImpl(BookRepos bookRepos, AuthorRepo authorRepo){
        this.bookRepos = bookRepos;
        this.authorRepo = authorRepo;
    }

    @Override
    public BooksEntity createUpdateBook(String isbn, BooksEntity books) {
        books.setIsbn(isbn);
        if(books.getAuthor() != null){
            AuthorEntity authorEntity = books.getAuthor();
            Optional<AuthorEntity> existingAuthor = Optional.empty();

            if(authorEntity.getId() != null){
                existingAuthor = authorRepo.findById(authorEntity.getId());
            }else if(authorEntity.getName() != null){
                existingAuthor = authorRepo.findByName(authorEntity.getName());
            }

            existingAuthor.ifPresent(books::setAuthor);
        }
        return bookRepos.save(books);
    }

    @Override
    public List<BooksEntity> listBooks() {
        return StreamSupport.stream(bookRepos
                .findAll()
                .spliterator()
                ,false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<BooksEntity> getOne(String isbn) {
        return bookRepos.findByIsbn(isbn);
    }

    @Override
    public boolean ifExist(String isbn) {
        return bookRepos.existsById(isbn);
    }

    @Override
    public BooksEntity partialUpdate(String isbn, BooksEntity booksEntity) {
        booksEntity.setIsbn(isbn);

        return bookRepos.findByIsbn(isbn).map(existingBook -> {
            Optional.ofNullable(booksEntity.getTitle()).ifPresent(existingBook::setTitle);

            return bookRepos.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        bookRepos.deleteById(isbn);
    }
}
