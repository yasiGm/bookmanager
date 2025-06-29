package com.example.bookmanager.service;

import com.example.bookmanager.entity.Book;
import com.example.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testCreateBook_ShouldReturnSavedBook() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publisheDate(LocalDate.now())
                .available(true)
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.createBook(book);
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getPublisheDate(), result.getPublisheDate());
        assertEquals(book.isAvailable(), result.isAvailable());
    }

    @Test
    void testGetBookById_ShouldReturnBookIfExist() {
        Long id = 1L;

        Book book = Book.builder()
                .id(id)
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publisheDate(LocalDate.now())
                .available(true)
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Book result = bookService.getBookById(id);
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());

    }
    @Test
    void getBookById_ShouldThrowExceptionIfNotExist() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception=assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(id);
        });
            assertEquals("Book not found with id: " + id, exception.getMessage());
        }

    @Test
    void testUpdateBook_ShouldReturnUpdatedBook() {
        Long id = 1L;
        Book oldBook = Book.builder()
                .id(id)
                .title("Old Title")
                .author("Old Author")
                .isbn("1234567890")
                .publisheDate(LocalDate.now())
                .available(true)
                .build();

        Book updatedBook = Book.builder()
                .title("Updated Title")
                .author("Updated Author")
                .isbn("0987654321")
                .publisheDate(LocalDate.now())
                .available(false)
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(oldBook));
        when(bookRepository.save(oldBook)).thenReturn(oldBook);

        Book result = bookService.updateBook(id, updatedBook);
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
        assertEquals(updatedBook.getIsbn(), result.getIsbn());
        assertEquals(updatedBook.getPublisheDate(), result.getPublisheDate());
        assertEquals(updatedBook.isAvailable(), result.isAvailable());
    }
    @Test
    void testDeleteBook_ShouldDeleteBookIfExist() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);

        bookService.deleteBook(id);
    }
    @Test
    void testDeleteBook_ShouldThrowExceptionIfNotExist() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(id);
        });
        assertEquals("Book not found with id: " + id, exception.getMessage());
    }
    @Test
    void testGetAllBooks_ShouldReturnListOfBooks() {
        Book book1 = Book.builder()
                .id(1L)
                .title("Book One")
                .author("Author One")
                .isbn("1111111111")
                .publisheDate(LocalDate.now())
                .available(true)
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Book Two")
                .author("Author Two")
                .isbn("2222222222")
                .publisheDate(LocalDate.now())
                .available(false)
                .build();

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));
        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
        assertEquals(book1.getTitle(), result.get(0).getTitle());
        assertEquals(book2.getTitle(), result.get(1).getTitle());
    }
}
