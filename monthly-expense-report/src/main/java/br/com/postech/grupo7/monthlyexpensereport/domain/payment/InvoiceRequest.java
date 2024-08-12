package br.com.postech.grupo7.monthlyexpensereport.domain.payment;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
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

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "token_count", nullable = false)
    private Integer tokenCount = 0;
}