package com.example.bookmanager.controller;

import com.example.bookmanager.dto.BookRequest;
import com.example.bookmanager.dto.BookResponse;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService=bookService;
    }
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest){
        Book book = BookMapper.requestToEntity(bookRequest);
        Book savedBook = bookService.createBook(book);
        BookResponse bookResponse = BookMapper.entityToResponse(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookResponse> responseList = books.stream()
                .map(BookMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@Valid @PathVariable Long id){
        Book book = bookService.getBookById(id);
        BookResponse bookResponse = BookMapper.entityToResponse(book);
        return ResponseEntity.ok(bookResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@Valid @PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook ( @PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = BookMapper.requestToEntity(bookRequest);
        Book savedBook = bookService.updateBook(id, updatedBook);
        BookResponse bookResponse = BookMapper.entityToResponse(savedBook);
        return ResponseEntity.ok(bookResponse);
    }
}
