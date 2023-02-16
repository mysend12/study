package io.my.tobyhelloboot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HelloControllerTest {

    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);

        String spring = helloController.hello("Spring");
        assertEquals("Spring", spring);
    }

    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(name -> name);
        assertThrows(IllegalArgumentException.class, () -> helloController.hello(null));
        assertThrows(IllegalArgumentException.class, () -> helloController.hello(" "));
    }
}
