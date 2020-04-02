package algorithm;

import galaxy.Galaxy;
import java.lang.Math;
import java.util.*;

public class HauntedAPSP {
    private Galaxy galaxy;

    //Non-Haunted all pairs shortest path matrix stores initial solutions for APSP while avoiding all haunted galaxies
    private int[][] baseCase;

    // Constructor
    public HauntedAPSP(Galaxy galaxy) {
        this.galaxy = galaxy;
        this.baseCase = initializeArray();
    }

    public void clear() {
        baseCase = initializeArray();
    }

    // Utility function to create a new n+1 by n+1 array filled with Max integer values
    private int[][] initializeArray(){
        int[][] array = new int[galaxy.getGalaxySize() + 1][galaxy.getGalaxySize() + 1];
        for (int i = 0; i <= galaxy.getGalaxySize(); i++) {
            Arrays.fill(array[i], Integer.MAX_VALUE);
        }

        return array;
    }


    // A modified All Pairs Shortest Path function to find the shortest path while avoiding haunted galaxies
    // NHAPSP(i,j,k) represents the shortest path from node i to node j using intermediate nodes {1, ... , k}
    // provided the nodes are not haunted
    public int nonHauntedShortestPath() {
        // generate initial path lengths for each pair of nodes if nodes are not haunted
        for (int i = 1; i <= galaxy.getGalaxySize(); i++) {
            for (int j = 1; j <= galaxy.getGalaxySize(); j++) {
                if (i == j){
                    baseCase[i][j] = 0;    
                }
                else if (galaxy.isHaunted(i) || galaxy.isHaunted(j)) {
                    baseCase[i][j] = Integer.MAX_VALUE;
                }
                else {
                    baseCase[i][j] = galaxy.getWeight(i, j);
                }
            }
        }

        // Allow for paths using nodes {1, ..., k} between nodes i and j
        for (int k = 1; k < galaxy.getGalaxySize(); k++) {
            // avoid haunted galaxies
            if (galaxy.isHaunted(k)) {
                continue;
            }

            // use two arrays to save space as we only consider values from the {1, ..., k-1} case
            int[][] previous = baseCase;
            baseCase = initializeArray();

            for (int i = 1; i <= galaxy.getGalaxySize(); i++) {
                for (int j = 1; j <= galaxy.getGalaxySize(); j++) {

                    // Avoid using haunted galaxies
                    if (galaxy.isHaunted(i) || galaxy.isHaunted(j)) { 
                        continue; 
                    }

                    // NHAPSP(i,j,k) = min { NHAPSP(i,j,k-1), NHAPSP(i,k) + NHAPSP(k,j) }
                    // Use k as an intermediary node if this results in a shorter path
                    if (galaxy.getWeight(i, k) == Integer.MAX_VALUE || galaxy.getWeight(k, j) == Integer.MAX_VALUE) {
                        // avoid an int overflow when adding a max valued integer
                        baseCase[i][j] = previous[i][j];
                    }
                    else {
                        // we either use the intermediate node k or we take the path directly from i to j
                        baseCase[i][j] = Math.min(galaxy.getWeight(i,j), galaxy.getWeight(i,k) + galaxy.getWeight(k,j));
                    }

                }
            }
        }

        return baseCase[1][galaxy.getGalaxySize()]; // return NHAPSP(1,n)
    }


    // Function to find the shortest path using max k astro haunted galaxies using a dynamic approach
    // SPTKH(i,j,k) is the shortest path from i to j through at most k haunted galaxies
    // SPTKH(i,j,0) is a modified APSP approach that avoids using haunted galaxies
    public int findShortestPath(int maxHaunted) {
        // get base case SPTKH(i,j,0) stored in NHAPSP variable
        nonHauntedShortestPath();

        // add c(i,j) for haunted galaxies to base case if they are smaller than the current shortest path from i to j
        for (int i = 1; i <= galaxy.getGalaxySize(); i++){
            for (int j = 1; j <= galaxy.getGalaxySize(); j++) {
                baseCase[i][j] = Math.min( galaxy.getWeight(i, j), baseCase[i][j]);
            }
        }

        // Initialize array to store current shortest paths through k haunted galaxies
        int[][] shortestPath = initializeArray();
        for (int i = 1; i <= galaxy.getGalaxySize(); i++) {
            for (int j = 1; j <= galaxy.getGalaxySize(); j++) {
                shortestPath[i][j] = baseCase[i][j];
            }
        }

        // Go through adding one haunted galaxy at a time and storing every possible shortest path between i and j
        for (int k = 1; k <= maxHaunted; k++) {
            // Save space by saving k-1 answer and making a new array for k-th answers
            int[][] previousPath = shortestPath;
            shortestPath = initializeArray();

            for (int i = 1; i <= galaxy.getGalaxySize(); i++) {
                for (int j = 1; j <= galaxy.getGalaxySize(); j++) {

                    // SPTKH(i,j,k) = min{SPTKH(i,z,k-1) + SPTKH(z,j,0)} for all z in {1, ... , n}
                    // Pick the shortest path using every possible node as an intermediate between i and j

                    int min = Integer.MAX_VALUE;
                    for (int z = 1; z <= galaxy.getGalaxySize(); z++) {
                        int sum = previousPath[i][z] + baseCase[z][j]; // find intermediate path

                        // store shorter values if they have not overflowed into negative values
                        if (sum < min && sum >= 0 ) {
                            min = previousPath[i][z] + baseCase[z][j];
                        }
                    }
                    shortestPath[i][j] = min;
                }
            }
        }

        // Return SPTKH(1, n, k) which stores the shortest path from 1 to n through k haunted galaxies
        return shortestPath[1][galaxy.getGalaxySize()];
    }

}
