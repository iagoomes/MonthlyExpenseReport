package br.com.postech.grupo7.monthlyexpensereport.domain.payment;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerService;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServer;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerService;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequest;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequestDTO;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequestRepository;
import br.com.postech.grupo7.monthlyexpensereport.infra.service.OpenAIService;
import br.com.postech.grupo7.monthlyexpensereport.infra.service.PaymentChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private final PaymentRepository paymentRepository;
    private final InvoiceRequestRepository invoiceRequestRepository;
    private final CustomerService customerService;
    private final PaymentChannelService paymentChannelService;
    private final FileServerService fileServerService;
    private final OpenAIService openAIService;

    public BigDecimal calculatePayment(final BigDecimal tokenBRLPrice, int tokenQuantity) {
        return tokenBRLPrice.multiply(new BigDecimal(tokenQuantity));
    }

    public ResponseEntity<String> saveInvoiceRequest(final BigDecimal tokenBRLPrice,
            InvoiceRequestDTO invoiceRequestDTO) {
        final Customer customer = customerService.getCustomerById(invoiceRequestDTO.getCustomerId());
        final FileServer file = fileServerService.getFileById(invoiceRequestDTO.getArchiveId());

        if (file.getData().length > 0) {
            final int tokens = openAIService.countTokens(decodeUTF8(file.getData()));

            if (tokens > 0) {
                final InvoiceRequest invoiceRequest = new InvoiceRequest(invoiceRequestDTO, customer, tokens);
                invoiceRequest.setAmount(calculatePayment(tokenBRLPrice, tokens));
                invoiceRequestRepository.save(invoiceRequest);
                return ResponseEntity.ok()
                        .body("Requisição de pagamento salva com sucesso, id: " + invoiceRequest.getId());
            }
            return ResponseEntity.badRequest().body("Não foi possivel identificar a quantidade de tokens necessárias!");
        }
        return ResponseEntity.badRequest().body("Não foi possivel localizar o arquivo!");
    }

    public ResponseEntity<String> savePayment(PaymentDTO paymentDTO) {
        InvoiceRequest invoiceRequest = invoiceRequestRepository.findById(paymentDTO.getInvoiceRequestId())
                .orElseThrow();
        Payment payment = new Payment(paymentDTO, invoiceRequest);
        Payment paymentSaved = paymentRepository.save(payment);
        paymentChannelService.processPayment(paymentSaved.getId());
        return ResponseEntity.ok().body("Solicitação de Pagamento processado com sucesso, id: " + paymentSaved.getId());
    }

    public ResponseEntity<String> getStatus(Integer paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        return ResponseEntity.ok().body(payment.getPaymentStatus());
    }

    private String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }
}
