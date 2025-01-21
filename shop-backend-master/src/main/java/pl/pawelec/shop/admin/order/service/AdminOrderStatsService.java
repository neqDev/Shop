package pl.pawelec.shop.admin.order.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.admin.order.model.AdminOrder;
import pl.pawelec.shop.admin.order.model.dto.AdminOrderStats;
import pl.pawelec.shop.admin.order.repositor.AdminOrderRepository;
import pl.pawelec.shop.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
@Service
public class AdminOrderStatsService {
    private final AdminOrderRepository orderRepository;

    public AdminOrderStatsService(AdminOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public AdminOrderStats getStatistics() {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = LocalDateTime.now();
        List<AdminOrder> orders = orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from,
                to,
                OrderStatus.COMPLETED
        );

        TreeMap<Integer, AdminOrderStatsValue> result = IntStream.rangeClosed(from.getDayOfMonth(), to.getDayOfMonth())
                .boxed()
                .map(i -> aggregateValues(i, orders))
                .collect(toMap(
                        value -> value.day(),
                        value -> value,
                        (t, t2) -> {throw new IllegalArgumentException();},
                        TreeMap::new
                ));
        List<Long> ordersList = result.values().stream().map(v -> v.orders()).toList();
        List<BigDecimal> salesList = result.values().stream().map(v -> v.sales()).toList();
        return AdminOrderStats.builder()
                .labels(result.keySet().stream().toList())
                .sales(salesList)
                .orders(ordersList)
                .ordersCount(ordersList.stream().reduce(Long::sum).orElse(0L))
                .salesSum(salesList.stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .build();
    }

    private AdminOrderStatsValue aggregateValues(int i, List<AdminOrder> orders) {
        return orders.stream()
                .filter(adminOrder -> adminOrder.getPlaceDate().getDayOfMonth() == i)
                .map(adminOrder -> adminOrder.getGrossValue())
                .reduce(
                        new AdminOrderStatsValue(i, BigDecimal.ZERO, 0L),
                        (AdminOrderStatsValue o, BigDecimal v) ->  new AdminOrderStatsValue(i, o.sales().add(v), o.orders() + 1),
                        (o, o2) -> null
                );
    }
    private record AdminOrderStatsValue(Integer day, BigDecimal sales, Long orders){}}
