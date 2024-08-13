package br.com.postech.grupo7.monthlyexpensereport.controller;

import br.com.postech.grupo7.monthlyexpensereport.domain.payment.*;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequestDTO;
import br.com.postech.grupo7.monthlyexpensereport.infra.service.OpenAIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OpenAIService openAIService;

    @PostMapping("/invoiceRequests")
    public ResponseEntity<String> saveInvoiceRequest(@RequestBody @Valid InvoiceRequestDTO invoiceRequestDTO) {
        return paymentService.saveInvoiceRequest(openAIService.tokenPriceBRL(), invoiceRequestDTO);
    }

    @PostMapping
    public ResponseEntity<String> savePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        return paymentService.savePayment(paymentDTO);
    }

    @GetMapping("{paymentId}")
    public ResponseEntity<String> getStatus(@PathVariable Integer paymentId) {
        return paymentService.getStatus(paymentId);
    }
}
