package br.com.postech.grupo7.monthlyexpensereport.domain.payment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequest;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "invoice_request_id", nullable = false)
    private InvoiceRequest invoiceRequest;

    @Column(name = "payment_amount", nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "card_number", nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false, length = 100)
    private String cardHolderName;

    @Column(name = "card_expiry_date", nullable = false)
    private LocalDate cardExpiryDate;

    @Column(name = "card_cvv", nullable = false, length = 4)
    private String cardCvv;

    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus = "Processing";

    public Payment(PaymentDTO paymentDTO, InvoiceRequest invoiceRequest) {
        this.invoiceRequest = invoiceRequest;
        this.paymentAmount = invoiceRequest.getAmount();
        this.paymentMethod = paymentDTO.getPaymentMethod();
        this.cardNumber = paymentDTO.getCardNumber();
        this.cardHolderName = paymentDTO.getCardHolderName();
        this.cardExpiryDate = paymentDTO.getCardExpiryDate();
        this.cardCvv = paymentDTO.getCardCvv();
    }
}