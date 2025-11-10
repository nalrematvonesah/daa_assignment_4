package org.example.graph.scc;

import org.example.graph.Edge;
import org.example.graph.Graph;
import java.util.*;

public class TarjanSCC {
    private int time;
    private int[] disc, low;
    private boolean[] inStack;
    private Deque<Integer> st;
    private List<List<Integer>> sccs;

    public List<List<Integer>> findSCCs(Graph g) {
        int n = g.n;
        time = 0;
        disc = new int[n];
        low = new int[n];
        inStack = new boolean[n];
        Arrays.fill(disc, -1);
        st = new ArrayDeque<>();
        sccs = new ArrayList<>();

        for (int i = 0; i < n; i++) if (disc[i] == -1) dfs(g, i);
        return sccs;
    }

    private void dfs(Graph g, int u) {
        disc[u] = low[u] = ++time;
        st.push(u);
        inStack[u] = true;

        for (Edge e : g.adj.get(u)) {
            int v = e.to;
            if (disc[v] == -1) {
                dfs(g, v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int v = st.pop();
                inStack[v] = false;
                comp.add(v);
                if (v == u) break;
            }
            sccs.add(comp);
        }
    }
}
