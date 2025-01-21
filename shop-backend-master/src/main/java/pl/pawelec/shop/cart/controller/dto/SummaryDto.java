package pl.pawelec.shop.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class SummaryDto {
    private BigDecimal grossValue;
}
