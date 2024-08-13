package br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRequestRepository extends JpaRepository<InvoiceRequest, Integer> {
}
