package io.my.websocketstomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GreetingController {
    private final ConnectUser connectUser;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/greeting/{id}")
    public void handle(String greeting, @DestinationVariable("id") int id) {
        log.info("/greeting: {} and id: {}", greeting, id);
    }

    @MessageMapping("/send/to/user/{id}")
    public void sendToUser(
            String sendMessage,
            @DestinationVariable("id") String id) {
        log.info("send to user: /queue");
        log.info("id: {}, payload: {}", id, sendMessage);

        simpMessagingTemplate.convertAndSendToUser(id, "/queue/send", Map.of("message", sendMessage + ", OK!"));
    }

    @MessageMapping("/send/to")
    @SendTo("/topic/send")
    public Map<String, String> sendTo(String sendMessage) {
        log.info("send to: /topic");
        log.info("payload: {}", sendMessage);
        return Map.of("message", sendMessage + ", OK!");
    }



}
