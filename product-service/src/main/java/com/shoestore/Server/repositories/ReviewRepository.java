package com.shoestore.Server.repositories;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 3:50 PM
    ProjectName: Server
*/

import com.shoestore.Server.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT r FROM Review r WHERE r.start = :rating")
    List<Review> getAllReviewByRating(@Param("rating") int rating);
}
