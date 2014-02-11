package com.github.tachesimazzoca.java.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringTest {
    @Test
    public void testEquals() {
        String a = "foo";
        assertTrue(a == "foo");
        assertTrue(a.equals("foo"));

        String b = a;
        assertTrue(b == "foo");
        assertTrue(a == b);
        assertTrue(b.equals(b));

        a = "bar";
        assertTrue(a != b);
        assertTrue(!a.equals(b));
        assertTrue(b == "foo");

        a = new String("foo");
        assertTrue(a != "foo");
        assertTrue(a.equals("foo"));
        assertTrue(a != b);
        assertTrue(a.equals(b));
    }
}
