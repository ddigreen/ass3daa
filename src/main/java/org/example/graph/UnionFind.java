package org.example.graph;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    public void makeSet(String x) {
        parent.put(x, x);
        rank.put(x, 0);
    }

    public String find(String x, OperationCounter counter) {
        // path compression
        if (!parent.get(x).equals(x)) {
            counter.comparisons++; // comparing parent != self
            parent.put(x, find(parent.get(x), counter));
        }
        return parent.get(x);
    }

    public boolean union(String x, String y, OperationCounter counter) {
        String rx = find(x, counter);
        String ry = find(y, counter);
        counter.comparisons++; // compare roots equality
        if (rx.equals(ry)) return false;

        int rxRank = rank.get(rx);
        int ryRank = rank.get(ry);
        counter.comparisons++; // compare ranks

        if (rxRank < ryRank) {
            parent.put(rx, ry);
        } else if (rxRank > ryRank) {
            parent.put(ry, rx);
        } else {
            parent.put(ry, rx);
            rank.put(rx, rxRank + 1);
        }
        counter.unions++;
        return true;
    }

    /** Simple struct to count algorithmic actions */
    public static class OperationCounter {
        public long comparisons = 0;
        public long unions = 0;
        public long heapPushes = 0;
        public long heapPops = 0;
        public long edgeRelaxations = 0;

        public long total() {
            return comparisons + unions + heapPushes + heapPops + edgeRelaxations;
        }
    }
}
