package br.com.postech.grupo7.monthlyexpensereport.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerService;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServer;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerRepository;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file-server")
@RequiredArgsConstructor
public class FileServerController {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private final CustomerService customerService;
    private final FileServerService validationEngineService;
    private final FileServerRepository validationEngineRepository;

    // Upload de arquivo
    @PostMapping("/upload/{customerId}")
    public ResponseEntity<String> readPdfAndSendToChatGPT(@RequestParam("file") MultipartFile file,
            @PathVariable Integer customerId) {
        try {
            final Customer customer = customerService.getCustomerById(customerId);
            final String stringfyJSON = validationEngineService.validAndConvertPdfToStringfyJson(file);

            FileServer attachment = new FileServer(file.getOriginalFilename(), stringfyJSON.getBytes(
                    UTF8_CHARSET),
                    customer);

            validationEngineRepository.save(attachment);

            return ResponseEntity.ok("Arquivo salvo com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}