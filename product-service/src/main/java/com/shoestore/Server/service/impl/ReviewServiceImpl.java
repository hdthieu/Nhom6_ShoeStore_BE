package com.shoestore.Server.service.impl;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 3:53 PM
    ProjectName: Server
*/


import com.shoestore.Server.entities.Review;
import com.shoestore.Server.repositories.ReviewRepository;
import com.shoestore.Server.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    @Override
    public boolean deleteReview(int id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Review> getReviewByRating(int rating) {
        return reviewRepository.getAllReviewByRating(rating);
    }
}
