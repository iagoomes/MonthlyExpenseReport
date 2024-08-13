package br.com.postech.grupo7.monthlyexpensereport.infra.service;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {

        @Value("${openai.api.key}")
        private String apiKey;

        @Value("${openai.api.url}")
        private String apiUrl;

        private final RestTemplate restTemplate = new RestTemplate();

        public String getChatGPTResponse(String userPrompt) {
                final String role = "Dado que você é um gerente financeiro de uma grande e importante empresa";
                final String content = "Retorne os dados abaixos agrupados e categorizados com as descrições, valores e datas em formato json: \n "
                                + userPrompt;
                final JSONArray messages = new JSONArray()
                                .put(new JSONObject()
                                                .put("role", role)
                                                .put("content", content));

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
                final ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
                                String.class);

                // Extraindo a resposta do corpo
                return response.getBody();
        }

}