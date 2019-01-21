package com.github.tachesimazzoca.java.examples.opencsv;


import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

import static org.junit.Assert.*;

public class CSVReaderTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testParseTSV() {
        String line = "foo\tbar\tbaz\nfoo2\tbar2\tbaz2\nfoo3\tb\"ar3\tbaz3";
        Reader ir = new InputStreamReader(new ByteArrayInputStream(line.getBytes()));
        CSVReader csv = new CSVReader(ir, '\t');
        try {
            List<String[]> table =  csv.readAll();
            assertEquals(3, table.size());

            String[] line1 = table.get(0);
            assertEquals("foo", line1[0]);
            assertEquals("bar", line1[1]);
            assertEquals("baz", line1[2]);

            String[] line2 = table.get(1);
            assertEquals("foo2", line2[0]);
            assertEquals("bar2", line2[1]);
            assertEquals("baz2", line2[2]);

            String[] line3 = table.get(2);
            assertEquals("foo3", line3[0]);
            assertEquals("b\"ar3\tbaz3\n", line3[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
