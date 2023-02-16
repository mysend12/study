package io.my.tobyhelloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloApiTest {

    /**
     * http localhost:8080/hello?name=Spring
     */
    @Test
    void helloApi() {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        // status code 200
        assertEquals(200, res.getStatusCode().value());
        // header(content-type) text/plain
        assertEquals("text/plain;charset=ISO-8859-1", res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        // body Hello Spring
        assertEquals("*Hello Spring*", res.getBody());
    }

    @Test
    void failsHelloApi() {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:8080/hello?name=", String.class);

        // status code 200
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
    }

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String test = decorator.sayHello("Test");
        assertEquals("*Test*", test);
    }

}
