package io.my.r2dbcentity.user.payload;

import io.my.r2dbcentity.content.entity.Content;
import lombok.Builder;

import java.util.List;

public record UserPayload(Long id, String title, List<Content> contents) {
    @Builder(toBuilder = true) public UserPayload {}
}
