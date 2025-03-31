package com.chill_guys.slack_service.application.service;

import com.chill_guys.slack_service.infrastructure.client.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chill_guys.slack_service.application.dto.request.SlackMessageSendRequestDto;
import com.chill_guys.slack_service.application.dto.response.SlackMessageSaveResponseDto;
import com.chill_guys.slack_service.domain.entity.Slack;
import com.chill_guys.slack_service.infrastructure.client.*;
import com.chill_guys.slack_service.infrastructure.dto.DeliveryInfoDto;
import com.chill_guys.slack_service.infrastructure.dto.HubResponseDto;
import com.chill_guys.slack_service.domain.repository.SlackRepository;
import com.chill_guys.slack_service.infrastructure.dto.OrderSlackDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.bot.token}")
    private String SLACK_BOT_TOKEN;

    private final SlackRepository slackRepository;
    private final AiClient aiClient;
    private final SlackClient slackClient;
    private final HubClient hubClient;
    private final OrderClient orderClient;
    private final ProductClient productClient;

    public SlackService(SlackRepository slackRepository, AiClient aiClient, SlackClient slackClient, HubClient hubClient, OrderClient orderClient, ProductClient productClient) {
        this.slackRepository = slackRepository;
        this.aiClient = aiClient;
        this.slackClient = slackClient;
        this.hubClient = hubClient;
        this.orderClient = orderClient;
        this.productClient = productClient;
    }

    public SlackMessageSaveResponseDto saveSlackMessage(DeliveryInfoDto deliveryResponseDto) {

        // 1. hubId로 hub 주소 찾기
        HubResponseDto hubStartResponseDto = hubClient.getAddressByHubId(deliveryResponseDto.getDepartureHubId());
        HubResponseDto hubEndResponseDto = hubClient.getAddressByHubId(deliveryResponseDto.getDestinationHubId());

        // 2. delivery 정보 + hub 주소를 AI 요청용 string으로 변환
        String inputForAI = convertDeliveryInfoToString(deliveryResponseDto, hubStartResponseDto, hubEndResponseDto);

        // 3. string 으로 AI api에 전달하여 최종 발송 시한 응답값 받기
        String output = aiClient.generateCompletion(inputForAI);

        // 주문 상품 정보 추가
        OrderSlackDto orderSlackDto = orderClient.getOrderInfo(deliveryResponseDto.getOrderId());

        // 주문 정보(수량, 주문 번호)
        UUID orderId = deliveryResponseDto.getOrderId();
        Long quantity = orderSlackDto.getQuantity();
        String reqeust = orderSlackDto.getRequest();

        // 상품 정보(상품 아이디, 이름)
        UUID productId = deliveryResponseDto.getProductId();
        String productName = productClient.getProductName(productId);

        // 4. Slack message string 생성 (AI 응답 포함해서)
        String slackText = buildSlackText(deliveryResponseDto, hubStartResponseDto, hubEndResponseDto, output, orderId, quantity, productId, productName);

        // 5. Slack entity 저장
        Slack slack = Slack.builder()
                .id(UUID.randomUUID())
                .slackId(deliveryResponseDto.getSlackId())
                .text(slackText)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        slackRepository.save(slack);

        sendSlackMessage(slack.getId(), slack.getSlackId());

        // 6. 저장된 메시지 DTO로 반환
        return SlackMessageSaveResponseDto.builder()
                .id(slack.getId())
                .slackId(slack.getSlackId())
                .text(slack.getText())
                .build();
    }

    private String convertDeliveryInfoToString(DeliveryInfoDto deliveryDto, HubResponseDto hubStartResponseDto, HubResponseDto hubEndResponseDto) {
        StringBuilder sb = new StringBuilder();

        sb.append("배송지: ").append(deliveryDto.getAddress()).append("\n");
        sb.append("출발 허브 이름: ").append(hubStartResponseDto.getHubName()).append("\n");
        sb.append("출발 허브 주소: ").append(hubStartResponseDto.getAddress()).append("\n");
        sb.append("도착 허브 이름: ").append(hubEndResponseDto.getHubName()).append("\n");
        sb.append("도착 허브 주소: ").append(hubEndResponseDto.getAddress()).append("\n");

        return sb.toString();
    }

    private String buildSlackText(DeliveryInfoDto deliveryResponseDto, HubResponseDto hubStartDto, HubResponseDto hubEndDto, String output, UUID orderId, Long quantity, UUID productId, String productName) {
        StringBuilder sb = new StringBuilder();

        sb.append("배송지: ").append(deliveryResponseDto.getAddress()).append("\n");
        sb.append("출발 허브 이름: ").append(hubStartDto.getHubName()).append("\n");
        sb.append("출발 허브 주소: ").append(hubStartDto.getAddress()).append("\n");
        sb.append("도착 허브 이름: ").append(hubEndDto.getHubName()).append("\n");
        sb.append("도착 허브 주소: ").append(hubEndDto.getAddress()).append("\n");
        sb.append("주문 ID: ").append(orderId).append("\n");
        sb.append("상품 ID: ").append(productId).append("\n");
        sb.append("상품명: ").append(productName).append("\n");
        sb.append("수량: ").append(quantity).append("\n");
        sb.append("AI 예상 배송 날짜: ").append(output).append("\n");

        return sb.toString();
    }

    public void sendSlackMessage(UUID id, String slackId) {
        String responseText = "";

        Slack slack = slackRepository.findById(id).orElse(null);

        if (slack == null) {
            log.info("슬랙 데이터 없음");
        }

        String messageText = slack.getText();

        SlackMessageSendRequestDto dto = SlackMessageSendRequestDto.builder()
                .id(slack.getId())
                .channel(slackId)
                .text(messageText)
                .build();

        try {
            String slackMessage = slackClient.sendSlackMessage("Bearer " + SLACK_BOT_TOKEN, dto);

            log.info("dto-channel: " + dto.getChannel());
            log.info("dto-text: " + dto.getText());
            log.info("slack에 dto 보냈음" + slackMessage);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(slackMessage);

            if (responseJson.get("ok").asBoolean()) {
                responseText = "Message sent successfully!";
            } else {
                responseText = "Slack API 오류 발생: " + slackMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseText = "Slack API 호출 중 예외 발생";
        }

    }
}