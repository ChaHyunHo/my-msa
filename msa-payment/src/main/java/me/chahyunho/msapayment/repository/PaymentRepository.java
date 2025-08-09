package me.chahyunho.msapayment.repository;

import me.chahyunho.msapayment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
