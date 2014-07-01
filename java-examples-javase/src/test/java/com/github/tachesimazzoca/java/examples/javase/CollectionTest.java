package com.github.tachesimazzoca.java.examples.javase;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionTest {
    @Test
    public void testArrayList() {
        testStringList(new ArrayList<String>());
    }

    @Test
    public void testLinkedList() {
        testStringList(new LinkedList<String>());
    }

    @Test
    public void testHashSet() {
        testIntegerSet(new HashSet<Integer>());
    }

    @Test
    public void testTreeSet() {
        testIntegerSet(new TreeSet<Integer>());
       
        Set<Integer> nums = new TreeSet<Integer>();
        for (int i = 0; i < 10; i++) {
            nums.add((int) (Math.random() * 100));
        }
        testSortedValues(nums);
    }

    @Test
    public void testHashMap() {
        testStringMap(new HashMap<String, String>());
    }

    @Test
    public void testTreeMap() {
        testStringMap(new TreeMap<String, String>());

        Map<String, String> m = new TreeMap<String, String>();
        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 100);
            m.put("key" + n, "value" + i);
        }
        testSortedValues(m.keySet());
    }

    @Test
    public void testLinkedHashMap() {
        testStringMap(new LinkedHashMap<String, String>());

        Map<String, String> m = new LinkedHashMap<String, String>();
        List<String> ks = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 100);
            String k = "key" + n;
            if (!ks.contains(k)) ks.add(k);
            m.put(k, "value" + i);
        }

        List<String> mks = new ArrayList<String>(m.keySet()); 
        int len = mks.size();
        for (int i = 0; i < len; i++) {
            assertEquals(mks.get(i), ks.get(i));
        }
    }

    private void testStringList(List<String> strings) {
        int i;
        for (i = 0; i < 10; i++) {
            strings.add("string" + i);
        }
        i = 0;
        for (String str : strings) {
            assertEquals("string" + i, str);
            i++;
        }

        i = 0;
        for (Iterator<String> it = strings.iterator(); it.hasNext();) {
            String str = it.next();
            assertEquals("string" + i, str);
            i++;
        }

        int len = strings.size();
        for (i = 0; i < len; i++) {
            String str = strings.get(i);
            assertEquals("string" + i, str);
        }
    }

    private void testIntegerSet(Set<Integer> nums) {
        final int N = 10;
        for (int i = 0; i < 100; i++) {
            nums.add(i % N);
        }
        assertTrue(nums.size() == N);
        for (int i = 0; i < N; i++) {
            assertTrue(nums.contains(i));
        }
    }

    private void testStringMap(Map<String, String> m) {
        final int N = 5;
        for (int i = 0; i < 10; i++) {
            m.put("key" + (i % N), "value" + i);
        }

        Set<String> ks = m.keySet();
        assertTrue(ks.size() == N);
        for (int i = 0; i < N; i++) {
            assertTrue(ks.contains("key" + i));
        }
        assertFalse(ks.contains("key" + N));

        for (int i = 0; i < N; i++) {
            assertEquals("value" + (5 + i), m.get("key" + i));
        }
    }

    private <T extends Comparable<? super T>>
            void testSortedValues (Iterable<T> vs) {
        T a = null;
        for (T b : vs) {
            if (a != null) {
                assertTrue(b.compareTo(a) >= 0);
            }
            a = b;
        }
    }
}
