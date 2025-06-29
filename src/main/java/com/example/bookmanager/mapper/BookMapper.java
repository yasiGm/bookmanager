package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.BookRequest;
import com.example.bookmanager.dto.BookResponse;
import com.example.bookmanager.entity.Book;

public class BookMapper {

    public static Book requestToEntity(BookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publisheDate(request.getPublishedDate())
                .available(true) // Default to available
                .build();
    }

    public static BookResponse entityToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedDate(book.getPublisheDate())
                .build();
    }
}
