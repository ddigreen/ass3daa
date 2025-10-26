package org.example.algo;

import org.example.graph.Edge;
import org.example.graph.Graph;
import org.example.graph.UnionFind.OperationCounter;

import java.util.*;

public class PrimAlgorithm {

    public static class PrimResult {
        public final List<Edge> mstEdges = new ArrayList<>();
        public long operations;
        public long millis;
        public int totalCost;
    }

    private static class PQEdge implements Comparable<PQEdge> {
        String from, to;
        int weight;
        PQEdge(String f, String t, int w) { from = f; to = t; weight = w; }
        @Override public int compareTo(PQEdge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public PrimResult run(Graph g) {
        long start = System.nanoTime();
        OperationCounter oc = new OperationCounter();

        PrimResult res = new PrimResult();

        if (g.vertexCount() == 0) {
            res.millis = 0;
            res.operations = 0;
            res.totalCost = 0;
            return res;
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<PQEdge> pq = new PriorityQueue<>();

        // start from an arbitrary node
        String startNode = g.nodes().iterator().next();
        visited.add(startNode);

        for (Edge e : g.neighbors(startNode)) {
            pq.offer(new PQEdge(e.from, e.to, e.weight)); oc.heapPushes++;
        }

        while (!pq.isEmpty() && res.mstEdges.size() < g.vertexCount() - 1) {
            PQEdge cur = pq.poll(); oc.heapPops++;
            // if 'to' already taken, skip
            if (visited.contains(cur.to)) continue;

            // accept this edge
            res.mstEdges.add(new Edge(cur.from, cur.to, cur.weight));
            res.totalCost += cur.weight;
            visited.add(cur.to);

            // push outgoing edges of the new node
            for (Edge e : g.neighbors(cur.to)) {
                if (!visited.contains(e.to)) {
                    pq.offer(new PQEdge(e.from, e.to, e.weight)); oc.heapPushes++;
                    oc.edgeRelaxations++; // treat as a "consider" action
                }
            }
        }

        res.operations = oc.total();
        res.millis = (System.nanoTime() - start) / 1_000_000;
        return res;
    }
}
