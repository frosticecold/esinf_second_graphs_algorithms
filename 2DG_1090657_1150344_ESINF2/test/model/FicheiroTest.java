/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graphbase.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class FicheiroTest {

    private static final String LOCAIS_TESTE = "locais_TEST.txt";
    private static final String PERS_TEST = "pers_TEST.txt";

    public FicheiroTest() {
    }

    /**
     * Test of lerLocais method, of class Ficheiro.
     */
    @Test
    public void testLerLocais() {
        System.out.println("Teste lerLocais");
        AdjacencyMatrixGraph<Local, Double> grafo_locais = new AdjacencyMatrixGraph<>();
        Graph<Personagem, Boolean> grafo_aliancas = new Graph<>(false);

        Ficheiro f = new Ficheiro();
        f.lerLocais(LOCAIS_TESTE, grafo_locais, grafo_aliancas);
        f.lerPersonagensAliancas(PERS_TEST, grafo_locais, grafo_aliancas);

        final int NUM_LOCAIS = 10;
        assertTrue("Tem todas as edges", NUM_LOCAIS == grafo_locais.numVertices());
    }

    /**
     * Test of lerPersonagensAliancas method, of class Ficheiro.
     */
    @Test
    public void testLerPersonagensAliancas() {
        System.out.println("lerPersonagensAliancas");
        AdjacencyMatrixGraph<Local, Double> grafo_locais = new AdjacencyMatrixGraph<>();
        Graph<Personagem, Boolean> grafo_aliancas = new Graph<>(false);

        Ficheiro instance = new Ficheiro();
        instance.lerLocais(LOCAIS_TESTE, grafo_locais, grafo_aliancas);
        instance.lerPersonagensAliancas(PERS_TEST, grafo_locais, grafo_aliancas);

        Personagem Pers0 = new Personagem("Pers0", 47);
        Personagem Pers1 = new Personagem("Pers1", 53);
        Personagem Pers2 = new Personagem("Pers2", 55);
        Personagem Pers3 = new Personagem("Pers3", 71);
        Personagem Pers4 = new Personagem("Pers4", 52);
        Personagem Pers5 = new Personagem("Pers5", 30);
        Personagem Pers6 = new Personagem("Pers6", 20);
        Personagem Pers7 = new Personagem("Pers7", 59);
        Personagem Pers8 = new Personagem("Pers8", 90);
        Personagem Pers9 = new Personagem("Pers9", 62);

        assertTrue(grafo_aliancas.validVertex(Pers0));
        assertTrue(grafo_aliancas.validVertex(Pers1));
        assertTrue(grafo_aliancas.validVertex(Pers2));
        assertTrue(grafo_aliancas.validVertex(Pers3));
        assertTrue(grafo_aliancas.validVertex(Pers4));
        assertTrue(grafo_aliancas.validVertex(Pers5));
        assertTrue(grafo_aliancas.validVertex(Pers6));
        assertTrue(grafo_aliancas.validVertex(Pers7));
        assertTrue(grafo_aliancas.validVertex(Pers8));
        assertTrue(grafo_aliancas.validVertex(Pers9));

        int expectedNumAliancas = 28;
        assertTrue("Tem todas as aliancas", expectedNumAliancas == grafo_aliancas.numEdges());

    }
}
