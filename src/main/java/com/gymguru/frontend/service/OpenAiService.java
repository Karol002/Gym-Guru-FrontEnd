package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.OpenAiClient;
import com.gymguru.frontend.domain.save.SaveOpenAiMessage;
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
        SaveOpenAiMessage saveOpenAiMessage = new SaveOpenAiMessage(message);

        try {
            return openAiClient.getResponse(saveOpenAiMessage);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return "OpenAi api error";
        }
    }
}
