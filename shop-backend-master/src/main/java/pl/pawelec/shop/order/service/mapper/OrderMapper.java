package pl.pawelec.shop.order.service.mapper;

import pl.pawelec.shop.cart.model.CartItem;
import pl.pawelec.shop.common.model.Cart;
import pl.pawelec.shop.common.model.OrderStatus;
import pl.pawelec.shop.order.model.*;
import pl.pawelec.shop.order.model.dto.OrderDto;
import pl.pawelec.shop.order.model.dto.OrderSummary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {
    public static Order createNewOrder(OrderDto orderDto, Cart cart, Shipment shipment, Payment payment, Long userId) {
        return Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .payment(payment)
                .userId(userId)
                .build();
    }

    private static BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(shipment.getPrice());
    }

    public static OrderSummary createOrderSummary(Payment payment, Order newOrder) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(payment)
                .build();
    }

    public static OrderRow mapToOrderRow(Long orderId, Shipment shipment) {
        return OrderRow.builder()
                .quantity(1)
                .price(shipment.getPrice())
                .orderId(orderId)
                .build();
    }

    public static OrderRow mapToOrderRowQuantity(Long orderId, CartItem cartItem) {
        return OrderRow.builder()
                .quantity(cartItem.getQuantity())
                .productId(cartItem.getProduct().getId())
                .price(cartItem.getProduct().getPrice())
                .orderId(orderId)
                .build();
    }
}
