package com.github.tachesimazzoca.java.examples.jackson;

import static org.junit.Assert.*;
import org.junit.Test;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class DDLTest {
    private final String encoding = "UTF-8";

    @Test
    public void testConvertJsonToSQL() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DDL.convertJsonToSQL(
                this.getClass().getResourceAsStream("/schema.json"), os);
        String actual = os.toString(encoding);

        String expected = IOUtils.toString(
                this.getClass().getResourceAsStream("/schema.sql"), encoding);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertYAMLToSQL() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DDL.convertYAMLToSQL(
                this.getClass().getResourceAsStream("/schema.yml"), os);
        String actual = os.toString(encoding);

        String expected = IOUtils.toString(
                this.getClass().getResourceAsStream("/schema.sql"), encoding);
        assertEquals(expected, actual);
    }
}
