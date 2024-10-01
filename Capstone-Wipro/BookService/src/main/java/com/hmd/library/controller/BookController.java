package com.hmd.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmd.library.dto.BorrowDetails;
import com.hmd.library.model.Book;
import com.hmd.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/borrow")
    public ResponseEntity<Void> borrowBook(@PathVariable Long id, @RequestBody BorrowDetails borrowDetails) {
        bookService.borrowBook(id, borrowDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        bookService.returnBook(id);
        return ResponseEntity.ok().build();
    }
    
 // Find books by title
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    // Find books by author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    // Find books by availability status
    @GetMapping("/availability/{isBorrowed}")
    public ResponseEntity<List<Book>> getBooksByAvailability(@PathVariable boolean isBorrowed) {
        List<Book> books = bookService.getBooksByAvailability(isBorrowed);
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/user/{userId}/books")
    public ResponseEntity<List<Book>> getBooksBorrowedByUser(@PathVariable Long userId) {
        List<Book> books = bookService.getBooksBorrowedByUser(userId);
        return ResponseEntity.ok(books);
    }

}
