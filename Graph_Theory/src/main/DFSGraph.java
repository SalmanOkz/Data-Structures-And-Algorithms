package main;

import java.util.*;

public class DFSGraph {

    // Graph represented using adjacency list
    static Map<String, List<String>> graph = new HashMap<>();

    // Function to add edges
    public static void addEdge(String source, String destination) {

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(destination);
        graph.get(destination).add(source); // Undirected graph
    }

    // DFS Algorithm using Stack
    public static void dfs(String startNode) {

        // Step 1: Initialize the stack
        Stack<String> stack = new Stack<>();

        // To keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Step 2:
        // Visit starting node S
        visited.add(startNode);
        stack.push(startNode);

        System.out.println("DFS Traversal:\n");

        while (!stack.isEmpty()) {

            // Get top node from stack
            String currentNode = stack.peek();

            System.out.println("Current Node: " + currentNode);

            // Get adjacent nodes
            List<String> neighbors = graph.get(currentNode);

            // Sort alphabetically
            Collections.sort(neighbors);

            boolean foundUnvisited = false;

            // Step 3,4,5:
            // Find unvisited adjacent node
            for (String neighbor : neighbors) {

                if (!visited.contains(neighbor)) {

                    // Mark as visited
                    visited.add(neighbor);

                    // Push onto stack
                    stack.push(neighbor);

                    System.out.println(
                        "Visited and Pushed: " + neighbor
                    );

                    foundUnvisited = true;
                    break;
                }
            }

            // Step 6:
            // If no unvisited adjacent node exists
            // then backtrack
            if (!foundUnvisited) {

                String removed = stack.pop();

                System.out.println(
                    "Backtracking from: " + removed
                );
            }

            System.out.println("Current Stack: " + stack);
            System.out.println("-------------------------");
        }
    }

    // Main Method
    public static void main(String[] args) {

        // Creating graph
        addEdge("S", "A");
        addEdge("S", "B");
        addEdge("S", "C");
        addEdge("A", "D");
        addEdge("B", "D");
        addEdge("C", "D");

        // Run DFS starting from S
        dfs("S");
    }
}