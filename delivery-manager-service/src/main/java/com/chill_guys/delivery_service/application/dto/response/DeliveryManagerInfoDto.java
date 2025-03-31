package com.chill_guys.delivery_service.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryManagerInfoDto {
    Long id;
    String slackId;
}
