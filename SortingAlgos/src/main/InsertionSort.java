package main;

import java.util.Arrays;

public class InsertionSort {
    
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        
        // Step 1: If it is the first element, it is already sorted
        // (automatically true, start from index 1)
        
        // Step 6: Repeat until list is sorted
        for (int i = 1; i < n; i++) {
            // Step 2: Pick next element
            int key = arr[i];
            int j = i - 1;
            
            // Step 3: Compare with all elements in the sorted sub-list
            // Step 4: Shift all elements greater than key to the right
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Step 5: Insert the value
            arr[j + 1] = key;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        insertionSort(arr);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}