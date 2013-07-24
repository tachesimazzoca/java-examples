package com.github.tachesimazzoca.java.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexExample {
    private RegexExample() {
    }

    public static void main(String[] args) {
        final String ptnStr = "[0-9a-zA-Z]+";
        System.out.println("DEADBEEF".matches(ptnStr));
        System.out.println("DEADBEEF!".matches(ptnStr));

        Pattern ptn = Pattern.compile("[0-9a-zA-Z]+");
        System.out.println(ptn.matcher("DEADEEEF").matches());
        System.out.println(ptn.matcher("DEADEEEF!").matches());

        Pattern urlPattern = Pattern.compile("(https?)://([^/]+)(.*)");
        Matcher urlMatcher = urlPattern.matcher("http://www.example.net/foo");
        if (urlMatcher.matches()) {
            int c = urlMatcher.groupCount();
            for (int i = 0; i <= c; i++) {
                System.out.println(urlMatcher.group(i));
            }
        }
    }
}
