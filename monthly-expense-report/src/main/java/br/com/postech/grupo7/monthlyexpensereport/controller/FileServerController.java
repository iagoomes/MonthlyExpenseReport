package br.com.postech.grupo7.monthlyexpensereport.controller;

import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServer;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file-server")
@RequiredArgsConstructor
public class FileServerController {

    private final FileServerService fileServerService;

    @PostMapping("/upload/{customerId}")
    public ResponseEntity<String> savePDF(@RequestParam("file") MultipartFile file,
                                          @PathVariable Integer customerId) throws IOException {
        return fileServerService.savePDF(customerId, file);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<String> getFille(@PathVariable Integer id) {
        FileServer fileServer = fileServerService.getFileById(id);
        return ResponseEntity.ok(fileServer.getData());
    }
}