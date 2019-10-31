package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import org.junit.Test;

import java.util.*;

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
        Edge e1 = new Edge(v1,v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);

        assertTrue(g.addEdge(e1));
    }

    @Test //testing adding an edge to a graph that already exists
    public void testAddEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v1, 9);

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
        Edge e1 = new Edge(v1,v2, 9);

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
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v3, 7);

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
        Edge e1 = new Edge(v1,v2, 9);

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
        Edge e1 = new Edge(v1,v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.edge(v2, v3));
    }

    @Test //testing the edge length function if edge is in a graph
    public void testEdgeLength1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Edge e1 = new Edge(v1,v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertEquals(9, g.edgeLength(v1,v2));
    }

    @Test //testing the edge length function if edge isn't in a graph
    public void testEdgeLength2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1,v2, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertEquals(0, g.edgeLength(v2,v3));
    }

    @Test //testing the edge length sum function
    public void testEdgeLengthSum1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v3, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(16, g.edgeLengthSum());
    }

    @Test //testing the edge length sum function, with some repeat of edges
    public void testEdgeLengthSum2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v3, 7);
        Edge e3 = new Edge(v2, v1, 7);

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
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v3, 7);

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
        Edge e1 = new Edge(v1,v2, 9);
        Edge e2 = new Edge(v2, v3, 7);

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
        Edge e1 = new Edge(v1,v2, 9);

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
        Edge e1 = new Edge(v1,v2, 9);

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
        Edge e1 = new Edge(v1,v2, 9);

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

    @Test //testing if the all incident edges function works
    public void testAllEdgesIncident1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1, v2, 9);
        Edge e2 = new Edge(v2, v3, 1);
        Edge e3 = new Edge(v1, v3, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge> answer = new HashSet<>();
        answer.add(e1);
        answer.add(e3);

        assertEquals(g.allEdges(v1), answer);
    }

    @Test //testing if the all edges function works
    public void testAllEdges1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1, v2, 9);
        Edge e2 = new Edge(v2, v3, 1);
        Edge e3 = new Edge(v1, v3, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge> answer = new HashSet<>();
        answer.add(e1);
        answer.add(e2);
        answer.add(e3);

        assertEquals(g.allEdges(), answer);
    }

    @Test //testing if the get neighbours function works
    public void testGetNeighbours1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Edge e1 = new Edge(v1, v2, 9);
        Edge e2 = new Edge(v2, v3, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        Map<Vertex, Edge> answer = new HashMap<>();
        answer.put(v1, e1);
        answer.put(v3, e2);

        assertEquals(g.getNeighbours(v2), answer);
    }

    @Test
    public void testMST1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge e1 = new Edge (v1, v2, 5);
        Edge e2 = new Edge (v2,v3, 4);
        Edge e3 = new Edge (v3, v4, 3);
        Edge e4 = new Edge (v2, v4, 1);
        Edge e5 = new Edge (v4, v1, 2);

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
    public void testMST2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge e1 = new Edge (v1, v2, 3);
        Edge e2 = new Edge (v2, v3, 1);
        Edge e3 = new Edge (v3, v4, 3);
        Edge e4 = new Edge (v2, v4, 2);
        Edge e5 = new Edge (v4, v1, 1);

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
        Edge e1 = new Edge (v1, v2, 3);
        Edge e2 = new Edge (v3, v4, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(Arrays.asList(), g.minimumSpanningTree());
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

        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }

    @Test
    public void testShortestPath3() {
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

        assertEquals(14, g.pathLength(g.shortestPath(v2, v4)));
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

        assertEquals(7, g.pathLength(g.shortestPath(v2, v3)));
    }

    @Test
    public void testSearch1() {
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

        assertEquals(Arrays.asList(v1, v3), g.search(v2, 8));
    }

}
