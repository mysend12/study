package io.my.r2dbcentity.convert;

import io.my.r2dbcentity.content.entity.Content;
import io.my.r2dbcentity.content.payload.ContentPayload;
import io.my.r2dbcentity.user.entity.User;
import io.my.r2dbcentity.user.payload.UserPayload;

public interface ConvertUtil {
    UserPayload userToUserPayload(User user);
    User userPayloadToUser(UserPayload userPayload);

    ContentPayload contentToContentPayload(Content content);
    Content contentPayloadToContent(ContentPayload contentPayload);
}
