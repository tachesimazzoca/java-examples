package com.github.tachesimazzoca.java.examples.jackson;

import static org.junit.Assert.*;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

public class ConfigTest {
    @Test
    public void testDeserialization() throws
            IOException,
            JsonParseException,
            JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(
                this.getClass().getResourceAsStream("/config.json"),
                Config.class);
        assertEquals(Config.Mode.DEVELOPMENT, config.getMode());
        assertEquals("Java Examples | Jackson", config.getName());
        assertEquals(2, config.getResources().size());
        assertEquals("app.resources", config.getResources().get(0));
        assertEquals("app.more.resources", config.getResources().get(1));
        assertEquals(9090, config.getServer().getPort());
        assertEquals(2, config.getServer().getHost().length);
        assertEquals(InetAddress.getByName("127.0.0.1"), config.getServer()
                .getHost()[0]);
        assertEquals(InetAddress.getByName("192.168.56.101"), config
                .getServer().getHost()[1]);
    }
}
