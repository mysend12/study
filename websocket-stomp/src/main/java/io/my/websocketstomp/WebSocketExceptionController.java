package io.my.websocketstomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketExceptionController {

    @MessageExceptionHandler
    public String handleException(Exception exception) {
        log.info("exception: {}", exception.getMessage());
        return "exception!!";
    }
}
