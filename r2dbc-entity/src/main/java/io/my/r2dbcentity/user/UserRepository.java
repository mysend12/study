package io.my.r2dbcentity.user;

import io.my.r2dbcentity.user.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
