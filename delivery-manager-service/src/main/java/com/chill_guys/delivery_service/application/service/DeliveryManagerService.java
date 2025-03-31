package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.request.DeliveryManagerUpdateRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerResponseDto;
import com.chill_guys.delivery_service.application.mapper.DeliveryManagerInfoMapper;
import com.chill_guys.delivery_service.application.mapper.DeliveryManagerMapper;
import com.chill_guys.delivery_service.domain.model.DeliveryManager;
import com.chill_guys.delivery_service.domain.model.DeliveryManagerType;
import com.chill_guys.delivery_service.domain.repository.DeliveryManagerRepository;
import com.chill_guys.delivery_service.exception.CustomException;
import com.chill_guys.delivery_service.exception.DeliveryManagerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryManagerService {

    private final DeliveryManagerRepository deliveryManagerRepository;

    @Transactional
    public void createDeliveryManager(Long id, String slackId) {
        Long sequence = deliveryManagerRepository.getMaxSequence() + 1;

        DeliveryManager deliveryManager = DeliveryManagerMapper.toEntity(id, slackId, sequence);
        deliveryManagerRepository.save(deliveryManager);
    }

    @Transactional(readOnly = true)
    public List<DeliveryManagerResponseDto> getDeliveryManagerList() {
        List<DeliveryManager> deliveryManagerList = deliveryManagerRepository.findByAndDeletedAtIsNull();
        List<DeliveryManagerResponseDto> deliveryManagerResponseDtoList = new ArrayList<>();

        for(DeliveryManager deliveryManager : deliveryManagerList) {
            deliveryManagerResponseDtoList.add(DeliveryManagerMapper.toDto(deliveryManager));
        }

        return deliveryManagerResponseDtoList;
    }

    @Transactional(readOnly = true)
    public DeliveryManagerResponseDto getDeliveryManager(Long deliveryManagerId) {
        DeliveryManager deliveryManager = findDeliveryManagerById(deliveryManagerId);

        return DeliveryManagerMapper.toDto(deliveryManager);
    }

    @Transactional
    public void updateDeliveryManager(Long deliveryManagerId, DeliveryManagerUpdateRequestDto deliveryManagerUpdateRequestDto) {
        DeliveryManager deliveryManager = findDeliveryManagerById(deliveryManagerId);

        deliveryManager.updateOf(deliveryManagerUpdateRequestDto);
    }

    @Transactional
    public void deleteDeliveryManager(Long deliveryManagerId, String userId) {
        DeliveryManager deliveryManager = findDeliveryManagerById(deliveryManagerId);

        Long id = Long.parseLong(userId);
        deliveryManager.deletedOf(id);
    }

    @Transactional
    public DeliveryManagerInfoDto assignDeliveryManager(UUID startHubId, UUID endHubId, DeliveryManagerType type) {
        //출발허브아이디와 타입으로 배송담당자 조회
        List<DeliveryManager> deliveryManagerList = deliveryManagerRepository.findByHubIdAndTypeAndDeletedAtIsNullOrdered(startHubId, type);

        //순번이 가장 먼저인 배송 담당자 배정
        if(deliveryManagerList.isEmpty()) {
            throw new CustomException(DeliveryManagerErrorCode.DELIVERY_MANAGER_NOT_AVAILABLE);
        }

        DeliveryManager deliveryManager = deliveryManagerList.get(0);
        Long id = deliveryManager.getId();
        Long maxSequence = deliveryManagerRepository.findMaxSequence();
        deliveryManager.changeHubId(endHubId, maxSequence);

        return DeliveryManagerInfoMapper.toDto(id, deliveryManager.getSlackId());
    }

    @Transactional
    public void assignHubAndType(Long deliveryManagerId, UUID hubId, DeliveryManagerType type) {
        DeliveryManager deliveryManager = findDeliveryManagerById(deliveryManagerId);

        deliveryManager.setHubAndType(hubId, type);
    }

    @Transactional
    public void updateSlackId(Long deliveryManagerId, String slackId) {
        DeliveryManager deliveryManager = findDeliveryManagerById(deliveryManagerId);
        deliveryManager.updateSlackId(slackId);
    }


    private DeliveryManager findDeliveryManagerById(Long id) {
        return deliveryManagerRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(DeliveryManagerErrorCode.DELIVERY_MANAGER_NOT_FOUND));
    }
}
