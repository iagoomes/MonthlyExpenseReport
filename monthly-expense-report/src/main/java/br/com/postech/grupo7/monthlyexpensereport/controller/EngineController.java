package br.com.postech.grupo7.monthlyexpensereport.controller;

import br.com.postech.grupo7.monthlyexpensereport.domain.transacition.CategorizedTransactionsDTO;
import br.com.postech.grupo7.monthlyexpensereport.domain.transacition.CategorizedTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/engine")
@RequiredArgsConstructor
public class EngineController {
    private final CategorizedTransactionsService categorizedTransactionsService;

    @GetMapping
    public ResponseEntity<CategorizedTransactionsDTO> getInsights(@RequestBody String contentPdf) throws Exception {
        return ResponseEntity.ok(categorizedTransactionsService.saveCategorizedTransactions(contentPdf));
    }
}
