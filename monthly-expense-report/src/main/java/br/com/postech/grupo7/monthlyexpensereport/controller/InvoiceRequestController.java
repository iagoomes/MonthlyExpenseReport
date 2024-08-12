package br.com.postech.grupo7.monthlyexpensereport.controller;

import br.com.postech.grupo7.monthlyexpensereport.domain.payment.InvoiceRequest;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.InvoiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("invoice-requests")
@RequiredArgsConstructor
public class InvoiceRequestController {
    private final InvoiceRequestRepository invoiceRequestRepository;

    @GetMapping
    public List<InvoiceRequest> findAll() {
        return invoiceRequestRepository.findAll();
    }
}
