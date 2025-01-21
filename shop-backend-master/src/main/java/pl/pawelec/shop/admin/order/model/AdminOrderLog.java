package pl.pawelec.shop.admin.order.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private LocalDateTime created;
    private String note;
}
