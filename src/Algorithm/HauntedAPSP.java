package Algorithm;

import Galaxy.Galaxy;
import java.lang.Math;
import java.util.*;

public class HauntedAPSP {
    private Galaxy galaxy;

    //Non-Haunted all pairs shortest path matrix stores initial solutions for APSP while avoiding all haunted galaxies
    private int[][] NHAPSP;

    // Constructor
    public HauntedAPSP(Galaxy galaxy) {
        this.galaxy = galaxy;
        this.NHAPSP = new int[galaxy.getGalaxySize()][galaxy.getGalaxySize()];
    }


    // A modified All Pairs Shortest Path function to find the shortest path while avoiding haunted galaxies
    // NHAPSP(i,j,k) represents the shortest path from node i to node j using intermediate nodes {1, ... , k}
    // provided the nodes are not haunted
    public void nonHauntedShortestPath() {
        // generate initial path lengths for each pair of nodes
        for (int i = 1; i <= NHAPSP.length; i++) {
            for (int j = 1; j <= NHAPSP.length; j++) {
                if (galaxy.isHaunted(i) || galaxy.isHaunted(j)) continue;
                NHAPSP[i-1][j-1] = galaxy.getWeight(i, j);
            }
        }

        // Allow for paths using nodes {1, ..., k} between nodes i and j
        for (int k = 1; k < NHAPSP.length; k++) {
            if (galaxy.isHaunted(k)) continue;

            int[][] previous = NHAPSP;
            NHAPSP = new int[galaxy.getGalaxySize()][galaxy.getGalaxySize()];;

            for (int i = 1; i <= NHAPSP.length; i++) {
                for (int j = 1; j <= NHAPSP.length; i++) {

                    // Avoid using haunted galaxies
                    if (galaxy.isHaunted(i) || galaxy.isHaunted(j)) continue;

                    // NHAPSP(i,j,k) = min { NHAPSP(i,j,k-1), NHAPSP(i,k) + NHAPSP(k,j) }
                    // Use k as an intermediary node if this results in a shorter path
                    if (galaxy.getWeight(i, k) == Integer.MAX_VALUE || galaxy.getWeight(k, j) == Integer.MAX_VALUE)
                        // avoid an int overflow when adding two paths together
                        NHAPSP[i-1][j-1] = previous[i-1][j-1];

                    else NHAPSP[i-1][j-1] =
                            Math.min(galaxy.getWeight(i,j), galaxy.getWeight(i,k) + galaxy.getWeight(k,j));

                }
            }
        }
    }


    // Function to find the shortest path using max k astro haunted galaxies using a dynamic approach
    // SPTKH(i,j,k) is the shortest path from i to j through at most k haunted galaxies
    // SPTKH(i,j,0) is a modified APSP approach that avoids using haunted galaxies
    public int findShortestPath(int maxHaunted) {
        // get base case SPTKH(i,j,0) stored in NHAPSP variable
        nonHauntedShortestPath();

        int[][] SPTKH = NHAPSP;
        for (int k = 1; k <= maxHaunted; k++) {
            int[][] previous = SPTKH;
            SPTKH = new int[galaxy.getGalaxySize()][galaxy.getGalaxySize()];

            for (int i = 1; i <= galaxy.getGalaxySize(); i++) {
                for (int j = 1; j <= galaxy.getGalaxySize(); j++) {

                    // SPTKH(i,j,k) = min{SPTKH(i,z,k-1) + SPTKH(z,j,0)} for all z in {1, ... , n}
                    // Pick the shortest path using every possible node as an intermediate and pick the smallest
                    List<Integer> paths = new ArrayList<>();
                    for (int z = 1; z <= galaxy.getGalaxySize(); z++) {
                        if (previous[i-1][z-1] == Integer.MAX_VALUE || NHAPSP[z-1][j-1] == Integer.MAX_VALUE)
                            paths.add(Integer.MAX_VALUE); // avoid an overflow when adding a max value int
                        else
                            paths.add(previous[i-1][z-1] + NHAPSP[z-1][j-1]);
                    }
                    SPTKH[i-1][j-1] = Collections.min(paths);
                }
            }
        }

        // Return SPTKH(1, n, k) which stores the shortest path from 1 to n through k haunted galaxies
        return SPTKH[0][galaxy.getGalaxySize() - 1];

    }

}
