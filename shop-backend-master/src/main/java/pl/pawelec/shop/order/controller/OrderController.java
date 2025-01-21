package pl.pawelec.shop.order.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.pawelec.shop.order.model.Order;
import pl.pawelec.shop.order.model.dto.InitOrder;
import pl.pawelec.shop.order.model.dto.OrderDto;
import pl.pawelec.shop.order.model.dto.OrderListDto;
import pl.pawelec.shop.order.model.dto.OrderSummary;
import pl.pawelec.shop.order.service.OrderService;
import pl.pawelec.shop.order.service.PaymentService;
import pl.pawelec.shop.order.service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, ShipmentService shipmentService, PaymentService paymentService) {
        this.orderService = orderService;
        this.shipmentService = shipmentService;
        this.paymentService = paymentService;
    }

    @PostMapping// @AuthenticationPrincipal - wstrzykniecie usera
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal Long userId){ // z frontu pobieramy orderDto uzupelniony z formularza
        return orderService.placeOrder(orderDto, userId);
    }

    @GetMapping("/initData")
    public InitOrder initData(){
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .payments(paymentService.getPayments())
                .build();
    }

    @GetMapping("")
    public List<OrderListDto> getOrders(@AuthenticationPrincipal Long userId){
        if(userId == null){
            throw new IllegalArgumentException("Brak użytkownika");
        }
        return orderService.getOrdersForCustomer(userId);

    }
}
