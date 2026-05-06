package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// Max Heap Implementation
class MaxHeap {
    private ArrayList<Integer> heap;
    
    public MaxHeap() {
        heap = new ArrayList<>();
    }
    
    private int parent(int i) {
        return (i - 1) / 2;
    }
    
    private int leftChild(int i) {
        return 2 * i + 1;
    }
    
    private int rightChild(int i) {
        return 2 * i + 2;
    }
    
    // Insert a new element into the heap
    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }
    
    // Move the element at index i up to maintain heap property
    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) > heap.get(parent(i))) {
            // Swap with parent if current > parent
            Collections.swap(heap, i, parent(i));
            i = parent(i);
        }
    }
    
    // Delete and return the maximum element (root)
    public Integer deleteMax() {
        if (heap.isEmpty()) {
            return null;
        }
        
        if (heap.size() == 1) {
            return heap.remove(0);
        }
        
        // Replace root with last element
        int root = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown(0);
        return root;
    }
    
    // Move the element at index i down to maintain heap property
    private void heapifyDown(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        // Find the largest among root, left child, right child
        if (left < heap.size() && heap.get(left) > heap.get(largest)) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right) > heap.get(largest)) {
            largest = right;
        }
        
        // If largest is not root, swap and continue
        if (largest != i) {
            Collections.swap(heap, i, largest);
            heapifyDown(largest);
        }
    }
    
    // Peek at the maximum element
    public Integer getMax() {
        return heap.isEmpty() ? null : heap.get(0);
    }
    
    public int size() {
        return heap.size();
    }
    
    public void printHeap() {
        System.out.println(heap);
    }
}

// Min Heap Implementation for ascending sort
class MinHeap {
    private ArrayList<Integer> heap;
    
    public MinHeap() {
        heap = new ArrayList<>();
    }
    
    private int parent(int i) {
        return (i - 1) / 2;
    }
    
    private int leftChild(int i) {
        return 2 * i + 1;
    }
    
    private int rightChild(int i) {
        return 2 * i + 2;
    }
    
    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }
    
    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) < heap.get(parent(i))) {
            Collections.swap(heap, i, parent(i));
            i = parent(i);
        }
    }
    
    public Integer deleteMin() {
        if (heap.isEmpty()) {
            return null;
        }
        if (heap.size() == 1) {
            return heap.remove(0);
        }
        
        int root = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapifyDown(0);
        return root;
    }
    
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
            smallest = left;
        }
        if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
            smallest = right;
        }
        
        if (smallest != i) {
            Collections.swap(heap, i, smallest);
            heapifyDown(smallest);
        }
    }
    
    public int size() {
        return heap.size();
    }
}

// Main class with sorting methods and demonstration
public class BinaryHeapDemo {
    
    // Sort array in ascending order using Min Heap
    public static int[] heapSortAscending(int[] arr) {
        MinHeap heap = new MinHeap();
        for (int value : arr) {
            heap.insert(value);
        }
        
        int[] sortedArr = new int[arr.length];
        int index = 0;
        while (heap.size() > 0) {
            sortedArr[index++] = heap.deleteMin();
        }
        return sortedArr;
    }
    
    // Sort array in descending order using Max Heap
    public static int[] heapSortDescending(int[] arr) {
        MaxHeap heap = new MaxHeap();
        for (int value : arr) {
            heap.insert(value);
        }
        
        int[] sortedArr = new int[arr.length];
        int index = 0;
        while (heap.size() > 0) {
            sortedArr[index++] = heap.deleteMax();
        }
        return sortedArr;
    }
    
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("MAX HEAP DEMONSTRATION");
        System.out.println("=".repeat(50));
        
        // Create a Max Heap
        MaxHeap maxHeap = new MaxHeap();
        
        // Insert elements
        System.out.println("\n1. Inserting elements: 10, 20, 15, 30, 40, 50, 25");
        int[] elements = {10, 20, 15, 30, 40, 50, 25};
        for (int elem : elements) {
            maxHeap.insert(elem);
            System.out.print("   Inserted " + elem + ": Heap = ");
            maxHeap.printHeap();
        }
        
        System.out.println("\n2. Maximum element (peek): " + maxHeap.getMax());
        System.out.print("   Current heap: ");
        maxHeap.printHeap();
        
        System.out.println("\n3. Deleting max elements one by one:");
        while (maxHeap.size() > 0) {
            int deleted = maxHeap.deleteMax();
            System.out.print("   Deleted " + deleted + ": Heap = ");
            maxHeap.printHeap();
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HEAP SORT DEMONSTRATION");
        System.out.println("=".repeat(50));
        
        // Heap Sort examples
        int[] unsorted = {64, 25, 12, 22, 11, 90, 85, 45};
        System.out.println("\nUnsorted array: " + Arrays.toString(unsorted));
        
        int[] ascending = heapSortAscending(unsorted.clone());
        System.out.println("Ascending order (using Min Heap): " + Arrays.toString(ascending));
        
        int[] descending = heapSortDescending(unsorted.clone());
        System.out.println("Descending order (using Max Heap): " + Arrays.toString(descending));
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PRIORITY QUEUE EXAMPLE");
        System.out.println("=".repeat(50));
        
        // Using Max Heap as a Priority Queue
        MaxHeap pq = new MaxHeap();
        String[] taskNames = {"Low priority", "High priority", "Medium priority"};
        int[] priorities = {3, 1, 2};
        
        System.out.println("\nInserting tasks with priorities:");
        for (int i = 0; i < priorities.length; i++) {
            pq.insert(priorities[i]);
            System.out.println("   Added: " + taskNames[i] + " (priority " + priorities[i] + ")");
            System.out.print("   Heap: ");
            pq.printHeap();
        }
        
        System.out.println("\nProcessing tasks by highest priority:");
        while (pq.size() > 0) {
            int priority = pq.deleteMax();
            System.out.println("   Processing priority " + priority);
        }
    }
}