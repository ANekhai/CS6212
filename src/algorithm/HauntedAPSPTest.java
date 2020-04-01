package algorithm;

import galaxy.Galaxy;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HauntedAPSPTest {
    HauntedAPSP mock;

    Galaxy galaxy;
    List<List<Integer>> edges;
    boolean[] haunted;

    private ArrayList<Integer> makeEdge(int source, int destination, int weight) {
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(source);
        edge.add(destination);
        edge.add(weight);
        return edge;
    }

    @BeforeEach
    void setUp() {
        edges = new ArrayList<>();
        edges.add(makeEdge(1, 2, 1));
        edges.add(makeEdge(2, 3, 1));
        edges.add(makeEdge(3,4,1));
        edges.add(makeEdge(4,5,1));
        edges.add(makeEdge(1,5,15));
        edges.add(makeEdge(4,2,1));

        this.haunted =  new boolean[]{false, true, true, true, false};
        galaxy = new Galaxy(edges, haunted);

        mock = new HauntedAPSP(galaxy);

    }

    @AfterEach
    void tearDown() {
        mock.clear();
    }

    @Test
    void testNonHauntedAPSP() {
        assertEquals(15, mock.nonHauntedShortestPath());
    }

    @Test
    void testNonHauntedFindBetterPath (){
        edges.add(makeEdge(1, 4, 3));
        haunted[3] = false;
        galaxy = new Galaxy(edges, haunted);
        mock = new HauntedAPSP(galaxy);
        assertEquals(4, mock.nonHauntedShortestPath());
    }

    @Test
    void testHauntedAPSPNoHauntedAllowed() {
        assertEquals(15, mock.findShortestPath(0));
    }

    @Test
    void testHauntedAPSPOneHauntedAllowed() {
        assertEquals(15, mock.findShortestPath(1));
    }

    @Test
    void testHauntedAPSPThreeHauntedAllowed() {
        assertEquals(4, mock.findShortestPath(3));
    }

}