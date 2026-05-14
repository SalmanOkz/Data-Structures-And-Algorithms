package main;

import java.util.*;

public class BFSGraph {

    // Graph using adjacency list
    static Map<String, List<String>> graph = new HashMap<>();

    // Function to add edges
    public static void addEdge(String source, String destination) {

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(destination);
        graph.get(destination).add(source); // Undirected graph
    }

    // BFS Algorithm
    public static void bfs(String startNode) {

        // Step 1: Initialize queue
        Queue<String> queue = new LinkedList<>();

        // To keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Step 2:
        // Visit starting node S
        visited.add(startNode);
        queue.add(startNode);

        System.out.println("BFS Traversal:\n");

        while (!queue.isEmpty()) {

            // Step 6:
            // Dequeue front node
            String currentNode = queue.poll();

            System.out.println("Visited Node: " + currentNode);

            // Get adjacent nodes
            List<String> neighbors = graph.get(currentNode);

            // Sort alphabetically
            Collections.sort(neighbors);

            // Step 3,4,5:
            // Explore adjacent unvisited nodes
            for (String neighbor : neighbors) {

                if (!visited.contains(neighbor)) {

                    // Mark visited
                    visited.add(neighbor);

                    // Enqueue node
                    queue.add(neighbor);

                    System.out.println(
                        "Enqueued Node: " + neighbor
                    );
                }
            }

            System.out.println("Current Queue: " + queue);
            System.out.println("----------------------");
        }
    }

    // Main Method
    public static void main(String[] args) {

        // Graph from your example
        addEdge("S", "A");
        addEdge("S", "B");
        addEdge("S", "C");
        addEdge("A", "D");

        // Run BFS
        bfs("S");
    }
}