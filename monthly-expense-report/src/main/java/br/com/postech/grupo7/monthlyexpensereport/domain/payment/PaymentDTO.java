package br.com.postech.grupo7.monthlyexpensereport.domain.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {
    @NotNull
    private Integer invoiceRequestId;
    @NotBlank
    private String paymentMethod;
    @Pattern(regexp = "\\d{16}", message = "Card number must be exactly 16 digits")
    private String cardNumber;
    @NotBlank
    private String cardHolderName;
    @NotNull
    private LocalDate cardExpiryDate;
    @Pattern(regexp = "\\d{3}", message = "CVV must be exactly 3 digits")
    private String cardCvv;
}
