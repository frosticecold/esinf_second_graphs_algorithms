package graphbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author DEI-ISEP
 */
public class GraphAlgorithmsTest {

    Graph<String, String> completeMap = new Graph<>(false);

    public GraphAlgorithmsTest() {
    }

    @Before
    public void setUp() throws Exception {

        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");

        completeMap.insertEdge("Porto", "Aveiro", "A1", 75);
        completeMap.insertEdge("Porto", "Braga", "A3", 60);
        completeMap.insertEdge("Porto", "Vila Real", "A4", 100);
        completeMap.insertEdge("Viseu", "Guarda", "A25", 75);
        completeMap.insertEdge("Guarda", "Castelo Branco", "A23", 100);
        completeMap.insertEdge("Aveiro", "Coimbra", "A1", 60);
        completeMap.insertEdge("Coimbra", "Lisboa", "A1", 200);
        completeMap.insertEdge("Coimbra", "Leiria", "A34", 80);
        completeMap.insertEdge("Aveiro", "Leiria", "A17", 120);
        completeMap.insertEdge("Leiria", "Lisboa", "A8", 150);

//        incompleteMap = completeMap.clone();
//        completeMap.insertEdge("Aveiro","Viseu","A25",85);
//        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
//        completeMap.insertEdge("Lisboa","Faro","A2",280);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of BreadthFirstSearch method, of class GraphAlgorithms.
     */
    @Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        assertTrue("Should be null if vertex does not exist", GraphAlgorithms.BreadthFirstSearch(completeMap, "LX") == null);

        LinkedList<String> path = GraphAlgorithms.BreadthFirstSearch(completeMap, "Faro");
        assertTrue("Should be just one", path.size() == 1);

        Iterator<String> it = path.iterator();
        assertTrue("it should be Faro", it.next().compareTo("Faro") == 0);

        path = GraphAlgorithms.BreadthFirstSearch(completeMap, "Porto");
        assertTrue("Should give seven vertices ", path.size() == 7);

        path = GraphAlgorithms.BreadthFirstSearch(completeMap, "Viseu");
        assertTrue("Should give 3 vertices", path.size() == 3);
    }

    /**
     * Test of DepthFirstSearch method, of class GraphAlgorithms.
     */
    @Test
    public void testDepthFirstSearch() {
        System.out.println("Test of DepthFirstSearch");

        LinkedList<String> path;

        assertTrue("Should be null if vertex does not exist", GraphAlgorithms.DepthFirstSearch(completeMap, "LX") == null);

        path = GraphAlgorithms.DepthFirstSearch(completeMap, "Faro");
        assertTrue("Should be just one", path.size() == 1);

        Iterator<String> it = path.iterator();
        assertTrue("it should be Faro", it.next().compareTo("Faro") == 0);

        path = GraphAlgorithms.DepthFirstSearch(completeMap, "Porto");
        assertTrue("Should give seven vertices ", path.size() == 7);

        path = GraphAlgorithms.DepthFirstSearch(completeMap, "Viseu");
        assertTrue("Should give 3 vertices", path.size() == 3);

        it = path.iterator();
        assertTrue("First in visit should be Viseu", it.next().compareTo("Viseu") == 0);
        assertTrue("then Guarda", it.next().compareTo("Guarda") == 0);
        assertTrue("then Castelo Branco", it.next().compareTo("Castelo Branco") == 0);
    }

    /**
     * Test of allPaths method, of class GraphAlgorithms.
     */
    @Test
    public void testAllPaths() {
        System.out.println("Test of all paths");

        ArrayList<LinkedList<String>> paths = new ArrayList<LinkedList<String>>();

        assertTrue("There should not be paths if vertex does not exist",
                GraphAlgorithms.allPaths(completeMap, "Porto", "LX") == null);

        paths = GraphAlgorithms.allPaths(completeMap, "Porto", "Lisboa");
        assertTrue("There should be 4 paths", paths.size() == 4);

        paths = GraphAlgorithms.allPaths(completeMap, "Porto", "Faro");
        assertTrue("There should not be paths between Porto and Faro in the incomplete map", paths.size() == 0);
    }

