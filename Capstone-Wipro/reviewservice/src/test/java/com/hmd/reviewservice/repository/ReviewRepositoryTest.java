package com.hmd.reviewservice.repository;

import com.hmd.reviewservice.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
        reviewRepository.save(review);
    }


    
}
