package com.github.tachesimazzoca.java.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public final class CollectionExample {
    private CollectionExample() {
    }

    public static void main(String[] args) {
        // ArrayList<E>
        System.out.println("List<E>");
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            strings.add("string" + i);
        }
        for (String str : strings) {
            System.out.println(str);
        }
        // Iterable#iterator()
        for (Iterator<String> it = strings.iterator(); it.hasNext();) {
            String str = it.next();
            System.out.println(str);
        }
        // AbstractList#get(int index)
        int len = strings.size();
        for (int i = 0; i < len; i++) {
            String str = strings.get(i);
            System.out.println(str);
        }

        // HashSet<E>
        System.out.println("Set<E>");
        Set<Integer> nums = new HashSet<Integer>();
        for (int i = 0; i < 100; i++) {
            nums.add(i % 10);
        }
        for (Integer num : nums) {
            System.out.println(num);
        }

        // HashMap<K, V>
        System.out.println("Map<K, V>");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, "value" + (i % 5));
        }
        // Set<K> Map<K, V>#keySet()
        for (String k : map.keySet()) {
            System.out.println(k);
        }
        // Collection<V> Map<K, V>#values()
        for (String v : map.values()) {
            System.out.println(v);
        }
        printMap(map);

        // TreeMap<K, V>
        System.out.println("TreeMap<K, V>");
        Map<Integer, String> tree = new TreeMap<Integer, String>();
        for (int i = 9; i >= 0; i--) {
            tree.put(i, "value" + (i % 5));
        }
        printMap(tree);

        System.out.println("TreeMap<K, V>(Map<? extends K,? extends V> m)");
        Map<String, String> map2 = new TreeMap<String, String>(map);
        printMap(map2);

        // LinkedHashMap<K, V>
        System.out.println("LinkedHashMap<K, V>");
        Map<Integer, Integer> lmap = new LinkedHashMap<Integer, Integer>();
        for (int i = 9; i >= 0; i--) {
            lmap.put(i % 5, i);
        }
        printMap(lmap);
    }

    private static <K, V> void printMap(Map<K, V> map) {
        // Set<Map.Entry<K, V>> Map<K, V>#entrySet()
        for (Entry<K, V> entry : map.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(k);
            sb.append(":");
            sb.append(v);
            System.out.println(sb.toString());
        }
    }
}
