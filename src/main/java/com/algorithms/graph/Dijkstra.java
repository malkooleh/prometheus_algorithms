package com.algorithms.graph;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class Dijkstra {

    public static void main(String[] args) {
        Instant start = Instant.now();

        Map<Integer, Map<Integer, Integer>> mapData = readFile();

        int startVertex = 1;
        int endVertex = 6;
        Map<Integer, Integer> shortestWays = findShortestWay(mapData, startVertex, endVertex);
//        log.debug("result: {}", shortestWays);
        log.debug("Shortest way: {}", shortestWays.get(endVertex));

        log.debug("Execution time: {}, ms", Duration.between(start, Instant.now()).toMillis());
    }

    public static Map<Integer, Integer> findShortestWay(
            Map<Integer, Map<Integer, Integer>> graph,
            Integer startVertex,
            Integer endVertex
    ) {
        Map<Integer, Integer> vertexShortestWay = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(vertexShortestWay::get));
//        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> prevVerticesToCurrent = new HashMap<>();

        vertexShortestWay.put(startVertex, 0);
        queue.add(startVertex);
        while (!queue.isEmpty()) {
            // remove the minimum distance vertex from the priority queue
            Integer vertexFrom = queue.poll();
            if (Objects.equals(vertexFrom, endVertex)) {
                break;
            }
            if (graph.get(vertexFrom) != null) {
                for (Map.Entry<Integer, Integer> entry : graph.get(vertexFrom).entrySet()) {
                    Integer vertexTo = entry.getKey();
//                    log.debug("vertexFrom : {}, vertexTo : {}", vertexFrom, vertexTo);
//                    if (!visited.contains(vertexTo)) {
                        int newDistance = vertexShortestWay.get(vertexFrom) + entry.getValue();
                        if (vertexShortestWay.get(vertexTo) == null || newDistance <= vertexShortestWay.get(vertexTo)) {
                            if (vertexShortestWay.get(vertexTo) == null || newDistance < vertexShortestWay.get(vertexTo)) {
                                vertexShortestWay.put(vertexTo, newDistance);
                                // Add the current vertex to the queue
                                queue.remove(vertexTo);
                                queue.add(vertexTo);

                            }
                            addPreviousValueFor(prevVerticesToCurrent, vertexFrom, vertexTo);
                        }
                    }
                }
//            }
//            visited.add(vertexFrom);
        }

        log.debug("Finding the shortest path is complete");
        log.debug("The countPaths : {}", countPaths(prevVerticesToCurrent, endVertex, startVertex));
        return vertexShortestWay;
    }

    //-------------------------
    // A recursive method to count
    // all paths from 'u' to 'd'.
    private static int countPathsUtil(Map<Integer, List<Integer>> prevVerticesToCurrent, int u, int d, int pathCount) {

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

    // Returns count of
    // paths from 's' to 'd'
    private static int countPaths(Map<Integer, List<Integer>> prevVerticesToCurrent, int s, int d) {

        // Call the recursive method
        // to count all paths
        int pathCount = 0;
        pathCount = countPathsUtil(prevVerticesToCurrent, s, d, pathCount);
        return pathCount;
    }
    //-------------------------

    private static void addPreviousValueFor(
            Map<Integer, List<Integer>> prevVerticesToCurrent,
            Integer vertexFrom,
            Integer vertexTo
    ) {
        if (prevVerticesToCurrent.get(vertexTo) != null) {
            prevVerticesToCurrent.get(vertexTo).add(vertexFrom);
        } else {
            prevVerticesToCurrent.put(vertexTo, new ArrayList<>());
            prevVerticesToCurrent.get(vertexTo).add(vertexFrom);
        }
    }

    private static Map<Integer, Map<Integer, Integer>> readFile() {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        Set<Integer> setNumbers = new HashSet<>();

        int vertexFrom;
        try (Scanner scanner = new Scanner(
                new File("E:\\programming\\projects\\practice\\prometheus_algorithms\\src\\test\\resources\\dijkstra_graph_task_data"))) {

            // the first line contains the number of vertices and edges
            if (scanner.hasNext()) {
                int verticesCount = scanner.nextInt();
                int edgesCount = scanner.nextInt();
                log.debug("The number of vertices and edges is : {} / {}", verticesCount, edgesCount);
            }
            while (scanner.hasNext()) {
                vertexFrom = scanner.nextInt();
                if (!setNumbers.contains(vertexFrom)) {
                    setNumbers.add(vertexFrom);
                    map.put(vertexFrom, new HashMap<>());
                    map.get(vertexFrom).put(scanner.nextInt(), scanner.nextInt());
                } else {
                    map.get(vertexFrom).put(scanner.nextInt(), scanner.nextInt());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}
