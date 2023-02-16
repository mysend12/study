package io.my.tobyhelloboot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloServiceTest {

    @Test
    void simpleHelloService() {
        HelloService helloService = new SimpleHelloService();
        String result = helloService.sayHello("Test");

        assertEquals("Hello Test", result);
    }

}