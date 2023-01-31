package io.my.websocketstomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectInterceptor implements ChannelInterceptor {
    private final ConnectUser connectUser;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);

        if (stompHeaderAccessor.getCommand() == StompCommand.CONNECT) {
            this.connect(stompHeaderAccessor, channel);
        } else if (stompHeaderAccessor.getCommand() == StompCommand.DISCONNECT) {
            connectUser.removeUser(stompHeaderAccessor);
            connectUser.logUsers();
        }

        return ChannelInterceptor.super.preSend(message, channel);
    }

    private void connect(StompHeaderAccessor stompHeaderAccessor, MessageChannel channel) {
        this.getHeader(stompHeaderAccessor, "Authorization");
        this.addUser(stompHeaderAccessor, channel);
    }

    private String getHeader(StompHeaderAccessor stompHeaderAccessor, String key) {
        String header = stompHeaderAccessor.getFirstNativeHeader(key);
        if (header == null) throw new RuntimeException(key + " is null!");
        return header;
    }

    private void addUser(StompHeaderAccessor stompHeaderAccessor, MessageChannel channel) {
        String userId = this.getHeader(stompHeaderAccessor, "userId");
        Principal principal = () -> userId;
        stompHeaderAccessor.setUser(principal);
        connectUser.addUser(userId, stompHeaderAccessor, channel);
    }
}
