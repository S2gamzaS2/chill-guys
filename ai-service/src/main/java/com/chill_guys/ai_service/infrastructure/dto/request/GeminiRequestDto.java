package com.sparta.logistics.ai_service.infrastructure.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GeminiRequestDto {

    private List<Content> contents;

    public GeminiRequestDto(String text) {
        this.contents = List.of(new Content(List.of(new Part(text))));
    }

    @Getter
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @AllArgsConstructor
    public static class Part {
        private String text;
    }
}