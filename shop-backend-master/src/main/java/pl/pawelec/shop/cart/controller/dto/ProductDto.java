package pl.pawelec.shop.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.pawelec.shop.product.service.dto.ReviewDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String currency;
    private String image;
    private String slug;
}
