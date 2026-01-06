package com.sohan.First.controller;

import com.sohan.First.domain.Entities.AuthorEntity;
import com.sohan.First.domain.dto.AuthorDTO;
import com.sohan.First.mappers.Mapper;
import com.sohan.First.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class AuthorController {

    private final AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDTO> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDTO> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);
        AuthorEntity savedAuthor = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDTO>> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        List<AuthorDTO> collect = authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }

    @GetMapping(path = "/author/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable("id") Long id){
         Optional<AuthorEntity> foundAuthor = authorService.getAuthor(id);
         return foundAuthor
                .map(authorMapper::mapTo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> fullUpdateAuthor(@PathVariable("id") Long id,
                                                     @RequestBody AuthorDTO authorDTO){
        if(!authorService.ifExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDTO.setId(id);
        AuthorEntity savedAuthor = authorMapper.mapFrom(authorDTO);
        authorService.createAuthor(savedAuthor);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDTO> partialUpdate(@PathVariable("id") Long id,
                                                   @RequestBody AuthorDTO authorDTO){
        if(!authorService.ifExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity foundEntity = authorMapper.mapFrom(authorDTO);
        AuthorEntity updatedAuthor = authorService.partialService(id, foundEntity);

        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
