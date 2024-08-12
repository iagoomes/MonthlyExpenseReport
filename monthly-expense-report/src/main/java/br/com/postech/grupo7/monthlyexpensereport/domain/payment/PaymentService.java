package br.com.postech.grupo7.monthlyexpensereport.domain.payment;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerService;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoiceRequest.InvoiceRequest;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoiceRequest.InvoiceRequestDTO;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoiceRequest.InvoiceRequestRepository;
import br.com.postech.grupo7.monthlyexpensereport.infra.service.PaymentChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRequestRepository invoiceRequestRepository;
    private final CustomerService customerService;
    private final PaymentChannelService paymentChannelService;

    private static final BigDecimal TOKEN_PRICE = new BigDecimal("0.50");

    public BigDecimal calculatePayment(int tokenQuantity) {
        return TOKEN_PRICE.multiply(new BigDecimal(tokenQuantity));
    }

    public ResponseEntity<String> saveInvoiceRequest(InvoiceRequestDTO invoiceRequestDTO) {
        Customer customer = customerService.getCustomerById(invoiceRequestDTO.getCustomerId());
        InvoiceRequest invoiceRequest = new InvoiceRequest(invoiceRequestDTO, customer);
        invoiceRequest.setAmount(calculatePayment(invoiceRequest.getTokenCount()));
        invoiceRequestRepository.save(invoiceRequest);
        return ResponseEntity.ok().body("Requisição de pagamento salva com sucesso, id: " + invoiceRequest.getId());
    }

    public ResponseEntity<String> savePayment(PaymentDTO paymentDTO) {
        InvoiceRequest invoiceRequest = invoiceRequestRepository.findById(paymentDTO.getInvoiceRequestId()).orElseThrow();
        Payment payment = new Payment(paymentDTO, invoiceRequest);
        Payment paymentSaved = paymentRepository.save(payment);
        paymentChannelService.processPayment(paymentSaved.getId());
        return ResponseEntity.ok().body("Solicitação de Pagamento processado com sucesso, id: " + paymentSaved.getId());
    }

    public ResponseEntity<String> getStatus(Integer paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        return ResponseEntity.ok().body(payment.getPaymentStatus());
    }
}
