package io.my.r2dbcentity.user.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;

public record User(@Id Long id, String title) {
    @Builder(toBuilder = true) public User{}
}
