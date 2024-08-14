package br.com.postech.grupo7.monthlyexpensereport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServer;
import br.com.postech.grupo7.monthlyexpensereport.domain.file.server.FileServerService;
import br.com.postech.grupo7.monthlyexpensereport.domain.transacition.CategorizedTransactionsDTO;
import br.com.postech.grupo7.monthlyexpensereport.domain.transacition.CategorizedTransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/engine")
@RequiredArgsConstructor
public class EngineController {

    private final CategorizedTransactionsService categorizedTransactionsService;
    private final FileServerService fileServerService;

    @GetMapping("/{idArchive}")
    public ResponseEntity<CategorizedTransactionsDTO> getInsights(@PathVariable @Valid int idArchive,
            @RequestBody String contentPdf) throws Exception {
        final FileServer file = this.fileServerService.getFileById(idArchive);
        return ResponseEntity.ok(categorizedTransactionsService.saveCategorizedTransactions(file.getData()));
    }
}
