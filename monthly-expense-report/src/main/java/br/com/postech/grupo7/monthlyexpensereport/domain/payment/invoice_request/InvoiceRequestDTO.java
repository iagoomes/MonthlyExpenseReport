package br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InvoiceRequestDTO {
    @NotNull
    private Integer customerId;
    private String analysisResults;
    @NotNull
    @Positive
    private Integer tokenCount;
}
