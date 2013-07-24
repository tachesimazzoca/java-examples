package com.github.tachesimazzoca.java.example;

import java.util.regex.Pattern;

public final class RegexExample {
    private RegexExample() {
    }

    public static void main(String[] args) {
        Pattern ptn = Pattern.compile("[0-9a-zA-Z]+");
        System.out.println(ptn.matcher("DEADBEEEF").matches());
        System.out.println(ptn.matcher("DEADBEEEF!").matches());

        final String ptnStr = "[0-9a-zA-Z]+";
        System.out.println("DEADBEEF".matches(ptnStr));
        System.out.println("DEADBEEF!".matches(ptnStr));
    }
}
