package com.hmd.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hmd.reviewservice.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Custom method to find reviews by bookId
    List<Review> findByBookId(Long bookId);
    
    List<Review> findByUserId(Long userId);
    
 // Custom delete method to remove all reviews by bookId
    void deleteByBookId(Long bookId);

}
