package com.github.tachesimazzoca.java.example;

public final class EnumExample {
    private EnumExample() {
    }

    public static void main(String[] args) {
        System.out.printf("%s - #%s%n",
            RGBColor.RED, RGBColor.RED.toHexString());
        System.out.printf("%s - #%s%n",
            RGBColor.GREEN, RGBColor.GREEN.toHexString());
        System.out.printf("%s - #%s%n",
            RGBColor.BLUE, RGBColor.BLUE.toHexString());

        for (RGBColor rgb : RGBColor.values()) {
            switch (rgb) {
                case RED:
                    System.out.println("I'm RED.");
                    break;
                case GREEN:
                    System.out.println("I'm GREEN.");
                    break;
                case BLUE:
                    System.out.println("I'm BLUE.");
                    break;
                default:
                    System.out.println("Unknown color.");
                    break;
            }
        }

        for (Device dev : Device.values()) {
            System.out.printf("%d.%s (%s)%n",
                dev.ordinal() + 1, dev, dev.getOSDescription());
        }
    }

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

    public enum Device {
        IPHONE(OS.IOS),
        IPAD(OS.IOS),
        GALAXY(OS.ANDROID),
        NEXUS(OS.ANDROID);

        private OS os;

        Device(OS os) {
            this.os = os;
        }

        public String getOSDescription() {
            return this.os.getDescription();
        }

        private enum OS {
            IOS {
                String getDescription() { return "iOS - Apple"; }
            },
            ANDROID {
                String getDescription() { return "Android - Google"; }
            };

            abstract String getDescription();
        }
    }
}
