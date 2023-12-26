package com.github.arlekinside.sqlver.logic.ai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AIServiceImpl implements AIService {

    private static final String Q_FORMAT = "Make a validation of the SQL query: [%s]. Consider that variable and table names are correct. Answer format is: [isValid] - [howToImproveTheQuery]";
    private final RestTemplate restTemplate;
    private final String endpoint;
    private final String model;

    public AIServiceImpl(@Qualifier("chatGPTRestTemplate") RestTemplate restTemplate,
                         @Value("${app.ai.chatgpt.api.endpoint}") String endpoint,
                         @Value("${app.ai.chatgpt.model}") String model) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
        this.model = model;
    }

    @Override
    public String requestAdvice(String query) {
        var message = new AIMessage();
        message.setRole("user");
        message.setContent(Q_FORMAT.formatted(query));

        var req = new AIRequestDTO();
        req.setModel(model);
        req.setMessages(List.of(message));
        req.setN(1);
        req.setTemperature(0.2);
        req.setMaxTokens(100);
        req.setFrequencyPenalty(2.0);


        return restTemplate.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(req), AIResponseDTO.class)
                .getBody()
                .getChoices()
                .stream()
                .findFirst()
                .orElseGet(AIResponseDTO.Choice::new)
                .getMessage()
                .getContent();
    }
}
