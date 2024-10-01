package com.hmd.reviewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hmd.reviewservice.dto.BookDTO;

@FeignClient(name = "BookService", url = "http://localhost:8081/api/books")
public interface BookServiceClient {

    @GetMapping("/{id}")
    BookDTO getBookById(@PathVariable Long id); // Feign client to fetch a book by ID from Book Service
}
