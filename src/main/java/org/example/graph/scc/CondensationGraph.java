package org.example.graph.scc;

import org.example.graph.Edge;
import org.example.graph.Graph;

import java.util.*;

public class CondensationGraph {

    public static Graph buildCondensation(Graph g, List<List<Integer>> sccs) {
        int k = sccs.size();
        Graph dag = new Graph(k);

        int[] compIndex = new int[g.n];
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) {
                compIndex[v] = i;
            }
        }

        Set<String> addedEdges = new HashSet<String>();

        for (int u = 0; u < g.n; u++) {
            for (Edge e : g.adj.get(u)) {
                int cu = compIndex[e.from];
                int cv = compIndex[e.to];
                if (cu != cv) {
                    String key = cu + "-" + cv;
                    if (!addedEdges.contains(key)) {
                        dag.addEdge(cu, cv, e.weight);
                        addedEdges.add(key);
                    }
                }
            }
        }

        return dag;
    }
}

