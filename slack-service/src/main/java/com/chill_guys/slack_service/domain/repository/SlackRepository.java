package com.chill_guys.slack_service.domain.repository;

import com.chill_guys.slack_service.domain.entity.Slack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SlackRepository extends JpaRepository<Slack, UUID> {
}
