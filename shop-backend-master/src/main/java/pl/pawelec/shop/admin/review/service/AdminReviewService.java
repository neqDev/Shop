package pl.pawelec.shop.admin.review.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.admin.review.model.AdminReview;
import pl.pawelec.shop.admin.review.repository.AdminReviewRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminReviewService {
    private final AdminReviewRepository reviewRepository;

    public AdminReviewService(AdminReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<AdminReview> getReviews() {
        return reviewRepository.findAll();
    }

    @Transactional // to jest wymagane
    public void moderate(Long id) {
        reviewRepository.moderate(id);
    }
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
