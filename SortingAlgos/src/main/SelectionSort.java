package main;

import java.util.Arrays;

public class SelectionSort {
    
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        // Step 1: Set MIN to location 0
        // Step 5: Repeat until list is sorted
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;  // Step 1: Set MIN to location i
            
            // Step 2: Search the minimum element in the list
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Step 3: Swap with value at location MIN
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            
            // Step 4: Increment MIN to point to next element (loop does this)
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        selectionSort(arr);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}