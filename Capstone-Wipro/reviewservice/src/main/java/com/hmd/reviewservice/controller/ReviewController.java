package com.hmd.reviewservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmd.reviewservice.model.Review;
import com.hmd.reviewservice.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add a review for a specific book, passing bookId as a path variable
    @PostMapping("/books/{bookId}")
    public ResponseEntity<Review> addReview(@PathVariable Long bookId, @RequestBody Review review) {
        // Use the ReviewService to add a review after verifying the book exists via Feign
        Review createdReview = reviewService.addReview(bookId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // Get a review by its ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    // Update a review by its ID
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(reviewId, reviewDetails);
        return ResponseEntity.ok(updatedReview);
    }

    // Delete a review by its ID
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
    
 // Get all reviews for a specific book
    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable Long bookId) {
        List<Review> reviews = reviewService.getReviewsByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/user/{userId}")
    public List<Review> getUserReviews(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }
    
    @DeleteMapping("/book/{bookId}")
    public void deleteReviewsByBookId(@PathVariable Long bookId) {
        reviewService.deleteReviewsByBookId(bookId);
    }

}
