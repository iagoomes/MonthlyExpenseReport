package br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoiceRequest;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "invoice_request")
public class InvoiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Pending";

    @Column(name = "submitted_at", nullable = false)
    private LocalDate submittedAt;

    @Column(name = "analysis_results")
    private String analysisResults;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "token_count", nullable = false)
    private Integer tokenCount = 0;

    public InvoiceRequest(InvoiceRequestDTO invoiceRequestDTO, Customer customer) {
        this.customer = customer;
        this.analysisResults = invoiceRequestDTO.getAnalysisResults() == null ? null : invoiceRequestDTO.getAnalysisResults();
        this.submittedAt = LocalDate.now();
        this.tokenCount = invoiceRequestDTO.getTokenCount();
    }

}