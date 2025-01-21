package pl.pawelec.shop.admin.order.service;

import pl.pawelec.shop.common.model.OrderStatus;

public class AdminOrderEmailMessage {
    public static String creatingProcessingEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " jest przetwarzane." +
                "\nStatus został zmieniony na: " + newStatus.getValue() +
                "\nTwoje zamówienie jest przetwarzane przez naszych pracowników" +
                "\nPo skompletowaniu niezwłocznie przekażemy je do wysyłki" +
                "\n\nPozdrawiamy" +
                "\nSklep Shop";
    }

    public static String creatingCompletedEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zrealizowane." +
                "\nStatus twojego zamówienia został zmieniony na:  " + newStatus.getValue() +
                "\n\nDziękujemy za zakupy i zapraszamy ponownie" +
                "\nSklep Shop";
    }

    public static String creatingRefundEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zwrócone." +
                "\nStatus twojego zamówienia został zmieniony na:  " + newStatus.getValue() +
                "\n\nPozdrawiamy" +
                "\nSklep Shop";    }
}
