package com.github.tachesimazzoca.java.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void testMatcher() {
        final String ptnStr = "[0-9a-zA-Z]+";
        assertTrue("DEADBEEF".matches(ptnStr));
        assertFalse("DEADBEEF!".matches(ptnStr));

        Pattern ptn = Pattern.compile("[0-9a-zA-Z]+");
        assertTrue(ptn.matcher("DEADEEEF").matches());
        assertFalse(ptn.matcher("DEADEEEF!").matches());

        Pattern urlPattern = Pattern.compile("(https?)://([^/]+)(.*)");
        Matcher urlMatcher = urlPattern.matcher("http://www.example.net/foo");
        assertTrue(urlMatcher.matches());
        assertEquals(urlMatcher.groupCount(), 3);
        assertTrue(urlMatcher.group(0).equals("http://www.example.net/foo"));
        assertTrue(urlMatcher.group(1).equals("http"));
        assertTrue(urlMatcher.group(2).equals("www.example.net"));
        assertTrue(urlMatcher.group(3).equals("/foo"));
    }
}
