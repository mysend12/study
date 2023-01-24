package io.my.r2dbcentity.content.entity;

import org.springframework.data.annotation.Id;

public record Content(@Id Long id, String title, Long userId) {
}
