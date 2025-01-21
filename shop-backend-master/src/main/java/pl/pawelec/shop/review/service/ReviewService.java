package pl.pawelec.shop.review.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.common.model.Review;
import pl.pawelec.shop.common.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review){
        return reviewRepository.save(review);
    }
}
