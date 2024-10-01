package com.hmd.reviewservice.service;

import com.hmd.reviewservice.model.Review;
import com.hmd.reviewservice.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        review = new Review();
        review.setComment("Great Book");
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Review foundReview = reviewService.getReviewById(1L);
        assertNotNull(foundReview);
        assertEquals(review.getComment(), foundReview.getComment());
    }

    @Test
    void testDeleteReview() {
        reviewService.deleteReview(1L);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

  

    @Test
    void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));
        List<Review> reviews = reviewService.getAllReviews();
        assertFalse(reviews.isEmpty());
    }

    @Test
    void testUpdateReview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        Review updatedReview = reviewService.updateReview(1L, review);
        assertNotNull(updatedReview);
        assertEquals(review.getComment(), updatedReview.getComment());
    }

   

    @Test
    void testGetReviewsByUserId() {
        when(reviewRepository.findByUserId(1L)).thenReturn(List.of(review));
        List<Review> reviews = reviewService.getReviewsByUserId(1L);
        assertFalse(reviews.isEmpty());
    }

    @Test
    void testDeleteReviewsByBookId() {
        doNothing().when(reviewRepository).deleteByBookId(101L);
        reviewService.deleteReviewsByBookId(101L);
        verify(reviewRepository, times(1)).deleteByBookId(101L);
    }
}
