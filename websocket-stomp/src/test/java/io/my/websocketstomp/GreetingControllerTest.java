package io.my.websocketstomp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest {
    private String url = "ws://localhost:8080/stomp";

    @Test
    void greetingTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient webSocketStompClient1 = new WebSocketStompClient(new SockJsClient(this.createTransportClient()));
        webSocketStompClient1.setMessageConverter(new MappingJackson2MessageConverter());

        var stompSession = this.connect(webSocketStompClient1, "1");

        StompSession.Receiptable send = stompSession.send("/app/greeting/10", "Hello!!");

        Thread.sleep(2000);
        this.disconnect(stompSession, "1");
    }

    @Test
    void sendToUserTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient webSocketStompClient1 = new WebSocketStompClient(new SockJsClient(this.createTransportClient()));
        webSocketStompClient1.setMessageConverter(new MappingJackson2MessageConverter());

        WebSocketStompClient webSocketStompClient2 = new WebSocketStompClient(new SockJsClient(this.createTransportClient()));
        webSocketStompClient2.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session1 = this.connect(webSocketStompClient1, "1");
        StompSession session2 = this.connect(webSocketStompClient2, "2");

        log.info("connect success!!");

        session1.subscribe("/user/1/queue/send", new StompFrameHandlerImpl());
        session2.subscribe("/user/2/queue/send", new StompFrameHandlerImpl());
        session1.send("/app/send/to/user/1", "Hello sendToUser!!");

        Thread.sleep(2000);
        this.disconnect(session1, "1");
        this.disconnect(session2, "2");
    }

    @Test
    void sendToTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient webSocketStompClient1 = new WebSocketStompClient(new SockJsClient(this.createTransportClient()));
        webSocketStompClient1.setMessageConverter(new MappingJackson2MessageConverter());

        WebSocketStompClient webSocketStompClient2 = new WebSocketStompClient(new SockJsClient(this.createTransportClient()));
        webSocketStompClient2.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session1 = this.connect(webSocketStompClient1, "1");
        StompSession session2 = this.connect(webSocketStompClient2, "2");

        log.info("connect success!!");

        session1.subscribe("/topic/send", new StompFrameHandlerImpl());
        session2.subscribe("/topic/send", new StompFrameHandlerImpl());
        session1.send("/app/send/to", "Hello sendUser!!");


        Thread.sleep(2000);
        this.disconnect(session1, "1");
        this.disconnect(session2, "2");
    }

    private StompSession connect(WebSocketStompClient client, String userId) throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketHttpHeaders connectHeaders = new WebSocketHttpHeaders();

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("Authorization", "auth");
        stompHeaders.add("userId", userId);
        return client.connect(
                this.url, connectHeaders, stompHeaders,
                new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);

    }

    private void disconnect(StompSession session, String userId) {
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("userId", userId);
        session.disconnect(stompHeaders);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(10);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class StompFrameHandlerImpl implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            log.info("--------------");
            return Map.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            if (payload instanceof Map) {
                log.info("payload: {}", payload);
            }
        }
    }
}
