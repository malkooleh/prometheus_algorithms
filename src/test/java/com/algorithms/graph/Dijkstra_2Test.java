package com.algorithms.graph;

import com.algorithms.TestDataHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

@Slf4j
class Dijkstra_2Test {

    @Test
    void dijkstra() {
        Instant start = Instant.now();

        Dijkstra_2 graph = init();

        graph.shortestPath(1, 6);
        log.debug("Execution time: {}, ms", Duration.between(start, Instant.now()).toMillis());
    }

    private static Dijkstra_2 init() {
        Dijkstra_2 graph = new Dijkstra_2();

        try (Scanner scanner = new Scanner(TestDataHelper.getFile("dijkstra_graph_task_data"))) {
            // the first line contains the number of vertices and edges
            if (scanner.hasNext()) {
                int verticesCount = scanner.nextInt();
                int edgesCount = scanner.nextInt();
                log.debug("The number of vertices and edges is : {} / {}", verticesCount, edgesCount);
                graph.init(verticesCount);

                while (scanner.hasNext()) {
                    int vertexFrom = scanner.nextInt();
                    int vertexTo = scanner.nextInt();
                    int edgeDistance = scanner.nextInt();
                    graph.addEdge(vertexFrom, vertexTo, edgeDistance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }
}