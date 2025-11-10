package org.example.graph.topo;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class TopologicalSort {
    public List<Integer> kahnSort(Graph g) {
        int n = g.n;
        int[] indeg = new int[n];
        for (List<Edge> es : g.adj) for (Edge e : es) indeg[e.to]++;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>(n);
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (Edge e : g.adj.get(u)) {
                if (--indeg[e.to] == 0) q.add(e.to);
            }
        }
        if (order.size() != n) {
            throw new IllegalStateException("Graph is not a DAG (cycle detected in condensation).");
        }
        return order;
    }
}
