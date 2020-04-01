package App;

import Algorithm.HauntedAPSP;
import Galaxy.Galaxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class App {
    static HauntedAPSP algo;

    private static boolean[] generateHauntedArray(int size) {
        boolean[] haunted = new boolean[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int randomInt = rand.nextInt(2);
            if (randomInt == 1) haunted[i] = true;
            else haunted[i] = false;
        }
        haunted[0] = false; haunted[size - 1] = false; // first and last nodes in galaxy are not haunted

        return haunted;
    }

    private static long timeHauntedAPSP(int k) {
        //take the time before finding the shortest path
        final long startTime = System.nanoTime();

        // find the shortest path through k-haunted galaxies
        algo.findShortestPath(k);

        //take a time after algorithm runs
        final long endTime = System.nanoTime();

        return endTime - startTime; //total runtime in nanoseconds
    }


    public static void main(String[] argv) {
        int n = 0; // number of nodes in graph
        int k = 0; // max number of haunted galaxies to pass through

        List<List<Integer>> edges = new ArrayList<>();


        //Read user input from command line
        // must find a value n for total nodes in graph and open a file to read edges for the graph
        // Weights for all edges are set to 1
        try {
            if (argv.length != 3) {
                throw new java.lang.IllegalArgumentException("Must input a number n and a file name with graph edges");
            }

            n = Integer.valueOf(argv[0]); // get the size of the graph
            k = Integer.valueOf(argv[1]); // get the max allowable haunted galaxies

            // read file with graph edges
            File file = new File(argv[2]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                // edges in graph are tab delimited
                String[] nodes = line.split("\t", 0);

                List<Integer> edge = new ArrayList<>();
                edge.add(Integer.valueOf(nodes[0]));
                edge.add(Integer.valueOf(nodes[1]));
                edge.add(1); // arbitrary weight
                edges.add(edge);
            }

        }
        catch (java.lang.Exception e) { //check for any errors in user input
            e.printStackTrace();
            System.exit(1);
        }

        // initialize an array of which nodes are haunted (assume node 1 and n are not haunted)
        boolean[] haunted = generateHauntedArray(n);

        //Initialize the galaxy structure
        Galaxy galaxy = new Galaxy(edges, haunted);
        algo = new HauntedAPSP(galaxy);

        // Run algorithm five times and calculate the average runtime
        long sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += timeHauntedAPSP(k);
        }

        long runTime = sum / 5;

        System.out.println("Program running on " + n + " values and " + k + " haunted planets executed in an average of " + runTime + " ns");

    }

}

