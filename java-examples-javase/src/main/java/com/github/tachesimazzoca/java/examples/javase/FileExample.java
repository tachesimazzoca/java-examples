package com.github.tachesimazzoca.java.examples.javase;

import java.io.File;
import java.io.IOException;

public final class FileExample {
    private FileExample() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("separator: " + File.separator);
        System.out.println("separatorChar: " + File.separatorChar);
        System.out.println("pathSeparator: " + File.pathSeparator);
        System.out.println("pathSeparatorChar: " + File.pathSeparatorChar);

        File file = new File(System.getProperty("java.io.tmpdir") + "/././.");
        System.out.println("absolutePath: " + file.getAbsolutePath());
        System.out.println("canonicalPath: " + file.getCanonicalPath());
    }
}
