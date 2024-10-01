package com.hmd.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmd.library.client.ReviewFeignClient;
import com.hmd.library.client.UserServiceClient;
import com.hmd.library.dto.BorrowDetails;
import com.hmd.library.dto.UserDTO;
import com.hmd.library.model.Book;
import com.hmd.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @Autowired
    private ReviewFeignClient reviewFeignClient;  // Feign client to call review service
    
    

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setBorrowed(bookDetails.isBorrowed());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        
     // Delete the reviews from the review microservice before deleting the book
        reviewFeignClient.deleteReviewsByBookId(id);
        bookRepository.deleteById(id);
    }
    
    public void borrowBook(Long bookId, BorrowDetails borrowDetails) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new RuntimeException("Book not found with ID: " + bookId);
        }

        Book book = bookOptional.get();
        if (book.isBorrowed()) {
            throw new RuntimeException("Book is already borrowed");
        }

        book.setBorrowed(true);
        book.setUserId(borrowDetails.getUserId()); // Ensure the userId is set
        bookRepository.save(book);
        
    }

    public void returnBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new RuntimeException("Book not found with ID: " + bookId);
        }

        Book book = bookOptional.get();
        book.setBorrowed(false);
        bookRepository.save(book);
        
        
    }
    
 // Find books by title
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // Find books by author
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    // Find books by borrowed status (true = borrowed, false = available)
    public List<Book> getBooksByAvailability(boolean isBorrowed) {
        return bookRepository.findByBorrowed(isBorrowed);
    }
    
    public List<Book> getBooksBorrowedByUser(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    public Book borrowBook(Long bookId, Long userId) {
        // Fetch user details using Feign Client
        UserDTO user = userServiceClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.isBorrowed()) {
            throw new RuntimeException("Book is already borrowed");
        }

        // Set userId in the book and mark it as borrowed
        book.setBorrowed(true);
        book.setUserId(userId);
        return bookRepository.save(book);
    }
    
    
    
}