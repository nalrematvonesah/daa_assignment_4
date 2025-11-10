package org.example.graph.scc;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class CondensationGraph {
    public static class Mapping {
        public final int[] compIndex;
        public Mapping(int[] compIndex) { this.compIndex = compIndex; }
    }

    public static Graph buildCondensation(Graph g, List<List<Integer>> sccs) {
        int k = sccs.size();
        Graph dag = new Graph(k);
        int[] compIndex = new int[g.n];
        for (int i = 0; i < sccs.size(); i++)
            for (int v : sccs.get(i)) compIndex[v] = i;

        Map<Long, Double> best = new HashMap<>(); // min weight per (cu,cv)
        for (int u = 0; u < g.n; u++) {
            for (Edge e : g.adj.get(u)) {
                int cu = compIndex[e.from], cv = compIndex[e.to];
                if (cu == cv) continue;
                long key = (((long)cu) << 32) | (cv & 0xffffffffL);
                best.merge(key, e.weight, Math::min);
            }
        }
        for (Map.Entry<Long, Double> en : best.entrySet()) {
            int cu = (int)(en.getKey() >> 32);
            int cv = (int)(long)en.getKey();
            dag.addEdge(cu, cv, en.getValue());
        }
        return dag;
    }

    public static int[] componentIndex(Graph g, List<List<Integer>> sccs) {
        int[] compIndex = new int[g.n];
        for (int i = 0; i < sccs.size(); i++)
            for (int v : sccs.get(i)) compIndex[v] = i;
        return compIndex;
    }
}
