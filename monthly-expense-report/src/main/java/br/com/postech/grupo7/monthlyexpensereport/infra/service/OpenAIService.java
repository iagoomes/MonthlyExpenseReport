package br.com.postech.grupo7.monthlyexpensereport.infra.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

        private final RestTemplate restTemplate = new RestTemplate();

        private static final Pattern TOKEN_PATTERN = Pattern.compile("\\s*(\\S+)\\s*");

        private final ObjectMapper objectMapper = new ObjectMapper();

        public String getChatGPTResponse(String userPrompt) {
                final String role = "Dado que você é um gerente financeiro de uma grande e importante empresa";
                final String content = "Retorne os dados abaixos agrupados e categorizados com as descrições, valores e datas em formato json: \n "
                                + userPrompt;
                final JSONArray messages = new JSONArray()
                                .put(new JSONObject()
                                                .put("role", role)
                                                .put("content", content));
                final int tokens = countTokens(content);

                // Criando o corpo da requisição JSON
                final JSONObject request = new JSONObject();
                request.put("model", "gpt-3.5-turbo");
                request.put("messages", messages);
                request.put("temperature", 0.8);
                request.put("max_tokens", 100);
                request.put("top_p", 1);

                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + apiKey);

                final HttpEntity<String> requestEntity = new HttpEntity<>(request.toString(), headers);
                final ResponseEntity<String> response = restTemplate.exchange(
                                apiOpenAIUrl, HttpMethod.POST, requestEntity,
                                String.class);

                // Extraindo a resposta do corpo
                return response.getBody();
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