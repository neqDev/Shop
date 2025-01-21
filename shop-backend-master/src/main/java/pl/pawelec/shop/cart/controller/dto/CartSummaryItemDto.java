package pl.pawelec.shop.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CartSummaryItemDto {
    private Long id;
    private int quantity;
    private ProductDto productDto; // musi sie maczowac na frontend
    private BigDecimal lineValue;
}
