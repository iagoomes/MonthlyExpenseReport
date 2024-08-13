package br.com.postech.grupo7.monthlyexpensereport.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PDFController {

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
}