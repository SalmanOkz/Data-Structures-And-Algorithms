package main;

import java.util.Arrays;

public class RadixSort {
    
    // Step 1 & 2: Input n elements and find total digits in largest element
    public static void radixSort(int[] arr) {
        // Find the maximum number to know number of digits
        int max = Arrays.stream(arr).max().getAsInt();
        
        // Step 3: Initialize i=1 and repeat until i <= digit
        // Do counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    // Step 4 & 5: Compare digit position and place in corresponding bucket
    public static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];  // Step 4: Initialize buckets 0-9
        
        // Step 5: Compare ith position and place in corresponding bucket
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
        
        // Change count[i] to actual position of digit in output
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Step 6: Copy output array to original array
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        
        System.out.println("Original array: " + Arrays.toString(arr));
        
        radixSort(arr);
        
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}