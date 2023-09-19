package com.algorithms.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Dijkstra_2 {

    private int v;
    private List<List<Pair>> adj;

    void init(int v) {
        this.v = v;
        adj = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v, int w) {
        adj.get(u).add(new Pair(v, w));
    }

    private record Pair(int first, int second) {
    }

    // works with a "Pair" object, where "first" is the distance and "second" is the next vertex
    void shortestPath(int from, int to) {
        PriorityQueue<Pair> pq = new PriorityQueue<>(v, Comparator.comparingInt(o -> o.first));
        int[] dist = new int[v + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Set<Integer> visited = new HashSet<>();

        pq.add(new Pair(0, from));
        dist[from] = 0;

        List<List<Integer>> prevVerticesToCurrent = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            prevVerticesToCurrent.add(new ArrayList<>());
        }
        while (!pq.isEmpty()) {
            int u = pq.poll().second;

            if (u == to) {
                break;
            }

            for (Pair pair : adj.get(u)) {
                int newDist = dist[u] + pair.second;
                if (!visited.contains(pair.first) && (dist[pair.first] >= newDist)) {
                    if (dist[pair.first] > newDist) {
                        dist[pair.first] = newDist;
                        pq.add(new Pair(dist[pair.first], pair.first));
                    }
                    prevVerticesToCurrent.get(pair.first).add(u);

                }
            }
            visited.add(u);
        }

        log.debug("Vertex Distance from Source");
//        for (int i = 0; i <= v; i++) {
        log.debug("Distance from {} to {} is {}", from, to, dist[to]);

        log.debug("The countPaths : {}", countPaths(prevVerticesToCurrent, to, from));
    }

    //-------------------------
    // Returns count of
    // paths from 's' to 'd'
    private static int countPaths(List<List<Integer>> prevVerticesToCurrent, int s, int d) {

        // Call the recursive method
        // to count all paths
        int pathCount = 0;
        pathCount = countPathsUtil(prevVerticesToCurrent, s, d, pathCount);
        return pathCount;
    }

    // A recursive method to count
    // all paths from 'u' to 'd'.
    private static int countPathsUtil(List<List<Integer>> prevVerticesToCurrent, int u, int d, int pathCount) {

        // If current vertex is same as
        // destination, then increment count
        if (u == d) {
            pathCount++;
        }

        // Recur for all the vertices
        // adjacent to this vertex
        else {
            for (int n : prevVerticesToCurrent.get(u)) {
                pathCount = countPathsUtil(prevVerticesToCurrent, n, d, pathCount);
            }
        }
        return pathCount;
    }
    //-------------------------

}
