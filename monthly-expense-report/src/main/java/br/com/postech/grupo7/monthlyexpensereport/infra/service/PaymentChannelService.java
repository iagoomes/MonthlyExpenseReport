package br.com.postech.grupo7.monthlyexpensereport.infra.service;

import br.com.postech.grupo7.monthlyexpensereport.domain.payment.Payment;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentChannelService {
    private final PaymentRepository paymentRepository;

    public void processPayment(Integer paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        payment.setPaymentStatus("processed");
        paymentRepository.save(payment);
    }
}
