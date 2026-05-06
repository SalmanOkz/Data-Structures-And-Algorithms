package main;

import java.util.Arrays;

public class MergeSort {
    
    // Step 1: If it is only one element, it is already sorted, return
    // Step 2: Divide the list recursively into two halves
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            
            // Divide left half
            mergeSort(arr, left, mid);
            // Divide right half
            mergeSort(arr, mid + 1, right);
            
            // Step 3: Merge the smaller lists into new list in sorted order
            merge(arr, left, mid, right);
        }
    }
    
    // Merge two sorted subarrays
    public static void merge(int[] arr, int left, int mid, int right) {
        // Sizes of two subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        // Create temporary arrays
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        // Copy data to temp arrays
        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];
        
        // Merge the temp arrays
        int i = 0, j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        mergeSort(arr, 0, arr.length - 1);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}