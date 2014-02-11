package com.github.tachesimazzoca.java.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnumTest {
    public enum RGBColor {
        RED(0xff, 0x00, 0x00),
        GREEN(0x00, 0xff, 0x00),
        BLUE(0x00, 0x00, 0xff);

        private final int red;
        private final int green;
        private final int blue;

        RGBColor(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
        }

        public String toHexString() {
            return String.format(
                "%02x%02x%02x",
                this.red, this.green, this.blue);
        }
    }

    @Test
    public void testRGBColor() {
        for (RGBColor rgb : RGBColor.values()) {
            switch (rgb) {
                case RED:
                    assertEquals(rgb.ordinal(), 0);
                    assertEquals(rgb.toHexString(), "ff0000");
                    break;
                case GREEN:
                    assertEquals(rgb.ordinal(), 1);
                    assertEquals(rgb.toHexString(), "00ff00");
                    break;
                case BLUE:
                    assertEquals(rgb.ordinal(), 2);
                    assertEquals(rgb.toHexString(), "0000ff");
                    break;
                default:
                    break;
            }
        }
    }

    public enum Device {
        IPHONE(OS.IOS),
        IPAD(OS.IOS),
        GALAXY(OS.ANDROID),
        NEXUS(OS.ANDROID);

        public OS os;

        Device(OS os) {
            this.os = os;
        }

        public String getOSDescription() {
            return this.os.getDescription();
        }

        public enum OS {
            IOS {
                String getDescription() { return "iOS - Apple"; }
            },
            ANDROID {
                String getDescription() { return "Android - Google"; }
            };

            abstract String getDescription();
        }
    }

    @Test
    public void testDevice() {
        assertEquals(Device.IPHONE.os, Device.OS.IOS);
        assertEquals(Device.IPAD.os, Device.OS.IOS);
        assertEquals(Device.NEXUS.os, Device.OS.ANDROID);
        assertEquals(Device.GALAXY.os, Device.OS.ANDROID);

        for (Device d : Device.values()) {
            assertEquals(d.getOSDescription(), d.os.getDescription());
        }
    }
}
