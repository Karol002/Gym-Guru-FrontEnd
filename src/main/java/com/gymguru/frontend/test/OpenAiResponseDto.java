package com.gymguru.frontend.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenAiResponseDto {
    @JsonProperty("text")
    private String text;

    public OpenAiResponseDto(String text) {
        this.text = text;
    }
}
