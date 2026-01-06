package com.sohan.First.mappers.impl;

import com.sohan.First.domain.Entities.BooksEntity;
import com.sohan.First.domain.dto.BooksDTO;
import com.sohan.First.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BooksMapper implements Mapper<BooksEntity, BooksDTO> {

    private ModelMapper modelMapper;
    public BooksMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public BooksDTO mapTo(BooksEntity booksEntity) {
        return modelMapper.map(booksEntity, BooksDTO.class);
    }

    @Override
    public BooksEntity mapFrom(BooksDTO booksDTO) {
        return modelMapper.map(booksDTO, BooksEntity.class);
    }
}
