package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CheckoutRequest;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.entity.Payment;
import com.myproject.ecommerce.enums.PaymentStatus;
import com.myproject.ecommerce.repository.PaymentRepository;
import com.myproject.ecommerce.utils.TransactionCodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    // create payment
    public Payment createPayment(Order order, CheckoutRequest checkoutRequest) {

        Payment payment = Payment.builder()
                .trasactionCode(TransactionCodeUtils.generate())
                .totalPayment(order.getTotalAmount())
                .paymentMethod(checkoutRequest.getPaymentMethod())
                .paymentStatus(PaymentStatus.UNPAID)
                .build();

        payment.setOrder(order); // owning side
        order.setPayment(payment); // bidirectional mapping for inverse side

        return paymentRepository.save(payment);
    }
}
