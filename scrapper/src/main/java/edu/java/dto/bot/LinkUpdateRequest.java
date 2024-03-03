package edu.java.dto.bot;

import java.util.List;

public record LinkUpdateRequest(
        Long id,
        String url,
        String description,
        List<Long> tgChatIds) {
}
