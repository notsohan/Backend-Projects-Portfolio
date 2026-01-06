package com.sohan.First.controller;

import com.sohan.First.domain.Entities.BooksEntity;
import com.sohan.First.domain.dto.BooksDTO;
import com.sohan.First.mappers.Mapper;
import com.sohan.First.service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class BookController {

    private Mapper<BooksEntity, BooksDTO> bookMapper;
    private BooksService booksService;

    public BookController(Mapper<BooksEntity, BooksDTO> bookMapper, BooksService booksService){
        this.bookMapper = bookMapper;
        this.booksService = booksService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BooksDTO> createUpdateBook(
            @PathVariable String isbn,
            @RequestBody BooksDTO booksDTO) {

        boolean bookExist = booksService.ifExist(isbn);
        BooksEntity booksEntity = bookMapper.mapFrom(booksDTO);
        BooksEntity savedBook = booksService.createUpdateBook(isbn, booksEntity);
        BooksDTO savedBookDTO = bookMapper.mapTo(savedBook);

        return bookExist
                ? new ResponseEntity<>(savedBookDTO, HttpStatus.OK)
                : new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BooksDTO> listBooks() {
        List<BooksEntity> foundBooks = booksService.listBooks();
        return foundBooks.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{isbn}")
    public BooksDTO getOne(@PathVariable String isbn){
        Optional<BooksEntity> foundBook = booksService.getOne(isbn);
        return foundBook
                .map(bookMapper::mapTo)
                .orElse(null);
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BooksDTO> partialUpdate(@PathVariable String isbn,
                                                  @RequestBody BooksDTO booksDTO){

        if(!booksService.ifExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BooksEntity booksEntity = bookMapper.mapFrom(booksDTO);
        BooksEntity savedBook = booksService.partialUpdate(isbn, booksEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity delete(@PathVariable String isbn){
        booksService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
