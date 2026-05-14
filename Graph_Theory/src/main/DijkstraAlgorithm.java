package main;

import java.util.*;

public class DijkstraAlgorithm {

    // Edge class
    static class Edge {

        String destination;
        int weight;

        Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Graph using adjacency list
    static Map<String, List<Edge>> graph = new HashMap<>();

    // Function to add edges
    public static void addEdge(String source,
                               String destination,
                               int weight) {

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(
            new Edge(destination, weight)
        );

        graph.get(destination).add(
            new Edge(source, weight)
        ); // Undirected graph
    }

    // Dijkstra Algorithm
    public static void dijkstra(String startNode) {

        // Store shortest distances
        Map<String, Integer> distance = new HashMap<>();

        // Initialize all distances as infinity
        for (String node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }

        // Distance of source node = 0
        distance.put(startNode, 0);

        // Priority Queue
        PriorityQueue<String> pq =
            new PriorityQueue<>(
                Comparator.comparingInt(distance::get)
            );

        pq.add(startNode);

        // Visited set
        Set<String> visited = new HashSet<>();

        System.out.println("Dijkstra Traversal:\n");

        while (!pq.isEmpty()) {

            // Get node with minimum distance
            String currentNode = pq.poll();

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            System.out.println(
                "Visiting Node: " + currentNode +
                " | Current Distance: " +
                distance.get(currentNode)
            );

            // Explore neighbors
            for (Edge edge : graph.get(currentNode)) {

                String neighbor = edge.destination;
                int weight = edge.weight;

                // Calculate new distance
                int newDistance =
                    distance.get(currentNode) + weight;

                // Update if shorter path found
                if (newDistance < distance.get(neighbor)) {

                    distance.put(neighbor, newDistance);

                    pq.add(neighbor);

                    System.out.println(
                        "Updated Distance of " +
                        neighbor +
                        " = " + newDistance
                    );
                }
            }

            System.out.println("----------------------");
        }

        // Final shortest distances
        System.out.println("\nShortest Distances:");

        for (String node : distance.keySet()) {

            System.out.println(
                startNode + " → " +
                node + " = " +
                distance.get(node)
            );
        }
    }

    // Main Method
    public static void main(String[] args) {

        // Create graph
        addEdge("A", "B", 4);
        addEdge("A", "C", 2);
        addEdge("B", "C", 1);
        addEdge("B", "D", 5);
        addEdge("C", "D", 8);
        addEdge("C", "E", 10);
        addEdge("D", "E", 2);
        addEdge("D", "F", 6);
        addEdge("E", "F", 3);

        // Run Dijkstra
        dijkstra("A");
    }
}