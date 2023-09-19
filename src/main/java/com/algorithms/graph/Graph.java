package com.algorithms.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Graph {

    private Map<Vertex, List<Vertex>> adjVertices;

    Graph() {
        this.adjVertices = new HashMap<>();
    }

    public static Graph buildGraph(List<String> data) {
        Graph graph = new Graph();
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
        adjVertices.putIfAbsent(new Vertex(val), new ArrayList<>());
    }

    void removeVertex(Integer val) {
        Vertex v = new Vertex(val);
        adjVertices.values().forEach(vertices -> vertices.remove(v));// remove edges to 'val'
        adjVertices.remove(new Vertex(val));// remove 'val'
    }

    /**
     * Method creates a new Edge and updates the adjacent vertices Map.
     */
    void addEdge(Integer from, Integer to) {
        Vertex v1 = new Vertex(from);
        Optional<Vertex> v2 = adjVertices.keySet().stream()
                .filter(vertex -> vertex.val.equals(to))
                .findFirst();
        adjVertices.get(v1).add(v2.orElse(new Vertex(to)));
    }

    void removeEdge(Integer val1, Integer val2) {
        Vertex v1 = new Vertex(val1);
        Vertex v2 = new Vertex(val2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null) eV1.remove(v2);
        if (eV2 != null) eV2.remove(v1);
    }

    List<Vertex> getAdjVerticesTo(Integer val) {
        return adjVertices.get(new Vertex(val));
    }

    static Set<Integer> breadthFirstSearch(Graph graph, Integer root) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        visited.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            graph.getAdjVerticesTo(v).forEach(vertex -> {
                if (!visited.contains(vertex.val)) {
                    visited.add(vertex.val);
                    queue.add(vertex.val);
                }
            });
        }
        return visited;
    }

    void depthFirstTraversal(Vertex root, AtomicInteger label) {
        Deque<Vertex> deque = new LinkedList<>();
        deque.push(root);
        root.visited = true;
        while (!deque.isEmpty()) {
            List<Vertex> adjVerticesTo = adjVertices.get(deque.peek());
//            log.debug("adjVerticesTo = {}", adjVerticesTo);
            if (adjVerticesTo != null && !adjVerticesTo.isEmpty()) {
                for (int i = 0; i < adjVerticesTo.size(); i++) {
//                    log.debug("iter {}", i);
                    Vertex vertex = adjVerticesTo.get(i);
                    if (!vertex.visited) {
//                        log.debug("visit the vertex = {}", vertex.val);
                        vertex.visited = true;
                        deque.push(vertex);
                        break;
                    } else if (i == adjVerticesTo.size() - 1) {
                        deque.peek().weight = label.incrementAndGet();
//                        log.debug("vertex = {}, label = {}", vertex.val, label);
                        deque.pop();
                    }
                }
            } else {
                deque.pop();
            }
        }
    }

    public void topologicalSort() {
        AtomicInteger label = new AtomicInteger(adjVertices.size());
        adjVertices.keySet().forEach(v -> {
            log.debug("topologicalSort on {}", v.val);
            if (!v.visited) {
                depthFirstTraversal(v, label);
            }
        });
    }

    public List<Integer> strongConnectedComponent() {
        AtomicInteger time = new AtomicInteger(0);
        ArrayList<Integer> strongConnectedComponents = new ArrayList<>();

        adjVertices.keySet().forEach(v -> {
            if (!v.visited) {
                depthFirstTraversal(v, time);
            }
        });

        transposeAdjVertices();

        List<Vertex> sortedVertices = adjVertices.keySet().stream()
                .sorted((o1, o2) -> o2.weight - o1.weight)
                .toList();
        sortedVertices.forEach(max -> {
            time.set(0);
            if (!max.visited) {
//                log.debug("Start dfs for {}", max);
                depthFirstTraversal(max, time);
                strongConnectedComponents.add(time.get());
            }
        });

        return strongConnectedComponents.stream()
                .sorted((o1, o2) -> o2 - o1)
                .toList();
    }

    public void transposeAdjVertices() {
        Map<Vertex, List<Vertex>> graphAdjVertices = adjVertices;
        Map<Vertex, List<Vertex>> transposed = new HashMap<>();

        graphAdjVertices.forEach((key, value) -> {
            key.visited = false;
            if (value != null && !value.isEmpty()) {
                value.forEach(vertex -> {
                    vertex.visited = false;
                    transposed.putIfAbsent(vertex, new ArrayList<>());
                    transposed.get(vertex).add(key);
                });
            }
        });

        adjVertices = transposed;
    }

    protected static class Vertex {
        Integer val;
        boolean visited;
        Integer weight;

        Vertex(Integer val) {
            this.val = val;
            this.visited = false;
            this.weight = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            return val.equals(vertex.val);
        }

        @Override
        public int hashCode() {
            return val.hashCode();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "val=" + val +
                    '}';
        }
    }
}
