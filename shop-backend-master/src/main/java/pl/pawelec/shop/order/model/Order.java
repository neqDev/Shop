package pl.pawelec.shop.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.pawelec.shop.common.model.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private LocalDateTime placeDate;
    @Enumerated(EnumType.STRING) // jesli chcemu uzywac enum-a
    private OrderStatus orderStatus;
    @OneToMany
    @JoinColumn(name = "orderdId")
    private List<OrderRow> orderRows;
    private BigDecimal grossValue;
    private String firstname;
    private String lastname;
    private String street;
    private String zipcode;
    private String city;
    private String email;
    private String phone;
    @OneToOne
    private Payment payment;
    private Long userId;
}
