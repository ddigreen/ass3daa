package org.example.graph;

import java.util.*;

public class Graph {
    private final Set<String> nodes = new LinkedHashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    private final Map<String, List<Edge>> adj = new HashMap<>();

    public void addNode(String v) {
        nodes.add(v);
        adj.computeIfAbsent(v, k -> new ArrayList<>());
    }

    public void addUndirectedEdge(String u, String v, int w) {
        addNode(u);
        addNode(v);
        Edge e = new Edge(u, v, w);
        edges.add(e);
        // undirected: add both ways for adjacency
        adj.get(u).add(e);
        adj.get(v).add(new Edge(v, u, w));
    }

    public Set<String> nodes() { return nodes; }
    public List<Edge> edges() { return edges; }
    public List<Edge> neighbors(String v) { return adj.getOrDefault(v, List.of()); }

    public int vertexCount() { return nodes.size(); }
    public int edgeCount() { return edges.size(); }
}
