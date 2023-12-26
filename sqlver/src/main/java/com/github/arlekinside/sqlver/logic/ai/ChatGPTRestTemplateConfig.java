package com.github.arlekinside.sqlver.logic.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@Slf4j
public class ChatGPTRestTemplateConfig {

    private final String url;
    private final String apiKey;

    public ChatGPTRestTemplateConfig(@Value("${app.ai.chatgpt.api.url}") String url,
                                     @Value("${app.ai.chatgpt.api.key}") String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    @Bean
    public RestTemplate chatGPTRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri(url)
                .defaultHeader("Authorization", "Bearer %s".formatted(apiKey))
                .defaultHeader("Content-Type", "application/json;charset=UTF-8")
                .errorHandler(new ResponseErrorHandler() {
                    @Override
                    public boolean hasError(ClientHttpResponse response) throws IOException {
                        return !response.getStatusCode().is2xxSuccessful();
                    }

                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        log.error("ChatGPT response error {}, {}", response.getStatusCode(), response.getStatusText());
                        throw new IOException("Wrong response received from ChatGPT");
                    }

                }).build();
    }
}
