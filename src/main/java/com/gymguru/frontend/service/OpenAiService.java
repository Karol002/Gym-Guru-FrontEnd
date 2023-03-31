package com.gymguru.frontend.service;

import com.gymguru.frontend.external.app.cllient.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final OpenAiClient openAiClient;
}
