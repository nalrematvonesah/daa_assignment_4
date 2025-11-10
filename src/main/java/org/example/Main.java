package org.example;

import com.google.gson.Gson;
import org.example.graph.*;
import org.example.graph.dagsp.DAGShortestPaths;
import org.example.graph.scc.CondensationGraph;
import org.example.graph.scc.TarjanSCC;
import org.example.graph.topo.TopologicalSort;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static List<Integer> deriveTaskOrder(List<Integer> topoComps, List<List<Integer>> sccs) {
        List<Integer> order = new ArrayList<>();
        for (int c : topoComps) order.addAll(sccs.get(c));
        return order;
    }

    public static void main(String[] args) throws Exception {
        // Determine datasets to run
        List<Path> files = new ArrayList<>();
        if (args.length > 0) {
            files.add(Path.of(args[0]));
        } else {
            File dir = new File("data");
            if (dir.exists()) {
                for (File f : Objects.requireNonNull(dir.listFiles((d, name) -> name.endsWith(".json")))) {
                    files.add(f.toPath());
                }
            } else {
                System.err.println("No data directory found. Put JSON datasets under ./data");
                return;
            }
        }

        System.out.println("=== Assignment 4 Runner ===");
        for (Path p : files) {
            System.out.println("\n--- Dataset: " + p.getFileName() + " ---");
            GraphLoader.GraphData data = GraphLoader.loadFromJson(p.toString());
            Graph g = data.graph;
            int srcNode = data.meta.source;

            System.out.println("Nodes n = " + g.n + ", edges m = " + g.edgesCount());
            System.out.println("Weight model: " + data.meta.weightModel);
            System.out.println("Source node: " + srcNode);

            // 1) SCCs
            TarjanSCC tarjan = new TarjanSCC();
            List<List<Integer>> sccs = tarjan.findSCCs(g);
            System.out.println("SCC count = " + sccs.size());
            for (int i = 0; i < sccs.size(); i++) {
                System.out.println("  SCC " + i + " (size " + sccs.get(i).size() + "): " + sccs.get(i));
            }

            // 2) Condensation DAG
            Graph dag = CondensationGraph.buildCondensation(g, sccs);
            System.out.println("Condensation DAG: " + dag.n + " nodes, " + dag.edgesCount() + " edges");
            for (int u = 0; u < dag.n; u++) {
                for (Edge e : dag.adj.get(u)) {
                    System.out.println("  " + e.from + " -> " + e.to + " : " + e.weight);
                }
            }

            // Mapping from node to component
            int[] compIndex = new int[g.n];
            for (int i = 0; i < sccs.size(); i++) for (int v : sccs.get(i)) compIndex[v] = i;

            // 3) Topological order on condensation
            TopologicalSort topo = new TopologicalSort();
            List<Integer> topoOrder = topo.kahnSort(dag);
            System.out.println("Topological order (components): " + topoOrder);
            System.out.println("Derived order of original tasks: " + deriveTaskOrder(topoOrder, sccs));

            // 4) Shortest & Longest paths on DAG from the SCC containing src
            int srcComp = compIndex[srcNode];
            DAGShortestPaths sp = new DAGShortestPaths();
            DAGShortestPaths.Result shortest = sp.shortestPath(dag, srcComp, topoOrder);
            DAGShortestPaths.Result longest  = sp.longestPath(dag, srcComp, topoOrder);

            System.out.println("Shortest distances from comp " + srcComp + ": " + Arrays.toString(shortest.dist));
            System.out.println("Longest  distances from comp " + srcComp + ": " + Arrays.toString(longest.dist));

            // Pick farthest target for critical path
            int best = -1; double bestd = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < longest.dist.length; i++) if (longest.dist[i] > bestd) { bestd = longest.dist[i]; best = i; }
            List<Integer> critCompPath = DAGShortestPaths.reconstructPath(best, longest.parent);
            System.out.println("Critical path (components): " + critCompPath + ", length = " + bestd);

            // Example shortest path to every reachable comp: pick one example (best)
            int bestS = -1; double bestSd = Double.POSITIVE_INFINITY;
            for (int i = 0; i < shortest.dist.length; i++) if (shortest.dist[i] < bestSd) { bestSd = shortest.dist[i]; bestS = i; }
            List<Integer> bestShortPath = DAGShortestPaths.reconstructPath(bestS, shortest.parent);
            System.out.println("Example shortest path (components) to comp " + bestS + ": " + bestShortPath + ", length = " + bestSd);
        }
    }
}
