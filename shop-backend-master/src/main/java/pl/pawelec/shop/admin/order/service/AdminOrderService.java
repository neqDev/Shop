package pl.pawelec.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawelec.shop.admin.order.model.AdminOrder;
import pl.pawelec.shop.admin.order.model.AdminOrderLog;
import pl.pawelec.shop.admin.order.repositor.AdminOrderLogRepository;
import pl.pawelec.shop.admin.order.repositor.AdminOrderRepository;
import pl.pawelec.shop.common.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepository orderRepository;
    private final AdminOrderLogRepository adminOrderLogRepository;
    private final EmailNotificationForStatusChange emailNotificationForStatusChange;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return orderRepository.findAll(
                PageRequest.of( // strona posortowana odwrotnej kokejnosci
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending()) // sortowanie
        );
    }

    public AdminOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void patchOrder(Long id, Map<String, String> values) {
        AdminOrder adminOrder = orderRepository.findById(id).orElseThrow();
        patchValues(adminOrder, values);

    }

    private void patchValues(AdminOrder adminOrder, Map<String, String> values) {
        if(values.get("orderStatus") != null){
            processOrderStatusChange(adminOrder, values);
        }
    }

    private void processOrderStatusChange(AdminOrder adminOrder, Map<String, String> values) {
        OrderStatus oldStatus = adminOrder.getOrderStatus();
        OrderStatus newStatus = OrderStatus.valueOf(values.get("orderStatus"));
        if(oldStatus == newStatus){
            return;
        }
        adminOrder.setOrderStatus(newStatus);
        logStatusChange(adminOrder.getId(), oldStatus, newStatus);
        emailNotificationForStatusChange.sendEmailNotification(newStatus, adminOrder);
    }



    private void logStatusChange(Long orderId, OrderStatus oldStatus, OrderStatus newStatus){
        adminOrderLogRepository.save(AdminOrderLog.builder()
                        .created(LocalDateTime.now())
                        .orderId(orderId)
                        .note("Zmiana statusu zamówienia z " + oldStatus.getValue() + " na "
                                + newStatus.getValue())
                .build());
    }
}
