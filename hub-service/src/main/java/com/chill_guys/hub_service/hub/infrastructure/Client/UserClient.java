package com.chill_guys.hub_service.hub.infrastructure.Client;

import com.chill_guys.hub_service.hub.application.dto.response.UserRoleSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service")
public interface UserClient {
  @GetMapping("/api/master/users/role")
  Page<UserRoleSearchResponseDto> roleSearchUser(@RequestParam("userRole") String userRole,
      Pageable pageable);
}
