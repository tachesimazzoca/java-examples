package com.github.tachesimazzoca.java.examples.javase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentHashMapExample {
    private ConcurrentHashMapExample() {
    }

    public static void main(String[] args) throws InterruptedException {
        final int MAX_ATTEMPTS = 10;

        int numCores = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            System.out.println(String.format("-- Attempt %d in %d threads", i + 1, numCores));

            long t = System.currentTimeMillis();
            // ConcurrentHashMap can be used safely in multiple thread.
            ConcurrentHashMap<Integer, Integer> chm = new ConcurrentHashMap<Integer, Integer>();
            runInThreads(chm, numCores);
            System.out.println(String.format(
                    "ConcurrentHashMap: %d millis", System.currentTimeMillis() - t));

            // Collections.synchronizedMap makes the given map synchronized.
            t = System.currentTimeMillis();
            Map<Integer, Integer> sm = Collections.synchronizedMap(new HashMap<Integer, Integer>());
            runInThreads(sm, numCores);
            System.out.println(String.format(
                    "Collections.synchronizedMap: %d millis", System.currentTimeMillis() - t));

            // Using HashMap in multiple threads might cause an infinite loop.
            t = System.currentTimeMillis();
            HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
            runInThreads(hm, numCores);
            System.out.println(String.format(
                    "HashMap: %d millis", System.currentTimeMillis() - t));
        }
    }

    private static class CreateMapTask implements Runnable {
        private Map<Integer, Integer> map;

        public CreateMapTask(Map<Integer, Integer> map) {
            this.map = map;
        }

        public void run() {
            for (int i = 0; i < 1000000; i++) {
                int k = i % 1000;
                if (map.containsKey(k)) {
                    map.remove(k);
                } else {
                    map.put(k, i);
                }
            }
        }
    }

    private static void runInThreads(Map<Integer, Integer> m, int numberOfThreads)
            throws InterruptedException {
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new CreateMapTask(m));
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }
    }
}
