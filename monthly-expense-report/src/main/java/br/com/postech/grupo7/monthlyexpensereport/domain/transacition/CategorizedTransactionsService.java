package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import br.com.postech.grupo7.monthlyexpensereport.infra.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorizedTransactionsService {
    private final CategorizedTransactionsRepository categorizedTransactionsRepository;
    private final TransactionRepository transactionRepository;
    private final OpenAIService openAIService;

    public CategorizedTransactionsDTO saveCategorizedTransactions(String contentPdf) throws Exception {
        CategorizedTransactionsDTO categorizedTransactionsDTO = openAIService.getChatGPTResponse(contentPdf);
        List<TransactionDTO> categorizedTransactionDTOS = categorizedTransactionsDTO.getCategorizedTransactionDTOS();
        List<Transactions> transactions = categorizedTransactionDTOS.stream().map(Transactions::new).toList();

        CategorizedTransactions categorizedTransactions = new CategorizedTransactions(transactions);
        transactions.forEach(t -> t.setCategorizedTransactions(categorizedTransactions));

        CategorizedTransactions saved = categorizedTransactionsRepository.save(categorizedTransactions);
        return null;
    }
}
