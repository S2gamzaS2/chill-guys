package com.chill_guys.delivery_service.application.notification;

import com.chill_guys.delivery_service.domain.model.Delivery;

public interface NotificationService {

    void sendMessage(String type, Delivery delivery, String slackId, String recipientAddress);
}
