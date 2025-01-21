package pl.pawelec.shop.admin.review.controller;

import org.springframework.web.bind.annotation.*;
import pl.pawelec.shop.admin.review.model.AdminReview;
import pl.pawelec.shop.admin.review.service.AdminReviewService;

import java.util.List;

@RestController
@RequestMapping("/admin/reviews")
public class AdminReviewController {
    private final AdminReviewService reviewService;

    public AdminReviewController(AdminReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<AdminReview> getReviews(){
        return reviewService.getReviews();
    }
    @PutMapping("/{id}/moderate")
    public void moderate(@PathVariable Long id){
        reviewService.moderate(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        reviewService.delete(id);
    }
}
