package org.example.graph.scc;


import org.example.graph.Edge;
import org.example.graph.Graph;

import java.util.*;

public class TarjanSCC {
    private int time;
    private int[] disc;
    private int[] low;
    private boolean[] stackMember;
    private Deque<Integer> stack;
    private List<List<Integer>> sccList;

    public List<List<Integer>> findSCCs(Graph g) {
        int n = g.n;
        time = 0;
        disc = new int[n];
        low = new int[n];
        stackMember = new boolean[n];
        stack = new ArrayDeque<Integer>();
        sccList = new ArrayList<List<Integer>>();

        Arrays.fill(disc, -1);

        for (int i = 0; i < n; i++) {
            if (disc[i] == -1) {
                dfs(g, i);
            }
        }

        return sccList;
    }

    private void dfs(Graph g, int u) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        stackMember[u] = true;

        for (Edge edge : g.adj.get(u)) {
            int v = edge.to;
            if (disc[v] == -1) {
                dfs(g, v);
                low[u] = Math.min(low[u], low[v]);
            } else if (stackMember[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> component = new ArrayList<Integer>();
            while (true) {
                int v = stack.pop();
                stackMember[v] = false;
                component.add(v);
                if (v == u) break;
            }
            sccList.add(component);
        }
    }
}
