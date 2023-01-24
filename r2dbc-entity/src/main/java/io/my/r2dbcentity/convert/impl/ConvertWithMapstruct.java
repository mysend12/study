package io.my.r2dbcentity.convert.impl;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;
import io.my.r2dbcentity.convert.ConvertUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConvertWithMapstruct extends ConvertUtil {
    ConvertWithMapstruct INSTANCE = Mappers.getMapper(ConvertWithMapstruct.class);

    UserPayload userToUserPayload(User user);
    User userPayloadToUser(UserPayload userPayload);

    ContentPayload contentToContentPayload(Content content);
    Content contentPayloadToContent(ContentPayload contentPayload);

}
