package com.hmd.library.controller;

import com.hmd.library.dto.BorrowDetails;
import com.hmd.library.model.Book;
import com.hmd.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }

    @Test
    void testGetAllBooks() {
        when(bookService.getAllBooks()).thenReturn(List.of(book));
        List<Book> books = bookController.getAllBooks();
        assertEquals(1, books.size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        when(bookService.getBookById(1L)).thenReturn(book);
        ResponseEntity<Book> response = bookController.getBookById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void testAddBook() {
        when(bookService.addBook(any(Book.class))).thenReturn(book);
        ResponseEntity<Book> response = bookController.addBook(book);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void testUpdateBook() {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);
        ResponseEntity<Book> response = bookController.updateBook(1L, book);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookService).deleteBook(1L);
        ResponseEntity<Void> response = bookController.deleteBook(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void testBorrowBook() {
        doNothing().when(bookService).borrowBook(anyLong(), any(BorrowDetails.class));
        ResponseEntity<Void> response = bookController.borrowBook(1L, new BorrowDetails());
        assertEquals(200, response.getStatusCodeValue());
        verify(bookService, times(1)).borrowBook(eq(1L), any(BorrowDetails.class));
    }

    @Test
    void testReturnBook() {
        doNothing().when(bookService).returnBook(1L);
        ResponseEntity<Void> response = bookController.returnBook(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(bookService, times(1)).returnBook(1L);
    }

    // Additional Test Cases for new methods

    @Test
    void testGetBooksByTitle() {
        when(bookService.getBooksByTitle("Test Book")).thenReturn(List.of(book));
        ResponseEntity<List<Book>> response = bookController.getBooksByTitle("Test Book");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByTitle("Test Book");
    }

    @Test
    void testGetBooksByAuthor() {
        when(bookService.getBooksByAuthor("Test Author")).thenReturn(List.of(book));
        ResponseEntity<List<Book>> response = bookController.getBooksByAuthor("Test Author");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByAuthor("Test Author");
    }

    @Test
    void testGetBooksByAvailability() {
        when(bookService.getBooksByAvailability(false)).thenReturn(List.of(book));
        ResponseEntity<List<Book>> response = bookController.getBooksByAvailability(false);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksByAvailability(false);
    }

    @Test
    void testGetBooksBorrowedByUser() {
        when(bookService.getBooksBorrowedByUser(1L)).thenReturn(List.of(book));
        ResponseEntity<List<Book>> response = bookController.getBooksBorrowedByUser(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService, times(1)).getBooksBorrowedByUser(1L);
    }
}
