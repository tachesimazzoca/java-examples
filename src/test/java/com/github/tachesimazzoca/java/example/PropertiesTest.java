package com.github.tachesimazzoca.java.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

public class PropertiesTest {
    @Test
    public void test() {
        Properties prop = new Properties();
        prop.setProperty("A", "valueOfA");
        prop.setProperty("B", "valueOfB");

        for (Enumeration<?> names = prop.propertyNames(); names.hasMoreElements();) {
            String nm = (String) names.nextElement();
            assertEquals("valueOf" + nm, prop.getProperty(nm));
        }

        Set<String> nameSet = prop.stringPropertyNames();
        for (String nm : nameSet) {
            assertEquals("valueOf" + nm, prop.getProperty(nm));
        }

        assertEquals("defaultValue", prop.getProperty("c", "defaultValue"));

        try {
            final String path = "properties-test/a.properties";
            prop.load(this.getClass().getClassLoader().getResourceAsStream(path));

            assertEquals("valueOfA", prop.getProperty("A"));
            assertEquals("valueOfB", prop.getProperty("B"));
            assertEquals("java.util.Properties Example Program",
                    prop.getProperty("description"));
            assertEquals("java.util.Properties のプログラム例",
                    prop.getProperty("description.jp"));

            prop.setProperty("description", "updated");
            assertEquals("updated", prop.getProperty("description"));

            prop.load(this.getClass().getClassLoader().getResourceAsStream(path));
            assertEquals("java.util.Properties Example Program",
                    prop.getProperty("description"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
