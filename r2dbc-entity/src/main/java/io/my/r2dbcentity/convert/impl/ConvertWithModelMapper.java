package io.my.r2dbcentity.convert.impl;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.convert.ConvertUtil;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;
import org.modelmapper.ModelMapper;

public class ConvertWithModelMapper implements ConvertUtil {
    private final ModelMapper modelMapper;

    public ConvertWithModelMapper() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public UserPayload userToUserPayload(User user) {
        return modelMapper.map(user, UserPayload.class);
    }

    @Override
    public User userPayloadToUser(UserPayload userPayload) {
        return modelMapper.map(userPayload, User.class);
    }

    @Override
    public ContentPayload contentToContentPayload(Content content) {
        return modelMapper.map(content, ContentPayload.class);
    }

    @Override
    public Content contentPayloadToContent(ContentPayload contentPayload) {
        return modelMapper.map(contentPayload, Content.class);
    }

}
