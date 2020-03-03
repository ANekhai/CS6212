package main.java.Sort;

import java.util.Random;

public class Sort {
    Random stream = new Random(); // pseudorandom number generator for probabilistic quickselect

    //SORT ALGORITHMS

    // Basic implementation of insertion sort which sorts an array starting at lower before upper
    // Input: integer array of values to sort
    // Input: integer lower bound to start from
    // Input: integer upper bound of indices to sort values strictly less than this index
    public void insertionSort(int[] values, int lower, int upper) {

        for (int i = lower; i < upper; i++){
            int key = values[i];
            int j = i - 1;

            while (j >= lower && values[j] > key) {
                values[j + 1] = values[j];
                j--;
            }

            values[j + 1] = key;

        }
    }


    // Basic quicksort function that recursively sorts an array by finding the median and arranging elements on
    // either side of it. The median is found with a deterministic QuickSelect using a Median of Medians algorithm
    // Input: integer array of values to sort
    // Input: integer lower and upper bounds to sort within

    public void quickSort(int[] values, int lower, int upper) {
        if (lower < upper) {
            // Find the median value, which in turn partitions the array around the median value
            int median_index = lower + (upper - lower) / 2;
            quickSelectMOM(values, lower, upper, median_index);

            // Recurse around each new half of the array
            quickSort(values, lower, median_index);
            quickSort(values, median_index + 1, upper);
        }


    }

    //UTILITY FUNCTIONS

    // A function that chooses the k-th smallest value in an array (starting from 0th smallest up)
    // Input: an array of integer values
    // Input: an integer lower bound and upper bound
    // Input: integer k-th largest value to search for
    // Output: the integer value of the array in the k-th largest place
    public int probabilisticQuickSelect(int[] values, int lower, int upper, int k) {
        // randomly select a pivot
        int pivot = values[0];
        int pivot_index = upper;

        // check if pivot is in middle two quartiles
        while ( pivot_index < (upper-lower)/4 + lower || pivot_index > 3*(upper-lower)/4 + lower ) {
            pivot_index = lower + stream.nextInt(upper - lower);
            pivot = values[pivot_index];
            pivot_index = partition(values, lower, upper, pivot);
        }

        // recurse to find the median in the larger of the two partitions
        if (k > pivot_index)
            return probabilisticQuickSelect(values, pivot_index + 1, upper, k);
        else if (k < pivot_index)
            return probabilisticQuickSelect(values, lower, pivot_index, k);
        else
            return pivot;


    }


    // Recursive deterministic Quick Select Using Median of Medians method to find the k-th smallest element
    // Input: integer array of values
    // Input: integer lower bound to search within
    // Input: integer upper bound to search within
    // Input: integer k-th smallest element to look for (indexed from zero)
    // Output: The value of the k-th smallest value in values array
    public int quickSelectMOM(int[] values, int lower, int upper, int k) {

        // first find medians of all 5 element partitions between lower and upper

        int[] medians = new int[(upper-lower)/5];

        for (int i = 0; i < medians.length; i++) {
            // Set partition bounds for a 5 element partition
            int partition_lower = i*5 + lower;
            int partition_upper = partition_lower + 5;
            if (partition_upper > upper) partition_upper = upper;

            // sort the partition with insertion sort
            insertionSort(values, partition_lower, partition_upper);

            // store the median of this partition
            medians[i] = values[partition_lower + (partition_upper - partition_lower)/2];

        }

        // Find a median by recursing on our list of medians (or if there are less than 5 numbers in values,
        // directly calculate the median by sorting the array
        int pivot;
        if (medians.length == 0) {
            // get median directly
            insertionSort(values, lower, upper);
            pivot = values[lower + (upper-lower)/2];
        }
        else pivot = quickSelectMOM(medians, 0, medians.length, medians.length/2);

        // Partition values using pivot
        int pivot_index = partition(values, lower, upper, pivot);

        // recurse on the array that contains the k-th largest value
        if (k == pivot_index)
            return values[pivot_index];
        else if ( k < pivot_index)
            return quickSelectMOM(values, lower, pivot_index, k);
        else
            return quickSelectMOM(values, pivot_index + 1, upper, k);

    }

    // Quickly rearranges an array in place around a pivot element, where smaller elements are on the left of the pivot
    // and larger elements are on the right
    // Input: integer array of values to be arranged
    // Input: integer lower and upper bounds to rearrange within
    // Input: integer pivot element in values to arrange all other elements around
    // Output: integer index of pivot element after rearrangement
    public int partition(int[] values, int lower, int upper, int pivot) {
        //find the current index of the pivot in values
        int pivot_index = lower;
        for (int i = lower; i < upper; i++) {
            if (values[i] == pivot) pivot_index = i;
        }

        swap(values, pivot_index, upper-1); //place pivot last in array

        //sort values in whole array based on whether they are larger than the pivot or smaller than the pivot
        int i = lower-1;
        for (int j = lower; j < upper-1; j++) {
            if (values[j] <= pivot){
                i++;
                swap(values, i, j);
            }
        }
        // Move the pivot to the end of the lower values in the array and return it's index
        swap(values, i+1, upper-1);
        return i+1;
    }

    // Swap the the values at indices i and j in the values array
    public void swap(int[] values, int i, int j) {
        if(i != j){
            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }

}
