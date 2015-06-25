package com.github.tachesimazzoca.java.examples.jaxws.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloServiceTest {
    @Test
    public void testSayHello() {
        HelloService service = new HelloService();
        assertEquals("Hello Foo!", service.getHelloPort().sayHello("Foo"));
    }
}
