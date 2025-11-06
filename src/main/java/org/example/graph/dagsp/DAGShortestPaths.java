package org.example.graph.dagsp;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class DAGShortestPaths {

    public double[] shortestPath(Graph g, int src, List<Integer> topo) {
        double[] dist = new double[g.n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo) {
            if (dist[u] != Double.POSITIVE_INFINITY) {
                for (Edge e : g.adj.get(u)) {
                    if (dist[e.to] > dist[u] + e.weight) {
                        dist[e.to] = dist[u] + e.weight;
                    }
                }
            }
        }
        return dist;
    }

    public double[] longestPath(Graph g, int src, List<Integer> topo) {
        double[] dist = new double[g.n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo) {
            if (dist[u] != Double.NEGATIVE_INFINITY) {
                for (Edge e : g.adj.get(u)) {
                    if (dist[e.to] < dist[u] + e.weight) {
                        dist[e.to] = dist[u] + e.weight;
                    }
                }
            }
        }
        return dist;
    }
}
