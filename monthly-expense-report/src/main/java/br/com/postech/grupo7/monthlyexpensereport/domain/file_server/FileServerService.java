package br.com.postech.grupo7.monthlyexpensereport.domain.file_server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    public String convertPdfToStringfyJson(final byte[] pdfBytes) throws IOException {
        try (InputStream pdfInputStream = new ByteArrayInputStream(pdfBytes)) {
            // Extrair texto do PDF
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfInputStream));
            StringBuilder text = new StringBuilder();

            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)));
            }

            pdfDoc.close();

            return createJSONObject("content", text.toString());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
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
        return convertPdfToStringfyJson(file.getBytes());
    }

    private String createJSONObject(final String key, final String value) throws IOException {
        // Criar um mapa com o texto extraído
        Map<String, String> pdfContent = new HashMap<>();
        pdfContent.put(key, value.toString());
        return objectMapper.writeValueAsString(pdfContent);
    }

}
