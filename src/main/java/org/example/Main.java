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
        System.out.println("\nStrongly Connected Components:");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("  SCC " + i + ": " + sccs.get(i));
        }

        Graph dag = CondensationGraph.buildCondensation(g, sccs);
        System.out.println("\nCondensation DAG built with " + dag.n + " nodes.");

        TopologicalSort topo = new TopologicalSort();
        List<Integer> topoOrder = topo.kahnSort(dag);
        System.out.println("Topological order: " + topoOrder);

        DAGShortestPaths sp = new DAGShortestPaths();

        double[] shortest = sp.shortestPath(dag, 0, topoOrder);
        double[] longest = sp.longestPath(dag, 0, topoOrder);

        System.out.println("\nShortest path distances (from SCC 0): " + Arrays.toString(shortest));
        System.out.println("Longest path distances (from SCC 0): " + Arrays.toString(longest));
    }
}
