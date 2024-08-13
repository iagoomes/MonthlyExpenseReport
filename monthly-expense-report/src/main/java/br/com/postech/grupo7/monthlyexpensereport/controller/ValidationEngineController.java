package br.com.postech.grupo7.monthlyexpensereport.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import br.com.postech.grupo7.monthlyexpensereport.domain.validation_engine.ValidationEngineService;
import br.com.postech.grupo7.monthlyexpensereport.infra.service.OpenAIService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class ValidationEngineController {

    private final OpenAIService openAIService;
    private final ValidationEngineService validationEngineService;

    @PostMapping("/read")
    public String readPdf(@RequestParam("file") MultipartFile file) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(file.getInputStream()));
        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            content.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i))).append("\n");
        }

        pdfDocument.close();
        return content.toString();
    }

    // Retorno de dados em formato JSON
    @PostMapping("/read-and-convert-to-stringfy-json")
    public ResponseEntity<String> readPdfAndConvertToStrinfyJSON(@RequestParam("file") final MultipartFile file) {
        try {
            final String stringfyJSON = validationEngineService.validAndConvertPdfToStringfyJson(file);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(stringfyJSON);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Retorno chamando a API do chatGPT
    @PostMapping("/read-and-send-to-chatgpt")
    public ResponseEntity<String> readPdfAndSendToChatGPT(@RequestParam("file") MultipartFile file) {
        try {
            final String stringfyJSON = validationEngineService.validAndConvertPdfToStringfyJson(file);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(openAIService.getChatGPTResponse(stringfyJSON));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}