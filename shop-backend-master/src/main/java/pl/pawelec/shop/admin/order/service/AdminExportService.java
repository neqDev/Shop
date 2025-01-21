package pl.pawelec.shop.admin.order.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.admin.order.model.AdminOrder;
import pl.pawelec.shop.admin.order.repositor.AdminOrderRepository;
import pl.pawelec.shop.common.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminExportService {

    private  final AdminOrderRepository orderRepository;

    public AdminExportService(AdminOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<AdminOrder> exportOrders(LocalDateTime from, LocalDateTime to, OrderStatus orderStatus) {
        return orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(from, to, orderStatus);
    }
}
