package com.github.tachesimazzoca.java.examples.jaxws.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloServiceTest {
    @Test
    public void testSayHello() {
        HelloService service = new HelloService();
        assertEquals("Hello Foo!", service.getHelloPort().sayHello("Foo"));
    }

    @Test
    public void testCalcArea() {
        HelloService service = new HelloService();
        Point p = new Point();
        p.setX(5);
        p.setY(7);
        assertEquals(35, service.getHelloPort().calcArea(p));
    }
}
