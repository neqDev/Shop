package pl.pawelec.shop.review.controller;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pawelec.shop.review.controller.dto.ReviewDto;
import pl.pawelec.shop.common.model.Review;
import pl.pawelec.shop.review.service.ReviewService;

import javax.validation.Valid;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto){
        // uzycie @Builder do przemapowania z Dto
        return reviewService.addReview(Review.builder()
                .authorName(cleanContent(reviewDto.authorName()))
                .productId(reviewDto.productId())
                .content(cleanContent(reviewDto.content()))
                .build());
    }

    private String cleanContent(String text) {
        return Jsoup.clean(text, Safelist.none()); // usuwa znaczniki html
    }
}
