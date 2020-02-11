import java.lang.Math;

public class main {
    private static int n = 0; //Stores the input size
    private static int a[], b[]; //arrays in pseudocode whose values are multiplied together

    //Given pseudocode implemented into a function
    //Returns time spent running the nested while loops in nanoseconds
    private static long testCode() {
        int j = 2;
        double sum = 0;

        //take the initial time
        final long startTime = System.nanoTime();

        while (j < n) {
            int k = j;

            while(k < n){
                sum += a[k] * b[k];
                k += Math.floor(Math.pow(n, 1.0/3)*Math.log(n));
            }

            j *= 2;
        }

        //take the final time
        final long endTime = System.nanoTime();

        return endTime - startTime;
    }

    public static void main(String[] argv) {

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

        //Initialize arrays mentioned in pseudocode
        a = new int[n];
        b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
            b[i] = n - i;
        }

        final long runTime = testCode();

        System.out.println("Program running on " + n + " values executed in " + runTime + " ns");

    }
}
