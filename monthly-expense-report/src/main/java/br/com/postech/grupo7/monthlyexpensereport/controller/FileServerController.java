package br.com.postech.grupo7.monthlyexpensereport.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServer;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file-server")
@RequiredArgsConstructor
public class FileServerController {

    private final FileServerService fileServerService;

    @GetMapping("/file/{id}")
    public ResponseEntity<String> getFille(@PathVariable Integer id) {
        FileServer fileServer = fileServerService.getFileById(id);
        return ResponseEntity.ok(fileServer.getData());
    }

    @PostMapping("/upload/{customerId}")
    public ResponseEntity<String> savePDF(@RequestParam("file") MultipartFile file,
            @PathVariable Integer customerId) throws IOException {
        return fileServerService.savePDF(customerId, file);
    }

}