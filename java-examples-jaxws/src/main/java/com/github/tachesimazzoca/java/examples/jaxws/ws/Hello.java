package com.github.tachesimazzoca.java.examples.jaxws.ws;

import javax.jws.WebService;

@WebService
public class Hello {
    public String sayHello(String name) {
        return String.format("Hello %s!", name);
    }

    public int calcArea(Point p) {
        return p.getX() * p.getY();
    }
}
