package main;


import java.util.Arrays;

public class BubbleSort {
    
    // Basic Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements
                if (arr[j] > arr[j + 1]) {
                    // Swap if they're in wrong order
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        bubbleSort(arr);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}