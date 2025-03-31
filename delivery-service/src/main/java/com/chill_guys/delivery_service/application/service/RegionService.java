package com.chill_guys.delivery_service.application.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegionService implements ExtractRegion {

    @Override
    public UUID extractHub(String address) {
        // TODO: 도착 허브 구하는 로직 구현



        return UUID.randomUUID();
    }
}
