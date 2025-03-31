package com.sparta.logistics.ai_service.application.service;

import com.sparta.logistics.ai_service.infrastructure.client.GeminiClient;
import com.sparta.logistics.ai_service.infrastructure.dto.request.GeminiRequestDto;
import com.sparta.logistics.ai_service.infrastructure.dto.response.GeminiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final GeminiClient geminiClient;

    public AiService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Value("${gemini.api.key}")
    private String GEMINI_API_KEY;

    public String getCompletionText(String inputForAI) {
        String request = inputForAI + " 이 경우의 출발지, 배송지, 허브 위치 등을 계산하여 오늘 날짜를 기준으로 배송 출발일을 YYYY-MM-DD 형식으로 정확하게 한 문장으로 답해주세요. 예시: 2025-03-28에 도착할 예정입니다. 추가 설명 없이 정확한 날짜만 반환해주세요.";
        GeminiRequestDto requestDto = new GeminiRequestDto(request);

        GeminiResponseDto responseDto = geminiClient.getCompletion(requestDto, GEMINI_API_KEY);

        if (responseDto.getCandidates() != null && !responseDto.getCandidates().isEmpty()) {
            String text = responseDto.getCandidates().get(0).getContent().getParts().get(0).getText();
            return text;
        }
        return "답변을 받을 수 없습니다.";
    }
}