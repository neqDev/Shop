package pl.pawelec.shop.admin.order.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class AdminOrderStats {
    private List<Integer> labels;
    private List<BigDecimal> sales;
    private List<Long> orders;
    private Long ordersCount;
    private BigDecimal salesSum;
}
