package io.my.r2dbcentity.convert.impl;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.convert.ConvertUtil;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;

/**
 *
 */
public class ConvertWithBoilerplate implements ConvertUtil {

    @Override
    public UserPayload userToUserPayload(User user) {
        return UserPayload.builder()
                .id(user.id())
                .title(user.title())
                .build();
    }

    @Override
    public User userPayloadToUser(UserPayload userPayload) {
        return null;
    }

    @Override
    public ContentPayload contentToContentPayload(Content content) {
        return null;
    }

    @Override
    public Content contentPayloadToContent(ContentPayload contentPayload) {
        return null;
    }
}
