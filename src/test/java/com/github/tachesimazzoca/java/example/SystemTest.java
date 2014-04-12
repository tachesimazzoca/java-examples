package com.github.tachesimazzoca.java.example;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

public class SystemTest {
    @Test
    public void testArraycopy() {
        int[] a = { 0, 1, 2, 3, 4 };
        int[] b = { 0, 0, 0, 0 };
        System.arraycopy(a, 0, b, 0, 2);
        System.arraycopy(a, 3, b, 2, 2);
        int[] expected = { 0, 1, 3, 4 };
        assertTrue(Arrays.equals(expected, b));
    }
}
