package com.hmd.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hmd.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books by title
    List<Book> findByTitle(String title);

    // Find books by author
    List<Book> findByAuthor(String author);

    // Find books by availability status (borrowed or available)
    List<Book> findByBorrowed(boolean borrowed);
    
    List<Book> findByUserId(Long userId);
}
