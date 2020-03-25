package Galaxy;

import java.util.*;


public class Galaxy {
    // Data Structure that stores weights of edges keyed by the edge vertices, assumes graph is directed
    // Edges stored are from {1, ..., n}
    private Map<List<Integer>, Integer> galaxyGraph = new HashMap<>();


    private boolean[] astroHaunted; // Array of which vertices are haunted from {1, ..., n} indexed by {0, ..., n-1}

    private int galaxySize = 0;


    // Constructors and Destructors

    // Expects edges to be an array of 3 element arrays where the first and second elements denote vertices incident
    // to an edge and the third element is an edge weight
    public Galaxy(List< List<Integer> > edges, boolean[] haunted) {
        this.astroHaunted = haunted;
        this.galaxySize = astroHaunted.length;

        for (int i = 0; i < edges.size(); i++ ) {
            addEdge(edges.get(i).get(0), edges.get(i).get(1), edges.get(i).get(2));
        }
    }

    public Galaxy() {
        this.astroHaunted = new boolean[0];
    }

    public void clear() {
        this.galaxyGraph = new HashMap<>();
        this.astroHaunted = new boolean[0];
        this.galaxySize = 0;
    }

    // Getters and Setters

    public boolean isHaunted(int vertex) {return astroHaunted[vertex - 1];}

    private List<Integer> getKey(int source, int destination) {
        List<Integer> key = new ArrayList<Integer>();
        key.add(source);
        key.add(destination);
        return key;
    }

    public boolean hasEdge(int source, int destination) {
        List<Integer> key = getKey(source, destination);
        return galaxyGraph.containsKey(key);
    }

    public int getWeight(int source, int destination) {
        List<Integer> key = getKey(source, destination);
        if (source == destination) return 0;
        return galaxyGraph.getOrDefault(key, Integer.MAX_VALUE);
    }

    public boolean addEdge(int source, int destination, int weight) {
        List<Integer> key = getKey(source, destination);
        //Only add new edges or edges that connect nodes already in graph (using the size of astrohaunted
        if (galaxyGraph.containsKey(key) || (source > galaxySize || destination > galaxySize)) {
            return false;
        }
        else {
            galaxyGraph.put(key, weight);
            return true;
        }
    }

    public int getGalaxySize() {return galaxySize;}


}
