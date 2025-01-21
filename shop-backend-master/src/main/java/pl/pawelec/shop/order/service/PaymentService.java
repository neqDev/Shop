package pl.pawelec.shop.order.service;

import org.springframework.stereotype.Service;
import pl.pawelec.shop.order.model.Payment;
import pl.pawelec.shop.order.repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
}
