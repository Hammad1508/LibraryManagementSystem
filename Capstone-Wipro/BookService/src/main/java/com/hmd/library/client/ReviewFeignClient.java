package com.hmd.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reviewservice", url = "http://localhost:8083/api/reviews")
public interface ReviewFeignClient {

    @DeleteMapping("/book/{bookId}")
    void deleteReviewsByBookId(@PathVariable Long bookId);
}

