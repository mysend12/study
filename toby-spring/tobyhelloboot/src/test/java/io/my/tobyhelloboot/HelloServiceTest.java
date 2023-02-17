package io.my.tobyhelloboot;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

@Test
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface UnitTest { }

@UnitTest
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@interface FastUnitTest { }

class HelloServiceTest {

    @FastUnitTest
    void simpleHelloService() {
        HelloService helloService = new SimpleHelloService();
        String result = helloService.sayHello("Test");
        assertEquals("Hello Test", result);
    }

    @Test
    void helloDecorator() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String hello = decorator.sayHello("Test");
        assertEquals("*Test*", hello);
    }

}