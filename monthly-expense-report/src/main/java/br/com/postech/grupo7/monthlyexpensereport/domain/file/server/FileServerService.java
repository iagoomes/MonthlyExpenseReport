package br.com.postech.grupo7.monthlyexpensereport.domain.file.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request.InvoiceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServerService {

    static final Integer MAX_SIZE = 5 * 1024 * 1024; // Default size is 5MB

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileServerRepository fileServerRepository;

    public FileServer saveFile(FileServer file) {
        return fileServerRepository.save(file);
    }

    public FileServer getFileById(Integer id) {
        return fileServerRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public String convertPdfToStringfyJson(MultipartFile file) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(file.getInputStream()));
        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            content.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i))).append("\n");
        }

        pdfDocument.close();

        return processContent(content.toString());
    }

    public String validAndConvertPdfToStringfyJson(final MultipartFile file) throws IOException {
        // Verificar se o arquivo é um PDF
        if (file.isEmpty()) {
            throw new IOException("O arquivo está em branco!");
        }
        if (!file.getContentType().equals("application/pdf")) {
            throw new IOException("O arquivo deve ser um PDF!");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IOException("O arquivo ultrapassou o limite máximo de 30mb!");
        }

        // Converter PDF para JSON Stringfy
        return convertPdfToStringfyJson(file);
    }

    public String processContent(String content) {
        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile("(\\d{2} \\w{3}) (.+?)(?: - (\\d+/\\d+))? (\\d{1,3},\\d{2})");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            builder.append(matcher.group()).append(";\n");
        }

        // Removendo o último ponto-e-vírgula, se necessário
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 2); // Remove o "; "
        }

        // String final para inserção no banco de dados
        String finalResult = builder.toString();

        return finalResult;
    }

    private String createJSONObject(final String key, final String value) throws IOException {
        // Criar um mapa com o texto extraído
        Map<String, String> pdfContent = new HashMap<>();
        pdfContent.put(key, value.toString());
        return objectMapper.writeValueAsString(pdfContent);
    }

}
