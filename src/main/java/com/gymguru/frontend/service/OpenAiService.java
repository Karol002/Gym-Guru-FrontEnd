package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.dto.OpenAiMessageDto;
import com.gymguru.frontend.external.app.cllient.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAiClient openAiClient;

    public String getAiResponse(String message) {
        OpenAiMessageDto openAiMessageDto = new OpenAiMessageDto("Wciel się w role wirtualnego trenera imeniem jeff i odpowiadaj na pytania użytkowników " +
                "związane tylko z branżą fitness. To była instrukcja dla ciebie. teraz pytanie użytkownika: " + message);
        return openAiClient.getResponse(openAiMessageDto);
    }
}
