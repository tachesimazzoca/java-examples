package com.github.tachesimazzoca.java.example;

public final class StringExample {
    private StringExample() {
    }

    public static void main(String[] args) {
        System.out.println("String a = \"foo\"; String b = a;");
        String a = "foo";
        String b = a;
        inspect(a, b);

        System.out.println("a = \"bar\";");
        a = "bar";
        inspect(a, b);

        System.out.println("a = \"foo\";");
        a = "foo";
        inspect(a, b);

        System.out.println("a = new StringBuilder(\"foo\").toString();");
        a = new StringBuilder("foo").toString();
        inspect(a, b);
    }

    public static void inspect(String a, String b) {
        System.out.println("    a : " + a);
        System.out.println("    b : " + b);
        System.out.println("    a == b : " + (a == b));
        System.out.println("    a.equals(b) : " + a.equals(b));
        System.out.println();
    }
}
