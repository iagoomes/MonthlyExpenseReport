package br.com.postech.grupo7.monthlyexpensereport.domain.file.server;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServerService {

    static final Integer MAX_SIZE = 5 * 1024 * 1024; // Default size is 5MB
    private final FileServerRepository fileServerRepository;
    private final CustomerService customerService;

    public FileServer getFileById(Integer id) {
        return fileServerRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public ResponseEntity<String> savePDF(Integer customerId, MultipartFile file) throws IOException {
        validPdf(file);
        final Customer customer = customerService.getCustomerById(customerId);
        final String content = getContent(file);
        final FileServer attachment = new FileServer(file.getOriginalFilename(), content, customer);
        fileServerRepository.save(attachment);
        return ResponseEntity.ok("Arquivo salvo com sucesso!");
    }

    public boolean validPdf(final MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("O arquivo está em branco!");
        }
        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            throw new IOException("O arquivo deve ser um PDF!");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IOException("O arquivo ultrapassou o limite máximo de 30mb!");
        }
        return true;
    }

    public String getContent(MultipartFile file) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(file.getInputStream()));
        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            content.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i))).append("\n");
        }

        pdfDocument.close();
        return processContent(content.toString());
    }

    public String processContent(String content) {
        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile("(\\d{2} \\w{3}) (.+?)(?: - (\\d+/\\d+))? (\\d{1,3},\\d{2})");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            builder.append(matcher.group()).append(";\n");
        }

        if (!builder.isEmpty()) {
            builder.setLength(builder.length() - 2); // Remove o "; "
        }

        return builder.toString();
    }
}
