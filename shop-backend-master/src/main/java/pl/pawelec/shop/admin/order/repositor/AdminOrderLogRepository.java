package pl.pawelec.shop.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawelec.shop.admin.order.model.AdminOrderLog;

public interface AdminOrderLogRepository extends JpaRepository<AdminOrderLog, Long> {
}
