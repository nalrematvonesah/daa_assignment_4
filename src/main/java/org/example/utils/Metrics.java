package org.example.utils;

public class Metrics {
    public long startTime;
    public long endTime;
    public int operations;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getTimeMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void incOps() {
        operations++;
    }
}

