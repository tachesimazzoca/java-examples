package com.github.tachesimazzoca.java.examples.config;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TypesafeConfigTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testHtmlSection() {
        final String title = "Java Examples | Config";
        final String charset = "utf-8";

        Configuration config = TypesafeConfig.load();
        assertEquals(title, config.get("html.title"));
        assertEquals(charset, config.get("html.charset"));

        Map<String, Object> m = (Map<String, Object>) config.get("html");
        assertEquals(title, m.get("title"));
        assertEquals(charset, m.get("charset"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUrlSection() {
        final String basedir = "/java-examples-config";
        final String domain = "example.net";

        Configuration config = TypesafeConfig.load();
        assertEquals(basedir, config.get("url.basedir"));

        Map<String, Object> m = (Map<String, Object>) config.get("url");
        assertEquals(basedir, m.get("basedir"));
        assertEquals(domain, m.get("domain"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMailerConf() {
        Configuration config = TypesafeConfig.load("mailer");

        List<Map<String, Object>> to =
                (List<Map<String, Object>>) config.get("inquiry.to");
        assertEquals("foo@example.net", to.get(0).get("email"));
        assertEquals("User Foo", to.get(0).get("name"));
    }

    @Test
    public void testParseFile() {
        Configuration config = TypesafeConfig.parseFile(
                new File(this.getClass().getResource("/test.conf").getPath()));

        assertEquals("/test", config.get("url.basedir"));
        assertEquals("test.example.net", config.get("url.domain"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParseString() {
        StringBuilder sb = new StringBuilder();
        sb.append("users: [ ");
        sb.append("{ id: 1, name: \"foo\" },");
        sb.append("{ id: 2, name: \"bar\" }");
        sb.append("]");
        Configuration config = TypesafeConfig.parseString(sb.toString());

        List<Map<String, Object>> users =
                (List<Map<String, Object>>) config.get("users");
        assertEquals(1, users.get(0).get("id"));
        assertEquals("foo", users.get(0).get("name"));
    }
}
