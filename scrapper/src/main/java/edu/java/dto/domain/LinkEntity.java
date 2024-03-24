package edu.java.dto.domain;

import java.time.OffsetDateTime;

public record LinkEntity(Long id, String url, OffsetDateTime updatedAt, OffsetDateTime lastCheck) {
}
