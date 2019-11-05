package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.GUIView;
import org.junit.Test;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void testCreateGraph1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(e2, g.getEdge(v2, v3));
        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }


    @Test // testing adding a vertex to graph
    public void testAddVertex1() {
        Vertex v1 = new Vertex(1, "A");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();

        assertTrue(g.addVertex(v1));
    }

    @Test //testing adding an existing vertex to graph
    public void testAddVertex2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(1, "B");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        assertFalse(g.addVertex(v2));
    }

    @Test //testing if a vertex is in a graph
    public void testVertex1() {
        Vertex v1 = new Vertex(1, "A");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        assertTrue(g.vertex(v1));
    }

    @Test //testing if a vertex isn't in a graph
    public void testVertex2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        assertFalse(g.vertex(v2));
    }

    @Test //testing adding an edge to a graph
    public void testAddEdge1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);

        assertTrue(g.addEdge(e1));
    }

    @Test //testing adding an edge to a graph that already exists
    public void testAddEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v1, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.addEdge(e2));
    }

    @Test //testing if an edge is in a graph
    public void testEdge1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(g.edge(e1));
    }

    @Test //testing if an edge isn't in a graph
    public void testEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.edge(e2));
    }

    @Test //testing if an edge from v1-v2 is in a graph
    public void testVEdge1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(g.edge(v1, v2));
    }

    @Test //testing if an edge from v2-v3 isn't in a graph
    public void testVEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.edge(v2, v3));
    }

    @Test //testing if an edge from v2-v1 is in a graph, opposite arguments
    public void testVEdge3() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);

        assertTrue(g.edge(v2, v1));
    }

    @Test //testing the edge length function if edge is in a graph
    public void testEdgeLength1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertEquals(9, g.edgeLength(v1, v2));
    }

    @Test //testing the edge length function if edge isn't in a graph
    public void testEdgeLength2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertEquals(0, g.edgeLength(v2, v3));
    }

    @Test //testing the edge length sum function
    public void testEdgeLengthSum1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(16, g.edgeLengthSum());
    }

    @Test //testing the edge length sum function, with some repeats of edges
    public void testEdgeLengthSum2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v2, v1, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(16, g.edgeLengthSum());
    }

    @Test //testing the remove edge function when edge is in graph
    public void testRemoveEdge1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        assertTrue(g.remove(e1));
    }

    @Test //testing the remove edge function when edge isn't in graph
    public void testRemoveEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);

        assertFalse(g.remove(e2));
    }

    @Test //testing the remove vertex function when vertex is in the graph
    public void testRemoveVertex1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);

        assertTrue(g.remove(v1));
    }

    @Test //testing the remove vertex function when vertex isn't in the graph
    public void testRemoveVertex2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.remove(v3));
    }

    @Test //testing if the all vertices function works
    public void testAllVertices1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);

        Set<Vertex> answer = new HashSet<>();
        answer.add(v1);
        answer.add(v2);
        answer.add(v3);

        assertEquals(g.allVertices(), answer);
    }

    @Test //testing if the all vertices function works with no vertices in the graph
    public void testAllVertices2() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Set<Vertex> answer = new HashSet<>();

        assertEquals(g.allVertices(), answer);
    }

    @Test //testing if the all incident edges function returns the correct set
    public void testAllEdgesIncident1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge<Vertex>> answer = new HashSet<>();
        answer.add(e1);
        answer.add(e3);

        assertEquals(g.allEdges(v1), answer);
    }

    @Test //testing if the all incident edges function works if no edges incident to v
    public void testAllEdgesIncident2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v3, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);


        Set<Edge<Vertex>> answer = new HashSet<>();

        assertEquals(g.allEdges(v2), answer);
    }

    @Test //testing if the all edges function works
    public void testAllEdges1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge<Vertex>> answer = new HashSet<>();
        answer.add(e1);
        answer.add(e2);
        answer.add(e3);

        assertEquals(answer, g.allEdges());
    }

    @Test //testing if the all edges function works if no edges are in the graph
    public void testAllEdges2() {
        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        Set<Edge<Vertex>> answer = new HashSet<>();

        assertEquals(answer, g.allEdges());
    }

    @Test //testing if the get neighbours function works
    public void testGetNeighbours1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        Map<Vertex, Edge<Vertex>> answer = new HashMap<>();
        answer.put(v1, e1);
        answer.put(v3, e2);

        assertEquals(g.getNeighbours(v2), answer);
    }

    @Test //testing if the get neighbours function works when one node is not a neighbour
    public void testGetNeighbours2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 9);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 20);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Map<Vertex, Edge<Vertex>> answer = new HashMap<>();
        answer.put(v2, e2);
        answer.put(v4, e3);

        assertEquals(answer, g.getNeighbours(v3));
    }

    @Test
    public void testMST1() { //testing MST functions normally
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 4);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 3);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 1);
        Edge<Vertex> e5 = new Edge<>(v4, v1, 2);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        assertEquals(Arrays.asList(e4, e5, e3), g.minimumSpanningTree());
    }

    @Test
    public void testMST2() { //testing MST
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 3);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 3);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 2);
        Edge<Vertex> e5 = new Edge<>(v4, v1, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        assertEquals(Arrays.asList(e5, e2, e4), g.minimumSpanningTree());
    }

    @Test
    public void testMST3() { //NO MST in graph
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 3);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        ArrayList<Edge<Vertex>> answer = new ArrayList<>();

        assertEquals(answer, g.minimumSpanningTree());
    }

    @Test
    public void testMST4() { //checking the merge flag works(e1, e2, e3 form a cycle)
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 10);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 20);
        Edge<Vertex> e3 = new Edge<>(v3, v1, 30);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 40);
        Edge<Vertex> e5 = new Edge<>(v4, v1, 50);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        assertEquals(Arrays.asList(e1, e2, e4), g.minimumSpanningTree());
    }

    @Test
    public void testShortestPath2() { //multiple ways to get to v4
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(Arrays.asList(v3, v2, v1, v4), g.shortestPath(v3, v4));
    }

    @Test
    public void testShortestPath3() { //testing if the algorithm hits a dead end
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        assertEquals(Arrays.asList(v2, v1, v4), g.shortestPath(v2, v4));
    }

    @Test
    public void testShortestPath4() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        assertEquals(Arrays.asList(v2, v3), g.shortestPath(v2, v3));
    }

    @Test
    public void testShortestPath5() { //testing with a cycle
        Vertex A = new Vertex(1, "A");
        Vertex B = new Vertex(2, "B");
        Vertex C = new Vertex(3, "C");
        Vertex D = new Vertex(4, "D");
        Vertex E = new Vertex(5, "E");


        Edge<Vertex> ad = new Edge<>(A, D, 1);
        Edge<Vertex> ab = new Edge<>(A, B, 6);
        Edge<Vertex> be = new Edge<>(B, E, 2);
        Edge<Vertex> bc = new Edge<>(B, C, 5);
        Edge<Vertex> ce = new Edge<>(C, E, 5);
        Edge<Vertex> ed = new Edge<>(E, D, 1);
        Edge<Vertex> db = new Edge<>(D, B, 2);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(A);
        g.addVertex(B);
        g.addVertex(C);
        g.addVertex(D);
        g.addVertex(E);

        g.addEdge(ad);
        g.addEdge(ab);
        g.addEdge(be);
        g.addEdge(bc);
        g.addEdge(ce);
        g.addEdge(ed);
        g.addEdge(db);


        assertEquals(Arrays.asList(A, D, E, C), g.shortestPath(A, C));
    }

    @Test
    public void testShortestPath6() { // no path from source to sink, should return empty list
        Vertex A = new Vertex(1, "A");
        Vertex B = new Vertex(2, "B");
        Vertex C = new Vertex(3, "C");
        Vertex D = new Vertex(4, "D");
        Vertex E = new Vertex(5, "E");


        Edge<Vertex> ad = new Edge<>(A, D, 1);
        Edge<Vertex> be = new Edge<>(B, E, 2);
        Edge<Vertex> bc = new Edge<>(B, C, 5);
        Edge<Vertex> ce = new Edge<>(C, E, 5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(A);
        g.addVertex(B);
        g.addVertex(C);
        g.addVertex(D);
        g.addVertex(E);

        g.addEdge(ad);
        g.addEdge(be);
        g.addEdge(bc);
        g.addEdge(ce);

        ArrayList<Vertex> answer = new ArrayList<>();


        assertEquals(answer, g.shortestPath(A, C));
    }

    @Test
    public void testShortestPath7() { // same source and sink
        Vertex A = new Vertex(1, "A");
        Vertex B = new Vertex(2, "B");
        Vertex C = new Vertex(3, "C");
        Vertex D = new Vertex(4, "D");
        Vertex E = new Vertex(5, "E");


        Edge<Vertex> ad = new Edge<>(A, D, 1);
        Edge<Vertex> be = new Edge<>(B, E, 2);
        Edge<Vertex> bc = new Edge<>(B, C, 5);
        Edge<Vertex> ce = new Edge<>(C, E, 5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(A);
        g.addVertex(B);
        g.addVertex(C);
        g.addVertex(D);
        g.addVertex(E);

        g.addEdge(ad);
        g.addEdge(be);
        g.addEdge(bc);
        g.addEdge(ce);

        ArrayList<Vertex> answer = new ArrayList<>();
        answer.add(A);


        assertEquals(answer, g.shortestPath(A, A));
    }

    @Test
    public void testSearch1() { //testing search works
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        Set<Vertex> answer = new HashSet<>();
        answer.add(v1);
        answer.add(v3);

        assertEquals(answer, g.search(v2, 8));
    }

    @Test
    public void testSearch2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        Set<Vertex> answer = new HashSet<>();
        answer.add(v1);

        assertEquals(answer, g.search(v2, 7));
    }

    @Test
    public void testSearch3() { //testing if none are in range
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        Set<Vertex> answer = new HashSet<>();

        assertEquals(answer, g.search(v2, 1));
    }

    @Test
    public void testSearch4() { //testing if there is no shortest path between vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        Set<Vertex> answer = new HashSet<>();
        answer.add(v1);

        assertEquals(answer, g.search(v2, 6));
    }

    @Test
    public void testDiameter1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 21);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);


        assertEquals(14, g.diameter());
    }

    @Test
    public void testDiameter2() { //testing if graph has multiple components
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);


        assertEquals(7, g.diameter());
    }

    @Test
    public void testDiameter3() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);


        assertEquals(20, g.diameter());
    }

    @Test
    public void testPathLength1() { //Testing if path length works given a list of vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        List<Vertex> path = new ArrayList<>();
        path.add(v1);
        path.add(v2);
        path.add(v3);
        path.add(v4);

        assertEquals(22, g.pathLength(path));
    }

    @Test
    public void testPathLength2() { // passing an empty list
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        List<Vertex> path = new ArrayList<>();

        assertEquals(0, g.pathLength(path));
    }

    @Test
    public void testPathLength3() { // passing a list with 1 vertex
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        List<Vertex> path = new ArrayList<>();
        path.add(v1);

        assertEquals(0, g.pathLength(path));
    }

    @Test
    public void testGetEdge1() { //edge is found in graph
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(e2, g.getEdge(v3, v4));

    }

    @Test
    public void testGetEdge2() { //ensuring we return null if edge isn't found
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v3, v4, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 20);
        Edge<Vertex> e4 = new Edge<>(v3, v2, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertNull(g.getEdge(v3, v5));

    }

    @Test
    public void testKamino1() { //testing seed 1
        Kamino game = new Kamino(1, new MillenniumFalcon(), new GUIView());

        game.start();

        while (!game.gatherSucceeded())  {
            game.update();
        }

        assertTrue(game.huntSucceeded());
        assertTrue(game.gatherSucceeded());
    }

    @Test
    public void testKamino2() { //gather failed with a previous version
        Kamino game = new Kamino(2645594156377344407L, new MillenniumFalcon(), new GUIView());

        game.start();

        while (!game.gatherSucceeded())  {
            game.update();
        }

        assertTrue(game.huntSucceeded());
        assertTrue(game.gatherSucceeded());

    }

    @Test
    public void testKamino3() { //seed that takes a medium time
        Kamino game = new Kamino(-5792514285509696024L, new MillenniumFalcon(), new GUIView());

        game.start();

        while (!game.gatherSucceeded()) {
            game.update();
        }

        assertTrue(game.huntSucceeded());
        assertTrue(game.gatherSucceeded());
    }

    @Test
    public void testKamino4() { //seed that takes a long time
        Kamino game = new Kamino(2541482881071491767L, new MillenniumFalcon(), new GUIView());

        game.start();

        while (!game.gatherSucceeded()) {
            game.update();
        }

        assertTrue(game.huntSucceeded());
        assertTrue(game.gatherSucceeded());
    }

}
