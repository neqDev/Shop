package pl.pawelec.shop.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawelec.shop.admin.order.model.AdminOrder;
import pl.pawelec.shop.common.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {
    List<AdminOrder> findAllByPlaceDateIsBetweenAndOrderStatus(LocalDateTime from, LocalDateTime to, OrderStatus orderStatus);
}
