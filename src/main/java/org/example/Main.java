package org.example;

import org.example.graph.Graph;
import org.example.graph.GraphLoader;
import org.example.graph.scc.TarjanSCC;
import org.example.graph.scc.CondensationGraph;
import org.example.graph.dagsp.DAGShortestPaths;
import org.example.graph.topo.TopologicalSort;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        GraphLoader.GraphData data = GraphLoader.loadFromJson("data/tasks.json");
        Graph g = data.graph;
        int src = data.meta.source;

        System.out.println("Loaded graph with " + g.n + " nodes.");
        System.out.println("Weight model: " + data.meta.weightModel);
        System.out.println("Source node: " + src);

        TarjanSCC tarjan = new TarjanSCC();
        List<List<Integer>> sccs = tarjan.findSCCs(g);
        int[] compIndex = new int[g.n];
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) compIndex[v] = i;
        }

// Build condensation DAG
        Graph dag = CondensationGraph.buildCondensation(g, sccs);

// Topo order on DAG
        TopologicalSort topo = new TopologicalSort();
        List<Integer> topoOrder = topo.kahnSort(dag);

// Use the SCC that contains the JSON "source" node
        int srcNode = data.meta.source;       // 4 from your JSON
        int srcComp = compIndex[srcNode];     // SCC index containing node 4

        DAGShortestPaths sp = new DAGShortestPaths();
        double[] shortest = sp.shortestPath(dag, srcComp, topoOrder);
        double[] longest  = sp.longestPath(dag, srcComp, topoOrder);

        System.out.println("\nSource node: " + srcNode + " is in SCC " + srcComp);
        System.out.println("Shortest (from SCC " + srcComp + "): " + Arrays.toString(shortest));
        System.out.println("Longest  (from SCC " + srcComp + "): " + Arrays.toString(longest));
    }
}
