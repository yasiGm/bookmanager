package com.example.bookmanager.controller;

import com.example.bookmanager.dto.BookRequest;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBook_shouldReturn201AndBookResponse() throws Exception {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 1, 1);

        BookRequest request = BookRequest.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publishedDate(date)
                .build();

        Book savedBook = BookMapper.requestToEntity(request);
        savedBook.setId(id);

        when(bookService.createBook(Mockito.any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.publishedDate").value(date.toString()));
    }

    @Test
    public void testGetAllBooks_shouldReturnListOfBooks() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publisheDate(LocalDate.of(2023, 1, 1))
                .available(true)
                .build();

        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));
    }

    @Test
    public void testGetBookById_shouldReturnBook() throws Exception {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 1, 1);
        Book book = Book.builder()
                .id(id)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publisheDate(date)
                .available(true)
                .build();

        when(bookService.getBookById(id)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }

    @Test
    public void testGetBookById_shouldReturn404() throws Exception {
        Long id = 1L;

        when(bookService.getBookById(id)).thenThrow(new RuntimeException("Book not found with id: " + id));

        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBook_shouldReturn204() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/books/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateBook_shouldReturn200() throws Exception {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 1, 1);

        BookRequest request = BookRequest.builder()
                .title("Updated Book")
                .author("Updated Author")
                .isbn("0987654321")
                .publishedDate(date)
                .build();

        Book updatedBook = BookMapper.requestToEntity(request);
        updatedBook.setId(id);

        when(bookService.updateBook(Mockito.eq(id), Mockito.any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }
}