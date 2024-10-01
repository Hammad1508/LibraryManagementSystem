package com.hmd.library.service;

import com.hmd.library.dto.BorrowDetails;
import com.hmd.library.dto.UserDTO;
import com.hmd.library.model.Book;
import com.hmd.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }

    @Test
    void testAddBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book foundBook = bookService.getBookById(1L);
        assertNotNull(foundBook);
        assertEquals(book.getTitle(), foundBook.getTitle());
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> books = bookService.getAllBooks();
        assertFalse(books.isEmpty());
    }

    @Test
    void testUpdateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book updatedBook = bookService.updateBook(1L, book);
        assertEquals(book.getTitle(), updatedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

   

    @Test
    void testBorrowBook() {
        BorrowDetails borrowDetails = new BorrowDetails();
        borrowDetails.setUserId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.borrowBook(1L, borrowDetails);

        assertTrue(book.isBorrowed());
        assertEquals(borrowDetails.getUserId(), book.getUserId());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook() {
        book.setBorrowed(true);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.returnBook(1L);

        assertFalse(book.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBooksByTitle() {
        when(bookRepository.findByTitle(anyString())).thenReturn(List.of(book));
        List<Book> books = bookService.getBooksByTitle("Test Book");
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    @Test
    void testGetBooksByAuthor() {
        when(bookRepository.findByAuthor(anyString())).thenReturn(List.of(book));
        List<Book> books = bookService.getBooksByAuthor("Test Author");
        assertEquals(1, books.size());
    }

    @Test
    void testGetBooksByAvailability() {
        when(bookRepository.findByBorrowed(false)).thenReturn(List.of(book));
        List<Book> books = bookService.getBooksByAvailability(false);
        assertEquals(1, books.size());
    }

    

   
}
