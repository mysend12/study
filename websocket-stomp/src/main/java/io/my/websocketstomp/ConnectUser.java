package io.my.websocketstomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectUser {
    private final Map<String, StompHeaderAccessor> users = new ConcurrentHashMap<>();
    private final Map<String, MessageChannel> userChannels = new ConcurrentHashMap<>();


    public void addUser(String userId, StompHeaderAccessor accessor, MessageChannel channel) {
        users.put(userId, accessor);
        userChannels.put(userId, channel);
        this.logUsers();
    }

    public MessageChannel getUser(String userId) {
        return userChannels.get(userId);
    }

    public StompHeaderAccessor getUserAccessor(String userId) {
        return users.get(userId);
    }

    public boolean removeUser(StompHeaderAccessor stompHeaderAccessor) {
        for (String userId : users.keySet()) {
            if (Objects.equals(users.get(userId).getSessionId(), stompHeaderAccessor.getSessionId())) {
                users.remove(userId);
                userChannels.remove(userId);
                return true;
            }
        }
        return false;
    }

    public void logUsers() {
        log.info(users.keySet().toString());
    }

}
