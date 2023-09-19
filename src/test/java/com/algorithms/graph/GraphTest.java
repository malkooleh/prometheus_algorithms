package com.algorithms.graph;

import com.algorithms.TestDataHelper;
import com.algorithms.graph.Graph;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

class GraphTest {

    // determine the components of strong connectivity in a directed graph
    @Test
    void strongConnectedComponent_task() throws IOException {
        Instant start1 = Instant.now();
        File file = TestDataHelper.getFile("graph_task_data");
        // each row has 2 numbers (vertices): the first is the beginning of the edge that goes to the second
        Stream<String> lines = Files.lines(file.toPath());
        List<String> data = lines.toList();
        lines.close();
        Duration duration1 = Duration.between(start1, Instant.now());
        System.out.println("Read file duration ms = " + duration1.toMillis());

        Instant start2 = Instant.now();
        Graph graph = Graph.buildGraph(data);
        Duration duration2 = Duration.between(start2, Instant.now());
        System.out.println("Build graph duration ms = " + duration2.toMillis());

        Instant start3 = Instant.now();
        List<Integer> strongConnectedComponents = graph.strongConnectedComponent();
        Duration duration3 = Duration.between(start3, Instant.now());
        System.out.println("Find strong connected components duration ms = " + duration3.toMillis());

        System.out.println(strongConnectedComponents);
        Assertions.assertThat(strongConnectedComponents.get(0)).isEqualTo(7578);
        Assertions.assertThat(strongConnectedComponents.get(1)).isEqualTo(1996);
        Assertions.assertThat(strongConnectedComponents.get(2)).isEqualTo(1);
    }
}