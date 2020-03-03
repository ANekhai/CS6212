package main.java.App;

import main.java.Sort.Sort;

public class Application {
    static Sort sort = new Sort();

    private static long timeQuickSelect(int[] array) {
        //take the time before running quickselect
        final long startTime = System.nanoTime();

        sort.quickSelectMOM(array, 0, array.length, array.length/2);

        //take the final time
        final long endTime = System.nanoTime();

        return endTime - startTime; //total runtime in nanoseconds
    }


    public static void main(String[] argv) {
        int n = 0; // size of array to test quickselect on

        //Read user input from command line
        try {
            if (argv.length != 1) {
                throw new java.lang.IllegalArgumentException("Must input a number n");
            }

            n = Integer.valueOf(argv[0]);

        }
        catch (java.lang.Exception e) { //check for any errors in user input
            e.printStackTrace();
            System.exit(1);
        }

        //Initialize array to be sorted
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++)
            array[i] = n - i;

        // test five run times and calculate the average
        long sum = 0;
        for (int i = 0; i < 5; i++)
            sum += timeQuickSelect(array);

        final long runTime = sum / 5;

        System.out.println("Program running on " + n + " values executed in an average of " + runTime + " ns");

    }

}
