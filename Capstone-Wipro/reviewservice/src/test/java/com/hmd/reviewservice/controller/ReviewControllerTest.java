package com.hmd.reviewservice.controller;

import com.hmd.reviewservice.model.Review;
import com.hmd.reviewservice.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        review = new Review();
    }

    @Test
    void testGetReviewById() {
        when(reviewService.getReviewById(1L)).thenReturn(review);
        ResponseEntity<Review> response = reviewController.getReviewById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetReviewsByBookId() {
        when(reviewService.getReviewsByBookId(101L)).thenReturn(List.of(review));
        ResponseEntity<List<Review>> response = reviewController.getReviewsByBookId(101L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testUpdateReview() {
        when(reviewService.updateReview(1L, review)).thenReturn(review);
        ResponseEntity<Review> response = reviewController.updateReview(1L, review);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // New Test Cases
    @Test
    void testAddReview() {
        when(reviewService.addReview(eq(101L), any(Review.class))).thenReturn(review);
        ResponseEntity<Review> response = reviewController.addReview(101L, review);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteReview() {
        doNothing().when(reviewService).deleteReview(1L);
        ResponseEntity<String> response = reviewController.deleteReview(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review deleted successfully", response.getBody());
    }

    @Test
    void testGetAllReviews() {
        when(reviewService.getAllReviews()).thenReturn(List.of(review));
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetReviewsByUserId() {
        when(reviewService.getReviewsByUserId(1L)).thenReturn(List.of(review));
        List<Review> response = reviewController.getUserReviews(1L);
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    void testDeleteReviewsByBookId() {
        doNothing().when(reviewService).deleteReviewsByBookId(101L);
        reviewController.deleteReviewsByBookId(101L);
        verify(reviewService, times(1)).deleteReviewsByBookId(101L);
    }
}
