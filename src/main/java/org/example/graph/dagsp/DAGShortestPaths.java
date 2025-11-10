package org.example.graph.dagsp;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class DAGShortestPaths {

    public static class Result {
        public final double[] dist;
        public final int[] parent;
        public Result(double[] d, int[] p) { this.dist = d; this.parent = p; }
    }

    public Result shortestPath(Graph g, int src, List<Integer> topo) {
        double[] dist = new double[g.n];
        int[] par = new int[g.n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(par, -1);
        dist[src] = 0.0;

        for (int u : topo) {
            if (dist[u] == Double.POSITIVE_INFINITY) continue;
            for (Edge e : g.adj.get(u)) {
                double nd = dist[u] + e.weight;
                if (nd < dist[e.to]) {
                    dist[e.to] = nd;
                    par[e.to] = u;
                }
            }
        }
        return new Result(dist, par);
    }

    public Result longestPath(Graph g, int src, List<Integer> topo) {
        double[] dist = new double[g.n];
        int[] par = new int[g.n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        Arrays.fill(par, -1);
        dist[src] = 0.0;

        for (int u : topo) {
            if (dist[u] == Double.NEGATIVE_INFINITY) continue;
            for (Edge e : g.adj.get(u)) {
                double nd = dist[u] + e.weight;
                if (nd > dist[e.to]) {
                    dist[e.to] = nd;
                    par[e.to] = u;
                }
            }
        }
        return new Result(dist, par);
    }

    public static List<Integer> reconstructPath(int target, int[] parent) {
        if (target < 0) return Collections.emptyList();
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }
}
