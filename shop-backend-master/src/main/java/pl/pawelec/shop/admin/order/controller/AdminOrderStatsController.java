package pl.pawelec.shop.admin.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawelec.shop.admin.order.model.dto.AdminOrderStats;
import pl.pawelec.shop.admin.order.service.AdminOrderStatsService;

@RestController
@RequestMapping("/admin/orders/stats")
public class AdminOrderStatsController {

    private final AdminOrderStatsService adminOrderStatsService;

    public AdminOrderStatsController(AdminOrderStatsService adminOrderStatsService) {
        this.adminOrderStatsService = adminOrderStatsService;
    }
    @GetMapping
    public AdminOrderStats getOrderStatistics(){
        return adminOrderStatsService.getStatistics();
    }
}
