package com.example.bookmanager.service;

import com.example.bookmanager.dto.BookRequest;
import com.example.bookmanager.entity.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    Book updateBook(Long id, Book updatedBook);
    void deleteBook(Long id);
    List<Book> getAllBooks();
}
