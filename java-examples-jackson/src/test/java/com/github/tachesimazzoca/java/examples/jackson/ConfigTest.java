package com.github.tachesimazzoca.java.examples.jackson;

import static org.junit.Assert.*;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        assertEquals(9090, config.getServer().getPort());
    }
}
