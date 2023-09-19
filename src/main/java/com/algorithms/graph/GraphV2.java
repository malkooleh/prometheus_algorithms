package com.algorithms.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GraphV2 {

    private Map<Integer, List<Integer>> adjVertices;
    private ArrayList<Integer> visited;
    private LinkedList<Integer> traverse;

    GraphV2() {
        this.adjVertices = new HashMap<>();
        this.visited = new ArrayList<>();
        this.traverse = new LinkedList<>();
    }

    public static GraphV2 buildGraph(List<String> data) {
        GraphV2 graph = new GraphV2();
        data.forEach(s -> Arrays.stream(s.split(" "))
                .mapToInt(Integer::parseInt).boxed()
                .reduce((from, to) -> {
                    graph.addVertex(from);
                    graph.addVertex(to);
                    graph.addEdge(from, to);
                    return from;
                }).orElseThrow());
        return graph;
    }

    void addVertex(Integer val) {
        adjVertices.putIfAbsent(val, new ArrayList<>());
    }

    /**
     * Method creates a new Edge and updates the adjacent vertices Map.
     */
    void addEdge(Integer from, Integer to) {
        adjVertices.get(from).add(to);
    }

    public List<Integer> strongConnectedComponent() {
        ArrayList<Integer> strongConnectedComponents = new ArrayList<>();

        adjVertices.keySet().forEach(v -> {
            if (!visited.contains(v)) {
                depthFirstTraversal(v);
            }
        });

        transposeAdjVertices();

        while (!traverse.isEmpty()) {
            Integer maxWeight = traverse.pollLast();
            if (!visited.contains(maxWeight)) {
//                log.debug("Start dfs for {}", maxWeight);
                int depth = depthFirstTraversal(maxWeight);
                strongConnectedComponents.add(depth);
            }
        }

        return strongConnectedComponents.stream()
                .sorted((o1, o2) -> o2 - o1)
                .toList();
    }

    // depthCounter - depth of visited vertices started from 'root' vertex
    private int depthFirstTraversal(Integer root) {
        int depthCounter = 0;
        Deque<Integer> deque = new LinkedList<>();
        deque.push(root);
        visited.add(root);
        while (!deque.isEmpty()) {
            List<Integer> adjVerticesTo = adjVertices.get(deque.peek());
//            log.debug("adjVerticesTo = {}", adjVerticesTo);
            if (adjVerticesTo != null && !adjVerticesTo.isEmpty()) {
                for (int i = 0; i < adjVerticesTo.size(); i++) {
//                    log.debug("iter {}", i);
                    Integer vertex = adjVerticesTo.get(i);
                    if (!visited.contains(vertex)) {
//                        log.debug("visit the vertex = {}", vertex);
                        visited.add(vertex);
                        deque.push(vertex);
                        break;
                    } else if (i == adjVerticesTo.size() - 1) {
                        traverse.add(deque.peek());
                        deque.pop();
                        depthCounter += 1;
//                        log.debug("vertex = {}, label = {}", vertex, depthCounter);
                    }
                }
            } else {
                deque.pop();
            }
        }
        return depthCounter;
    }

    private void transposeAdjVertices() {
        Map<Integer, List<Integer>> transposed = new HashMap<>();
        visited = new ArrayList<>();

        adjVertices.forEach((key, value) -> {
            if (value != null && !value.isEmpty()) {
                value.forEach(vertex -> {
                    transposed.putIfAbsent(vertex, new ArrayList<>());
                    transposed.get(vertex).add(key);
                });
            }
        });

        adjVertices = transposed;
    }
}
