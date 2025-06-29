package com.example.bookmanager.service;

import com.example.bookmanager.dto.BookRequest;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.repository.BookRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        Book oldBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        oldBook.setTitle(updatedBook.getTitle());
        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setIsbn(updatedBook.getIsbn());
        oldBook.setPublisheDate(updatedBook.getPublisheDate());
        oldBook.setAvailable(updatedBook.isAvailable());
        return bookRepository.save(oldBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
