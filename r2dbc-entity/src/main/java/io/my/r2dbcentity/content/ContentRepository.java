package io.my.r2dbcentity.content;

import io.my.r2dbcentity.content.entity.Content;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ContentRepository extends R2dbcRepository<Content, Long> {
}