    /**
     * Test of shortestPath method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<String>();
        double lenpath = 0;
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "LX", shortPath);
        assertTrue("Length path should be 0 if vertex does not exist", shortPath.size() == 0);

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Faro", shortPath);
        assertTrue("Length path should be 0 if there is no path", shortPath.size() == 0);

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Porto", shortPath);
        assertTrue("Length path should be 1 if source and vertex are the same", shortPath.size() == 1);

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Lisboa", shortPath);
        assertTrue("Path between Porto and Lisboa should be 335 Km", lenpath == 335);

        Iterator<String> it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto") == 0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro") == 0);
        assertTrue("then Coimbra", it.next().compareTo("Coimbra") == 0);
        assertTrue("then Lisboa", it.next().compareTo("Lisboa") == 0);

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Braga", "Leiria", shortPath);
        assertTrue("Path between Braga and Leiria should be 255 Km", lenpath == 255);

        it = shortPath.iterator();

        assertTrue("First in path should be Braga", it.next().compareTo("Braga") == 0);
        assertTrue("then Porto", it.next().compareTo("Porto") == 0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro") == 0);
        assertTrue("then Leiria", it.next().compareTo("Leiria") == 0);

        completeMap.insertEdge("Aveiro", "Viseu", "A25", 85);

        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue("Path between Porto and Castelo Branco should be 335 Km", lenpath == 335);
        assertTrue("N. cities between Porto and Castelo Branco should be 5 ", shortPath.size() == 5);

        it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto") == 0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro") == 0);
        assertTrue("then Viseu", it.next().compareTo("Viseu") == 0);
        assertTrue("then Guarda", it.next().compareTo("Guarda") == 0);
        assertTrue("then Castelo Branco", it.next().compareTo("Castelo Branco") == 0);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue("Path between Porto and Castelo Branco should now be 365 Km", lenpath == 365);
        assertTrue("Path between Porto and Castelo Branco should be 4 cities", shortPath.size() == 4);

        it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto") == 0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro") == 0);
        assertTrue("then Leiria", it.next().compareTo("Leiria") == 0);
        assertTrue("then Castelo Branco", it.next().compareTo("Castelo Branco") == 0);

    }

    @Test
    public void testUnweightedShortestPath() {
        System.out.println("Unweighted shortest path");
        Graph<String, Integer> grafo = new Graph<>(false);
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        String f = "f";
        String g = "g";
        String h = "h";

        grafo.insertVertex(a);
        grafo.insertVertex(b);
        grafo.insertVertex(c);
        grafo.insertVertex(d);
        grafo.insertVertex(e);
        grafo.insertVertex(f);
        grafo.insertVertex(g);
        grafo.insertVertex(h);

        grafo.insertEdge(a, b, 1, 1);
        grafo.insertEdge(b, c, 2, 2);

        grafo.insertEdge(a, d, 3, 2);
        grafo.insertEdge(d, e, 4, 3);
        grafo.insertEdge(e, f, 5, 4);
        grafo.insertEdge(e, g, 5, 4);
        grafo.insertEdge(f, g, 6, 5);
        grafo.insertEdge(g, h, 7, 7);

        LinkedList<String> path = new LinkedList<>();
        GraphAlgorithms.shortestPathEdges(grafo, a, h, path);
        assertTrue("Primeiro é A", path.pop().equals(a));
        assertTrue("2º é D", path.pop().equals(d));
        assertTrue("3º é E", path.pop().equals(e));
        assertTrue("4º é G", path.pop().equals(g));
        assertTrue("5º é H", path.pop().equals(h));

        String i = "i";
        String j = "j";
        String k = "k";
        String l = "l";

        grafo.insertVertex(i);
        grafo.insertVertex(j);
        grafo.insertVertex(k);
        grafo.insertVertex(l);

        grafo.insertEdge(b, k, 7, 100);
        grafo.insertEdge(b, c, 8, 101);
        grafo.insertEdge(c, j, 9, 102);
        grafo.insertEdge(j, i, 10, 103);
        grafo.insertEdge(j, l, 11, 104);
        grafo.insertEdge(i, k, 12, 105);
        grafo.insertEdge(k, l, 13, 106);

        path.clear();
        GraphAlgorithms.shortestPathEdges(grafo, a, l, path);
        assertTrue("Primeiro é A", path.pop().equals(a));
        assertTrue("2º é B", path.pop().equals(b));
        assertTrue("3º é K", path.pop().equals(k));
        assertTrue("4º é l", path.pop().equals(l));
        assertTrue("Vazio", path.isEmpty());

    }
}
