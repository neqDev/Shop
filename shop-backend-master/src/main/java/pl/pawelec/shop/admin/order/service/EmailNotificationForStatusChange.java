package pl.pawelec.shop.admin.order.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.admin.order.model.AdminOrder;
import pl.pawelec.shop.common.mail.EmailClientService;
import pl.pawelec.shop.common.model.OrderStatus;

import static pl.pawelec.shop.admin.order.service.AdminOrderEmailMessage.*;

@Service
class EmailNotificationForStatusChange {
    private final EmailClientService emailClientService;

    EmailNotificationForStatusChange(EmailClientService emailClientService) {
        this.emailClientService = emailClientService;
    }

    void sendEmailNotification(OrderStatus newStatus, AdminOrder adminOrder) {
        if(newStatus == OrderStatus.PROCESSING){
            sendEmail(adminOrder.getEmail(),
                    "Zamówienie "  + adminOrder.getId() +
                            " zmieniło status na:  " + newStatus.getValue(),
                    creatingProcessingEmailMessage(adminOrder.getId(), newStatus));
        }else if(newStatus == OrderStatus.COMPLETED){
            sendEmail(adminOrder.getEmail(),
                    "Zamówienie "  + adminOrder.getId() + " zostało zrealizowane",
                    creatingCompletedEmailMessage(adminOrder.getId(), newStatus));
        }else if(newStatus == OrderStatus.REFUND){
            sendEmail(adminOrder.getEmail(),
                    "Zamówienie "  + adminOrder.getId() +
                            " zostało zwrócone",
                    creatingRefundEmailMessage(adminOrder.getId(), newStatus));
        }
    }

    private void sendEmail(String email, String subject, String message) {
        emailClientService.getInstance().send(email, subject, message);
    }
}
