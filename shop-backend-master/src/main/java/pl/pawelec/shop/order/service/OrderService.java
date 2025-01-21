package pl.pawelec.shop.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawelec.shop.common.mail.EmailClientService;
import pl.pawelec.shop.common.model.Cart;
import pl.pawelec.shop.common.repository.CartItemRepository;
import pl.pawelec.shop.common.repository.CartRepository;
import pl.pawelec.shop.order.model.*;
import pl.pawelec.shop.order.model.dto.OrderDto;
import pl.pawelec.shop.order.model.dto.OrderListDto;
import pl.pawelec.shop.order.model.dto.OrderSummary;
import pl.pawelec.shop.order.repository.OrderRepository;
import pl.pawelec.shop.order.repository.OrderRowRepository;
import pl.pawelec.shop.order.repository.PaymentRepository;
import pl.pawelec.shop.order.repository.ShipmentRepository;


import java.util.List;

import static pl.pawelec.shop.order.service.mapper.OrderDtoMapper.mapToOrderListToDto;
import static pl.pawelec.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.pawelec.shop.order.service.mapper.OrderMapper.*;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
//    @Qualifier("emailSimpleService") // wstrzykuje konkretna implementacje, konkretny bean
    private final EmailClientService emailClientService;


    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
                        OrderRowRepository orderRowRepository, CartItemRepository cartItemRepository,
                        ShipmentRepository shipmentRepository, PaymentRepository paymentRepository, EmailClientService emailClientService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderRowRepository = orderRowRepository;
        this.cartItemRepository = cartItemRepository;
        this.shipmentRepository = shipmentRepository;
        this.paymentRepository = paymentRepository;
        this.emailClientService = emailClientService;
    }

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {
        // stworzenie zamowienia z wierszami
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        // mapujemy do naszej encji  dane, ktore potrzebujemy
        // zapisac zamowienie
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        // pobrac koszyk
        saveOrderRows(cart, newOrder.getId(), shipment);
        // usunac koszyk
        clearOlderCart(orderDto);
        // zwrocic podsumowanie
        log.info("Zamówienie złożone");
        sendConfirmEmail(newOrder);

        return createOrderSummary(payment, newOrder);
    }

    private void sendConfirmEmail(Order newOrder) {
        emailClientService.getInstance().send(newOrder.getEmail(),
                "Twoje zamówienie zostało przyjęte",
                createEmailMessage(newOrder));
    }

    private void clearOlderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }


    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowQuantity(orderId, cartItem)
                ).peek(orderRowRepository::save)
                .toList();
    }

    public List<OrderListDto> getOrdersForCustomer(Long userId) {
        return mapToOrderListToDto(orderRepository.findByUserId(userId));
    }


}
