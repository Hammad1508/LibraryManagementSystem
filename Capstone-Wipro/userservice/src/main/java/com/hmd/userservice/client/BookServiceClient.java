package com.hmd.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hmd.userservice.dto.BookDTO;
import com.hmd.userservice.dto.BorrowDetails;

@FeignClient(name = "BookService", url = "http://localhost:8081/api/books")
public interface BookServiceClient {

	@GetMapping("/{id}")
    BookDTO getBookById(@PathVariable Long id);

    // Ensure we only pass @RequestBody for BorrowDetails without form parameters
    @PutMapping("/{id}/borrow")
    void borrowBook(@PathVariable("id") Long bookId, @RequestBody BorrowDetails borrowDetails);
    
    // New method to return a book
    @PutMapping("/{id}/return")
    void returnBook(@PathVariable Long id);
}
