package br.com.postech.grupo7.monthlyexpensereport.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private final FileServerService fileServerService;
    private final FileServerRepository fileServerRepository;

    // Upload de arquivo
    @PostMapping("/upload/{customerId}")
    public ResponseEntity<String> readPdfAndSendToChatGPT(@RequestParam("file") MultipartFile file,
            @PathVariable Integer customerId) {
        try {
            final Customer customer = customerService.getCustomerById(customerId);
            final String stringfyJSON = fileServerService.validAndConvertPdfToStringfyJson(file);

            FileServer attachment = new FileServer(file.getOriginalFilename(), stringfyJSON,
                    customer);

            fileServerRepository.save(attachment);

            return ResponseEntity.ok("Arquivo salvo com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<String> getFille(@PathVariable Integer id) {
        FileServer fileServer = fileServerRepository.findById(id).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(fileServer.getData());
    }
}