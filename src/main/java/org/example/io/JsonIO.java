package org.example.io;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import org.example.graph.Graph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonIO {


    public static class InEdge {
        public String from;
        public String to;
        public int weight;
    }
    public static class InGraph {
        public int id;
        public List<String> nodes;
        public List<InEdge> edges;
    }
    public static class InRoot {
        public List<InGraph> graphs;
    }


    public static List<GraphBundle> readGraphs(InputStream inputStream) throws IOException {
        try (Reader r = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            InRoot root = gson.fromJson(r, InRoot.class);
            List<GraphBundle> list = new ArrayList<>();
            if (root != null && root.graphs != null) {
                for (InGraph g : root.graphs) {
                    Graph G = new Graph();
                    if (g.nodes != null) {
                        for (String v : g.nodes) G.addNode(v);
                    }
                    if (g.edges != null) {
                        for (InEdge e : g.edges) G.addUndirectedEdge(e.from, e.to, e.weight);
                    }
                    list.add(new GraphBundle(g.id, G));
                }
            }
            return list;
        }
    }

    public record GraphBundle(int id, Graph graph) {}


    public static class OutputEdge {
        public String from;
        public String to;
        public int weight;
        public OutputEdge(String f, String t, int w) { from = f; to = t; weight = w; }
    }

    public static class AlgoBlock {
        @SerializedName("mst_edges") public List<OutputEdge> mstEdges = new ArrayList<>();
        @SerializedName("total_cost") public int totalCost;
        @SerializedName("operations_count") public long operationsCount;
        @SerializedName("execution_time_ms") public double executionTimeMs;
    }

    public static class InputStats {
        public int vertices;
        public int edges;
    }

    public static class PerGraphResult {
        @SerializedName("graph_id") public int graphId;
        @SerializedName("input_stats") public InputStats inputStats = new InputStats();
        public AlgoBlock prim = new AlgoBlock();
        public AlgoBlock kruskal = new AlgoBlock();
    }

    public static class OutRoot {
        public List<PerGraphResult> results = new ArrayList<>();
    }

    public static void writeOutput(String path, OutRoot out) throws IOException {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(out, w);
        }
    }
}
