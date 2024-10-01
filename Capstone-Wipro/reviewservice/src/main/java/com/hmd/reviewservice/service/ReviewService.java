package com.hmd.reviewservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmd.reviewservice.client.BookServiceClient;
import com.hmd.reviewservice.dto.BookDTO;
import com.hmd.reviewservice.model.Review;
import com.hmd.reviewservice.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookServiceClient bookServiceClient; // Injecting Feign Client to call Book Service
    
   

    // Add a review, but first check if the book exists using Feign Client
    public Review addReview(Long bookId, Review review) {
        // Call Book Service to ensure the book exists
        BookDTO book = bookServiceClient.getBookById(bookId);

        if (book == null) {
            throw new RuntimeException("Book not found with ID: " + bookId);
        }

        // Set the bookId in the review and save it
        review.setBookId(bookId); // Assuming Review has a bookId field
        return reviewRepository.save(review);
        
        
    }

    // Retrieve all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get a review by ID
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    // Update an existing review
    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setReviewer(reviewDetails.getReviewer());

        return reviewRepository.save(review);
    }

    // Delete a review by ID
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // Get all reviews for a specific book by bookId
    public List<Review> getReviewsByBookId(Long bookId) {
        // Call Book Service to ensure the book exists
        BookDTO book = bookServiceClient.getBookById(bookId);

        if (book == null) {
            throw new RuntimeException("Book not found with ID: " + bookId);
        }

        // Fetch reviews from the repository for the bookId
        return reviewRepository.findByBookId(bookId);
    }

    // Get all reviews written by a specific user
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    
    @Transactional // Ensures this method runs within a transaction
    public void deleteReviewsByBookId(Long bookId) {
        reviewRepository.deleteByBookId(bookId);
    }
}
