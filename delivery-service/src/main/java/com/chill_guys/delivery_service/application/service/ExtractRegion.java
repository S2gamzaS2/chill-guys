package com.chill_guys.delivery_service.application.service;

import java.util.UUID;

public interface ExtractRegion {
    UUID extractHub(String address);
}
