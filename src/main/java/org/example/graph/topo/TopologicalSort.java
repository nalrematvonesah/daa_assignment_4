package org.example.graph.topo;

import org.example.graph.Edge;
import org.example.graph.Graph;

import java.util.*;

public class TopologicalSort {

    public List<Integer> kahnSort(Graph g) {
        int n = g.n;
        int[] indegree = new int[n];
        for (List<Edge> list : g.adj) {
            for (Edge e : list) {
                indegree[e.to]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<Integer>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);
            for (Edge e : g.adj.get(u)) {
                indegree[e.to]--;
                if (indegree[e.to] == 0) {
                    queue.add(e.to);
                }
            }
        }
        return order;
    }
}
