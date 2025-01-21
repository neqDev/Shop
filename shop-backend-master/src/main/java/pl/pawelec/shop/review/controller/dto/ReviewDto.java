package pl.pawelec.shop.review.controller.dto;

import org.hibernate.validator.constraints.Length;

public record ReviewDto(@Length(min = 2, max = 60) String authorName, @Length(min = 2, max = 60)String content, Long productId) {
}
