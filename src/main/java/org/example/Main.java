package org.example;

import org.example.algo.KruskalAlgorithm;
import org.example.algo.PrimAlgorithm;
import org.example.graph.Edge;
import org.example.io.JsonIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        // --- Handle arguments or default paths ---
        String inputPath;
        String outputPath;

        if (args.length >= 2) {
            inputPath = args[0];
            outputPath = args[1];
        } else {
            inputPath = "src/resources/ass_3_input.json";
            outputPath = "src/resources/ass_3_output.json";
            System.out.println("Using default paths:\nInput: " + inputPath + "\nOutput: " + outputPath);
        }

        // --- Validate input file ---
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            System.err.println("Error: Input file not found: " + inputPath);
            System.exit(1);
        }

        // --- Prepare streams and files ---
        InputStream inputStream = new FileInputStream(inputFile);
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();

        // --- Read graphs ---
        var bundles = JsonIO.readGraphs(inputStream);

        KruskalAlgorithm kr = new KruskalAlgorithm();
        PrimAlgorithm pr = new PrimAlgorithm();
        JsonIO.OutRoot out = new JsonIO.OutRoot();

        // --- Process each graph ---
        for (var bundle : bundles) {
            var G = bundle.graph();
            var per = new JsonIO.PerGraphResult();
            per.graphId = bundle.id();
            per.inputStats.vertices = G.vertexCount();
            per.inputStats.edges = G.edgeCount();

            // Prim’s algorithm
            var prRes = pr.run(G);
            per.prim.totalCost = prRes.totalCost;
            per.prim.operationsCount = prRes.operations;
            per.prim.executionTimeMs = prRes.millis;
            for (Edge e : prRes.mstEdges) {
                per.prim.mstEdges.add(new JsonIO.OutputEdge(e.from, e.to, e.weight));
            }

            // Kruskal’s algorithm
            var krRes = kr.run(G);
            per.kruskal.totalCost = krRes.totalCost;
            per.kruskal.operationsCount = krRes.operations;
            per.kruskal.executionTimeMs = krRes.millis;
            for (Edge e : krRes.mstEdges) {
                per.kruskal.mstEdges.add(new JsonIO.OutputEdge(e.from, e.to, e.weight));
            }

            // Compare results
            if (prRes.totalCost != krRes.totalCost) {
                System.err.printf(
                        "WARNING: Graph %d MST costs differ: Prim=%d Kruskal=%d%n",
                        bundle.id(), prRes.totalCost, krRes.totalCost
                );
            }

            out.results.add(per);
        }

        // --- Write results ---
        JsonIO.writeOutput(outputPath, out);
        System.out.println("✅ Results written to: " + outputFile.getAbsolutePath());
    }
}
