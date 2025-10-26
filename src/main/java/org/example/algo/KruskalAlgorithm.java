package org.example.algo;

import org.example.graph.Edge;
import org.example.graph.Graph;
import org.example.graph.UnionFind;
import org.example.graph.UnionFind.OperationCounter;

import java.util.*;

public class KruskalAlgorithm {

    public static class KruskalResult {
        public final List<Edge> mstEdges = new ArrayList<>();
        public long operations;
        public long millis;
        public int totalCost;
    }

    public KruskalResult run(Graph g) {
        long start = System.nanoTime();
        OperationCounter oc = new OperationCounter();

        KruskalResult res = new KruskalResult();

        UnionFind dsu = new UnionFind();
        for (String v : g.nodes()) dsu.makeSet(v);

        List<Edge> sorted = new ArrayList<>(g.edges());
        Collections.sort(sorted);

        for (Edge e : sorted) {
            boolean merged = dsu.union(e.from, e.to, oc);
            if (merged) {
                res.mstEdges.add(e);
                res.totalCost += e.weight;
                if (res.mstEdges.size() == g.vertexCount() - 1) break;
            }
        }

        res.operations = oc.total();
        res.millis = (System.nanoTime() - start) / 1_000_000;
        return res;
    }
}
