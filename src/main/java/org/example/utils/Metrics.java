package org.example.utils;

public class Metrics {
    private long start;
    public long dfsVisits = 0;
    public long topoPushes = 0;
    public long topoPops = 0;
    public long relaxations = 0;
    public void start() { start = System.nanoTime(); }
    public long elapsedNanos() { return System.nanoTime() - start; }
    public void reset() { dfsVisits = topoPushes = topoPops = relaxations = 0; start = 0; }
}
