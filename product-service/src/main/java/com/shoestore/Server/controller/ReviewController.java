package com.shoestore.Server.controller;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 3:54 PM
    ProjectName: Server
*/

import com.shoestore.Server.entities.Review;
import com.shoestore.Server.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllReview() {
        List<Review> reviews = reviewService.getAllReview();
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        boolean isDeleted = reviewService.deleteReview(id);
        if (isDeleted) {
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @PostMapping("/rating/{rating}")
    public ResponseEntity<Map<String,Object>> getReviewByRating(@PathVariable int rating) {
        List<Review> reviews = reviewService.getReviewByRating(rating);
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        return ResponseEntity.ok(response);
    }
}
