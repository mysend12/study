package io.my.r2dbcentity.convert.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;
import io.my.r2dbcentity.convert.ConvertUtil;

public class ConvertWithObjectMapper implements ConvertUtil {
    private final ObjectMapper objectMapper;

    public ConvertWithObjectMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public UserPayload userToUserPayload(User user) {
        Object o = this.toObject(user, UserPayload.class);

        if (!(o instanceof UserPayload userPayload)) {
            throw new RuntimeException("Server Error!!");
        }

        return userPayload;
    }

    @Override
    public User userPayloadToUser(UserPayload userPayload) {
        Object o = this.toObject(userPayload, User.class);

        if (!(o instanceof User user)) {
            throw new RuntimeException("Server Error!!");
        }

        return user;
    }

    @Override
    public ContentPayload contentToContentPayload(Content content) {
        Object o = this.toObject(content, ContentPayload.class);

        if (!(o instanceof ContentPayload contentPayload)) {
            throw new RuntimeException("Server Error!!");
        }

        return contentPayload;
    }

    @Override
    public Content contentPayloadToContent(ContentPayload contentPayload) {
        Object o = this.toObject(contentPayload, Content.class);

        if (!(o instanceof Content content)) {
            throw new RuntimeException("Server Error!!");
        }

        return content;
    }

    private Object toObject(Object o, Class<?> clz) {
        try {
            String s = this.objectMapper.writeValueAsString(o);
            return objectMapper.readValue(s, clz);
        } catch (JsonProcessingException e) { /* do nothing */ }
        throw new RuntimeException("fail convert!!");
    }
}
