package com.sohan.First.service.impl;

import com.sohan.First.domain.Entities.AuthorEntity;
import com.sohan.First.repositories.AuthorRepo;
import com.sohan.First.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepo authorRepo;
    public AuthorServiceImpl(AuthorRepo authorRepo){
        this.authorRepo = authorRepo;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity author) {
        return authorRepo.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport
                .stream(authorRepo
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> getAuthor(Long id) {
        return authorRepo.findById(id);
    }

    @Override
    public boolean ifExist(Long id) {
        return authorRepo.existsById(id);
    }

    @Override
    public AuthorEntity partialService(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRepo.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorEntity.getName()).ifPresent((existingAuthor::setName));

            return authorRepo.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author doesn't not exist!"));
    }

    @Override
    public void delete(Long id) {
        authorRepo.deleteById(id);
    }
}
