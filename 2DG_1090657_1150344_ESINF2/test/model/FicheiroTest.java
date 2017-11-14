/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
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
        AdjacencyMatrixGraph<Local, Double> map = new AdjacencyMatrixGraph<>();
        AdjacencyMatrixGraph<Personagem, Alianca> mapPersonagensAliancas = new AdjacencyMatrixGraph<>();

        Ficheiro f = new Ficheiro();
        f.lerLocais(LOCAIS_TESTE, map, mapPersonagensAliancas);
        f.lerPersonagensAliancas(PERS_TEST, map, mapPersonagensAliancas);
         
        final int NUM_LOCAIS = 10;
        assertTrue("Tem todas as edges", NUM_LOCAIS==map.numVertices());
    }

    /**
     * Test of lerPersonagensAliancas method, of class Ficheiro.
     */
    @Test
    public void testLerPersonagensAliancas() {
        System.out.println("lerPersonagensAliancas");
        AdjacencyMatrixGraph<Local, Double> mapLocaisEstradas = new AdjacencyMatrixGraph<>();
        AdjacencyMatrixGraph<Personagem, Alianca> mapPersonagens = new AdjacencyMatrixGraph<>();

        Ficheiro instance = new Ficheiro();
        instance.lerLocais(LOCAIS_TESTE, mapLocaisEstradas, mapPersonagens);
        instance.lerPersonagensAliancas(PERS_TEST, mapLocaisEstradas, mapPersonagens);

        Personagem Pers0 = new Personagem("Pers0", 47);
        Personagem Pers1 = new Personagem("Pers1", 53);
        Personagem Pers2 = new Personagem("Pers2", 55);
        Personagem Pers3 = new Personagem("Pers3", 71);
        Personagem Pers4 = new Personagem("Pers4", 52);
        Personagem Pers5 = new Personagem("Pers5", 71);
        Personagem Pers6 = new Personagem("Pers6", 60);
        Personagem Pers7 = new Personagem("Pers7", 59);
        Personagem Pers8 = new Personagem("Pers8", 46);
        Personagem Pers9 = new Personagem("Pers9", 62);

        assertTrue(mapPersonagens.checkVertex(Pers0));
        assertTrue(mapPersonagens.checkVertex(Pers1));
        assertTrue(mapPersonagens.checkVertex(Pers2));
        assertTrue(mapPersonagens.checkVertex(Pers3));
        assertTrue(mapPersonagens.checkVertex(Pers4));
        assertTrue(mapPersonagens.checkVertex(Pers5));
        assertTrue(mapPersonagens.checkVertex(Pers6));
        assertTrue(mapPersonagens.checkVertex(Pers7));
        assertTrue(mapPersonagens.checkVertex(Pers8));
        assertTrue(mapPersonagens.checkVertex(Pers9));

        int expectedNumAliancas = 14;
        assertTrue("Tem todas as aliancas", expectedNumAliancas == mapPersonagens.numEdges());

    }
}
