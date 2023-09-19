package com.algorithms.graph;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class GraphV3 {
    static List<Integer> tListIndices;
    static List<Integer> result = new ArrayList<>();

    // It works faster than previous ones for the task (with a large number of vertices) where the vertices are from 1 to 875714.
    public static void main(String[] args) {
        Instant start = Instant.now();

        Map<Integer, List<Integer>> mapData = readFile();

        tListIndices = new ArrayList<>(mapData.size());
        for (int i = 0; i < mapData.size(); i++) {
            tListIndices.add(0);
        }

        strongConnectedComponent(mapData);

        List<Integer> sorted = result.stream()
                .sorted((o1, o2) -> o2 - o1)
                .toList();
        // {434821, 968, 459, 313, 211} - correct answer
        log.debug("result: {}", sorted.subList(0, 5));

        log.debug("Execution time: {}, ms", Duration.between(start, Instant.now()).toMillis());
    }

    private static void strongConnectedComponent(Map<Integer, List<Integer>> graphData) {
        dfsloop(graphData);
        graphData = transposed(graphData);
        dfsrLoopAfter(graphData);
    }

    private static Map<Integer, List<Integer>> transposed(Map<Integer, List<Integer>> graphData) {
        Map<Integer, List<Integer>> mapInvert = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> pair : graphData.entrySet()) {
            for (int i = 0; i < pair.getValue().size(); i++) {
                int key = pair.getValue().get(i);
                if (!mapInvert.containsKey(key)) {
                    mapInvert.put(pair.getValue().get(i), new LinkedList<>());
                }
                mapInvert.get(key).add(pair.getKey());
            }
        }
        return mapInvert;
    }

    static int t = 0;
    static Set<Integer> researchList = new HashSet<>();

    private static void dfsloop(Map<Integer, List<Integer>> graphData) {
        for (Map.Entry<Integer, List<Integer>> pair : graphData.entrySet()) {
            if (!researchList.contains(pair.getKey())) {
                dfsIter(graphData, pair.getKey());
            }
        }
    }

    static Deque<Integer> stack = new LinkedList<>();

    private static void dfsIter(Map<Integer, List<Integer>> graphData, int s) {
        researchList.add(s);
        stack.push(s);
        while (!stack.isEmpty()) {
            List<Integer> head = graphData.get(stack.peek());
            if (head != null) {
                for (int i = 0; i < head.size(); i++) {
                    if (!researchList.contains(head.get(i))) {
                        researchList.add(head.get(i));
                        stack.push(head.get(i));
                        break;
                    } else if (i == head.size() - 1) {
                        tListIndices.set(t++, stack.peek());
                        stack.pop();
                    }
                }
            } else {
                stack.pop();
            }
        }
    }

    static int count = 0;

    private static void dfsrLoopAfter(Map<Integer, List<Integer>> graphData) {
        researchList.clear();
        for (int i = tListIndices.size() - 1; i >= 0; i--) {
            if (!researchList.contains(tListIndices.get(i))) {
                dfsrIterAfter(graphData, tListIndices.get(i));
                if (count != 0) {
                    result.add(count);
                    count = 0;
                }
            }
        }
    }

    private static void dfsrIterAfter(Map<Integer, List<Integer>> graphData, int s) {
        researchList.add(s);
        stack.push(s);
        while (!stack.isEmpty()) {
            List<Integer> head = graphData.get(stack.peek());
            if (head != null) {
                for (int i = 0; i < head.size(); i++) {
                    if (!researchList.contains(head.get(i))) {
                        researchList.add(head.get(i));
                        stack.push(head.get(i));
                        break;
                    } else if (i == head.size() - 1) {
                        stack.pop();
                        count++;
                    }
                }
            } else {
                stack.pop();
            }
        }
    }

    private static Map<Integer, List<Integer>> readFile() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        Set<Integer> setNumbers = new HashSet<>();

        int vertexFrom;
        try (Scanner scanner = new Scanner(
                new File("E:\\programming\\projects\\practice\\prometheus_algorithms\\src\\test\\resources\\input_08.txt"))) {

            while (scanner.hasNext()) {
                vertexFrom = scanner.nextInt();
                if (!setNumbers.contains(vertexFrom)) {
                    setNumbers.add(vertexFrom);
                    map.put(vertexFrom, new LinkedList<>());
                    map.get(vertexFrom).add(scanner.nextInt());
                } else {
                    int vertexTo = scanner.nextInt();
                    if (vertexFrom != vertexTo) {
                        map.get(vertexFrom).add(vertexTo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
