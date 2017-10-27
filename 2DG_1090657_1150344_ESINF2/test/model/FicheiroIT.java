/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class FicheiroIT {

    public FicheiroIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of lerLocais method, of class Ficheiro.
     */
    @Test
    public void testLerLocais() {
        System.out.println("Teste lerLocais");
        final String nomeFicheiro = "locais_TEST.txt";
        AdjacencyMatrixGraph<Local, Double> map = new AdjacencyMatrixGraph<>();
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, map);

        //Locais no ficheiro
        Local local0 = new Local("Local0", 27);
        Local local1 = new Local("Local1", 28);
        Local local2 = new Local("Local2", 38);
        Local local3 = new Local("Local3", 37);
        Local local4 = new Local("Local4", 24);

        //Locais não no ficheiro
        Local local5 = new Local("Local5", 37);
        Local local6 = new Local("Local6", 24);

//        expectedmap.insertEdge(local0, local1, new Estrada(20));
//        expectedmap.insertEdge(local0, local4, new Estrada(50));
//        expectedmap.insertEdge(local1, local2, new Estrada(30));
//        expectedmap.insertEdge(local2, local3, new Estrada(10));
//        expectedmap.insertEdge(local3, local4, new Estrada(20));
        assertTrue("Local0", map.checkVertex(local0));
        assertTrue("Local1", map.checkVertex(local1));
        assertTrue("Local2", map.checkVertex(local2));
        assertTrue("Local3", map.checkVertex(local3));
        assertTrue("Local4", map.checkVertex(local4));
        assertFalse("Local5", map.checkVertex(local5));
        assertFalse("Local6", map.checkVertex(local6));

        Iterable<Double> itr = map.edges();
        int custoEstrada[] = new int[5];
        custoEstrada[0] = 20;
        custoEstrada[1] = 50;
        custoEstrada[2] = 30;
        custoEstrada[3] = 10;
        custoEstrada[4] = 20;
        boolean visitado[] = new boolean[5];
        for (Double e : itr) {
            for (int i = 0; i < 5; i++) {
                if (e == custoEstrada[i]) {
                    if (visitado[i] == false) {
                        visitado[i] = true;
                        break;
                    }
                }
            }
        }
        boolean todosvisitados = true;
        for (boolean b : visitado) {
            if (b == false) {
                todosvisitados = false;
            }
        }

        assertTrue("Tem todas as edges", todosvisitados);
    }
}
