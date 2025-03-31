package com.sparta.logistics.ai_service.presentation.controller;

import com.sparta.logistics.ai_service.application.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public String generateCompletion(@RequestBody String inputForAI) {
        return aiService.getCompletionText(inputForAI);
    }
}