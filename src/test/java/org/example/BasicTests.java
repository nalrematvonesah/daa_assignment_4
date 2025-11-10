package org.example;

import org.example.graph.Graph;
import org.example.graph.scc.TarjanSCC;
import org.example.graph.topo.TopologicalSort;
import org.example.graph.dagsp.DAGShortestPaths;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BasicTests {

    @Test
    public void sccSimple() {
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,0,1);
        g.addEdge(1,2,1);
        TarjanSCC t = new TarjanSCC();
        var sccs = t.findSCCs(g);
        assertTrue(sccs.size() >= 2);
    }

    @Test
    public void topoDAG() {
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        var topo = new TopologicalSort().kahnSort(g);
        assertEquals(3, topo.size());
        assertTrue(topo.indexOf(0) < topo.indexOf(1) && topo.indexOf(1) < topo.indexOf(2));
    }

    @Test
    public void dagSP() {
        Graph g = new Graph(4);
        g.addEdge(0,1,2); g.addEdge(0,2,1); g.addEdge(2,1,1); g.addEdge(1,3,3); g.addEdge(2,3,5);
        var topo = Arrays.asList(0,2,1,3);
        var res = new DAGShortestPaths().shortestPath(g, 0, topo);
        assertEquals(0.0, res.dist[0]);
        assertEquals(2.0, res.dist[2]);
        assertEquals(4.0, res.dist[1]);
        assertEquals(7.0, res.dist[3]); // 0->1->3
    }
}
