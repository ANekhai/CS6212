package main.java.Sort;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    Sort test = new Sort();


    @org.junit.jupiter.api.Test
    void insertionSortBackwardsArray() {
        int[] array = {5, 4, 3, 2, 1};
        test.insertionSort(array, 0, array.length);

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
    }

    @org.junit.jupiter.api.Test
    void insertionSortRandomArray() {
        int[] array = {3, 5, 2, 1, 4};
        test.insertionSort(array, 0, array.length);

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
    }

    @org.junit.jupiter.api.Test
    void insertionSortMiddleArray() {
        int[] array = {7, 6, 5, 4, 3, 2, 1};
        test.insertionSort(array, 1, array.length-1);

        assertEquals(1, array[6]);
        assertEquals(7, array[0]);
        assertEquals(2, array[1]);
        assertEquals(4, array[3]);
    }

    @org.junit.jupiter.api.Test
    void swapDifferentIndices() {
        int[] array = {1, 2, 3};
        test.swap(array, 0, 2);

        assertEquals(3, array[0]);
        assertEquals(1, array[2]);
    }

    @org.junit.jupiter.api.Test
    void swapSameIndices() {
        int[] array = {1, 2, 3};
        test.swap(array, 0, 0);

        assertEquals(1, array[0]);
    }

    @org.junit.jupiter.api.Test
    void partitionFullArray() {
        int[] array = {7,6,5,4,3,2,1};
        assertEquals(2,  test.partition(array, 0, array.length, 3) );

        assertEquals(3, array[2]);

    }

    @org.junit.jupiter.api.Test
    void partitionPartialArray() {
        int[] array = {7,6,5,4,3,2,1};
        assertEquals(4, test.partition(array, 1, array.length - 1, 5) );

        assertEquals(7, array[0]);
        assertEquals(1, array[6]);
        assertEquals(5, array[4]);

    }

    @org.junit.jupiter.api.Test
    void testQuickSelectDeterministic() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        assertEquals(1, test.quickSelectMOM(array, 0, array.length, 0));
        assertEquals(2, test.quickSelectMOM(array, 0, array.length, 1) );
        assertEquals(3, test.quickSelectMOM(array, 0, array.length, 2) );
        assertEquals(4, test.quickSelectMOM(array, 0, array.length, 3) );
        assertEquals(5, test.quickSelectMOM(array, 0, array.length, 4) );
        assertEquals(6, test.quickSelectMOM(array, 0, array.length, 5) );
        assertEquals(7, test.quickSelectMOM(array, 0, array.length, 6) );
        assertEquals(8, test.quickSelectMOM(array, 0, array.length, 7) );
        assertEquals(9, test.quickSelectMOM(array, 0, array.length, 8) );
        assertEquals(10, test.quickSelectMOM(array, 0, array.length, 9) );
    }

    @org.junit.jupiter.api.Test
    void testQSelectDeterministicRepeatValues() {
        int [] array = {10, 9, 5, 5, 4, 3, 2, 1};

        assertEquals(5, test.quickSelectMOM(array, 0, array.length, 5));
    }

    @org.junit.jupiter.api.Test
    void testQSelectDeterministicLarge() {
        int[] array = new int[500];
        for (int i = 0; i < 500; i++)
            array[i] = 500 - i;

        assertEquals(52, test.quickSelectMOM(array, 0, array.length, 51) );

    }

    @org.junit.jupiter.api.Test
    void testQSelectProbabilistic() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        assertEquals(1, test.probabilisticQuickSelect(array, 0, array.length, 0));
        assertEquals(2, test.probabilisticQuickSelect(array, 0, array.length, 1) );
        assertEquals(3, test.probabilisticQuickSelect(array, 0, array.length, 2) );
        assertEquals(4, test.probabilisticQuickSelect(array, 0, array.length, 3) );
        assertEquals(5, test.probabilisticQuickSelect(array, 0, array.length, 4) );
        assertEquals(6, test.probabilisticQuickSelect(array, 0, array.length, 5) );
        assertEquals(7, test.probabilisticQuickSelect(array, 0, array.length, 6) );
        assertEquals(8, test.probabilisticQuickSelect(array, 0, array.length, 7) );
        assertEquals(9, test.probabilisticQuickSelect(array, 0, array.length, 8) );
        assertEquals(10, test.probabilisticQuickSelect(array, 0, array.length, 9) );
    }

    @org.junit.jupiter.api.Test
    void testQuickSort() {
        int[] array = {9, 8, 5, 6, 7, 12, 4, 1, 3, 17};
        test.quickSort(array, 0, array.length);

        assertEquals(1, array[0]);
        assertEquals(17, array[9]);
        assertEquals(7, array[5]);

    }

}