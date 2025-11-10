package org.example.graph;

import java.util.*;

public class Graph {
    public final int n;
    public final List<List<Edge>> adj;
    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }
    public void addEdge(int u, int v, double w) {
        adj.get(u).add(new Edge(u, v, w));
    }
    public int edgesCount() {
        int m = 0;
        for (List<Edge> es : adj) m += es.size();
        return m;
    }
}
