package br.com.postech.grupo7.monthlyexpensereport.infra.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.postech.grupo7.monthlyexpensereport.domain.transacition.CategorizedTransactionsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiOpenAIUrl;

    @Value("${apicotacoes.api.url}")
    private String apiCotacoesUrl;

    ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Pattern TOKEN_PATTERN = Pattern.compile("\\s*(\\S+)\\s*");

    public CategorizedTransactionsDTO getChatGPTResponse(String userPrompt) throws JsonProcessingException {
        var user = "Analise a fatura do cartão de crédito e categorize as transações:\n" +
                userPrompt;
        var system = "Você é um analista de fatura de cartão de credito ou debito e categorizar e separar detalhadadamente as trações." +
                "#############################" +
                "Sua resposta deve seguir o seguinte padrão:\n" +
                "{\n" +
                "  \"categorized_transactions\": [\n" +
                "    {\n" +
                "      \"date\": \"02 JUL\",\n" +
                "      \"description\": \"Dentista\",\n" +
                "      \"value\": \"105,00\",\n" +
                "      \"installment_current\": 3,\n" +
                "      \"installment_total\": 4,\n" +
                "      \"category\": \"Saúde\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"date\": \"02 JUL\",\n" +
                "      \"description\": \"Karisma Noivas\",\n" +
                "      \"value\": \"90,00\",\n" +
                "      \"installment_current\": 2,\n" +
                "      \"installment_total\": 3,\n" +
                "      \"category\": \"Casamento\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"date\": \"02 JUL\",\n" +
                "      \"description\": \"AUDI | Freios traseiros\",\n" +
                "      \"value\": \"89,92\",\n" +
                "      \"installment_current\": 8,\n" +
                "      \"installment_total\": 10,\n" +
                "      \"category\": \"Transporte\"\n" +
                "    },\n" +
                "    // Mais transações categorizadas aqui...\n" +
                "  ]\n" +
                "}";

        OpenAiService service = new OpenAiService(apiKey);
        var completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)))
                .build();

        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest)
                .getChoices();

        CategorizedTransactionsDTO categorizedTransactionsDTO = null;
        for (ChatCompletionChoice choice : choices) {
            categorizedTransactionsDTO = objectMapper.readValue(choice.getMessage().getContent(), CategorizedTransactionsDTO.class);
        }

        return categorizedTransactionsDTO;
    }

    public JsonNode getUSDtoBRL() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.getForEntity(apiCotacoesUrl, String.class);

        try {
            return objectMapper.readTree(response.getBody()).get("USDBRL");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(text);
        while (matcher.find()) {
            String token = matcher.group(1);
            tokens.add(token);
        }
        return tokens;
    }

    public int countTokens(String text) {
        List<String> tokens = tokenize(text);
        return tokens.size();
    }

    public BigDecimal tokenPriceBRL() {
        final Double cotacao = getUSDtoBRL().get("high").asDouble();
        final Double tokenPriceUSD = 0.0005;
        final Double tokenPriceBRL = tokenPriceUSD * cotacao;
        return BigDecimal.valueOf(tokenPriceBRL);
    }

}