package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.dto.OpenAiMessageDto;
import com.gymguru.frontend.external.app.cllient.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAiClient openAiClient;
    private final Logger logger = LoggerFactory.getLogger(OpenAiClient.class);

    public String getAiResponse(String message) {
        OpenAiMessageDto openAiMessageDto = new OpenAiMessageDto(message);

        try {
            return openAiClient.getResponse(openAiMessageDto);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return "OpenAi api error";
        }
    }
}
