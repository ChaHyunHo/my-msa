package me.chahyunho.msapayment.service;

import lombok.extern.slf4j.Slf4j;
import me.chahyunho.msapayment.event.PaymentEventPublisher;
import me.chahyunho.msapayment.model.Payment;
import me.chahyunho.msapayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentEventPublisher paymentEventPublisher;


    @Autowired
    private PaymentRepository paymentRepository;

    public void createPayment(String orderId) {
        log.info("Create payment for orderId: {}", orderId);
        Payment payment = new Payment();
        payment.setOrderId(Long.parseLong(orderId));
        payment.setStatus("COMPLETED");

        paymentRepository.save(payment);
        paymentEventPublisher.publishPaymentCreated(Long.parseLong(orderId));
    }

    @Transactional
    public void cancelPayment(String orderId) {
        log.info("cancel payment for orderId: {}", orderId);
        Payment payment = getPaymentById(Long.parseLong(orderId));
        payment.setStatus("CANCELLED");

    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
