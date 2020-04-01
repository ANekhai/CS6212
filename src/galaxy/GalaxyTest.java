package galaxy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class GalaxyTest {

    //Mock Galaxy class
    Galaxy galaxy;

    List<List<Integer>> edges;

    private List<Integer> makeEdge(int source, int destination, int weight) {
        List<Integer> edge = new ArrayList<>();
        edge.add(source);
        edge.add(destination);
        edge.add(weight);
        return edge;
    }

    @BeforeEach
    void setUp() {
        edges = new ArrayList<>();
        edges.add(makeEdge(1, 2, 1));
        edges.add(makeEdge(1, 3, 2));
        edges.add(makeEdge(2, 4, 3));
        edges.add(makeEdge(3, 5, 4));
        edges.add(makeEdge(1,5,5));

        boolean[] haunted = {false, true, false, true, false};

        galaxy = new Galaxy(edges, haunted);
    }

    @AfterEach
    void tearDown() {
        galaxy.clear();
    }

    @Test
    void testConstructor() {
        edges.add(makeEdge(1, 2, 1));
        boolean[] haunted = {false, true};
        galaxy = new Galaxy(edges, haunted);

        assertTrue(galaxy.hasEdge(1,2));
        assertEquals(2, galaxy.getGalaxySize());
    }

    @Test
    void testAddingNewEdgeForNodesInGraph() {
        assertTrue(galaxy.addEdge(1,4, 1));
        assertTrue(galaxy.hasEdge(1,4));
    }

    @Test
    void testAddingEdgeAlreadyInGraph() {
        assertFalse(galaxy.addEdge(1, 5, 1));
        assertTrue(galaxy.hasEdge(1, 5));
    }

    @Test
    void testAddingEdgeForNodeNotInGraph() {
        assertFalse(galaxy.addEdge(1, 1000, 1));
        assertFalse(galaxy.hasEdge(1, 1000));
        assertFalse(galaxy.addEdge(1000, 1, 1));
        assertFalse(galaxy.hasEdge(1000, 1));
    }

    @Test
    void testGettingWeightsOfEdges() {
        assertEquals(2, galaxy.getWeight(1, 3));
        assertEquals(5, galaxy.getWeight(1, 5));
    }

    @Test
    void testGettingNonEdgeWeight() {
        assertEquals(Integer.MAX_VALUE, galaxy.getWeight(1,6));
    }

    @Test
    void testHauntedPlanets() {
        assertFalse(galaxy.isHaunted(1));
        assertFalse(galaxy.isHaunted(3));
        assertFalse(galaxy.isHaunted(5));

        assertTrue(galaxy.isHaunted(2));
        assertTrue(galaxy.isHaunted(4));
    }

    @Test
    void testGalaxySize() {
        assertEquals(5, galaxy.getGalaxySize());
    }

}