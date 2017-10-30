/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import java.util.LinkedList;
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
public class ControloDoJogoIT {

    private static String LOCAIS_TEST = "locais_TEST.txt";

    public ControloDoJogoIT() {
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
     * Test of lerLocais method, of class Jogo.
     */
    @Test
    public void testLerLocais() {
        System.out.println("lerLocais");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerLocais(LOCAIS_TEST);
        final int NUM_ESTRADAS_TESTE = 9;
        final int NUM_LOCAIS_TESTE = 10;
        assertTrue(("Número de estradas é 9 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
        assertTrue(("Número de Locais é 10 do ficheiro de teste"), instance.numLocais() == NUM_LOCAIS_TESTE);
    }

    /**
     * Test of caminhoComMenorDificuldade method, of class Jogo.
     */
    @Test
    public void testCaminhoComMenorDificuldade() {
        System.out.println("caminhoComMenorDificuldade");
        ControloDoJogo jg1 = new ControloDoJogo();

        Local local1 = new Local("Local a", 10);
        Local local2 = new Local("Local b", 20);
        Local local3 = new Local("Local c", 25);
        Local local4 = new Local("Local d", 15);
        Local local5 = new Local("Local e", 5);
        Local local6 = new Local("Local f", 15);
        Local local7 = new Local("Local g", 25);
        Local local8 = new Local("Local h", 30);
        Local local9 = new Local("Local i", 35);
        Local local10 = new Local("Local j", 8);

        jg1.adicionarLocal(local1);
        jg1.adicionarLocal(local2);
        jg1.adicionarLocal(local3);
        jg1.adicionarLocal(local4);
        jg1.adicionarLocal(local5);
        jg1.adicionarLocal(local6);
        jg1.adicionarLocal(local7);
        jg1.adicionarLocal(local8);
        jg1.adicionarLocal(local9);
        jg1.adicionarLocal(local10);

        jg1.adicionarEstrada(local1, local2, 20);
        jg1.adicionarEstrada(local2, local3, 10);
        jg1.adicionarEstrada(local3, local4, 15);
        jg1.adicionarEstrada(local4, local5, 25);
        jg1.adicionarEstrada(local5, local6, 30);
        jg1.adicionarEstrada(local6, local7, 35);
        jg1.adicionarEstrada(local7, local8, 15);
        jg1.adicionarEstrada(local9, local10, 10);

        jg1.adicionarEstrada(local2, local4, 50);
        jg1.adicionarEstrada(local1, local5, 30);

        LinkedList<Local> lista1 = jg1.caminhoComMenorDificuldade(local1, local5);
        int tamanho = 2;
        assertTrue("O caminho mais curto de local1 para local5 é direto", lista1.size() == tamanho);

        lista1 = jg1.caminhoComMenorDificuldade(local1, local9);
        tamanho = 0;
        assertTrue("Não existe caminho entre local1 e local9", lista1.size() == tamanho);

        lista1 = jg1.caminhoComMenorDificuldade(local1, local7);
        tamanho = 4;
        assertTrue("Caminho mais curto entre local1 e local7 é de tamanho 3", lista1.size() == tamanho);
    }

    /**
     * Test of verificarConquista method, of class Jogo.
     */
    @Test
    public void testVerificarConquista() {
        System.out.println("verificarConquista");

        ControloDoJogo jg1 = new ControloDoJogo();

        Local local1 = new Local("Local a", 10);
        Local local2 = new Local("Local b", 20);
        Local local3 = new Local("Local c", 25);
        Local local4 = new Local("Local d", 15);
        Local local5 = new Local("Local e", 5);
        Local local6 = new Local("Local f", 15);
        Local local7 = new Local("Local g", 25);
        Local local8 = new Local("Local h", 30);
        Local local9 = new Local("Local i", 35);
        Local local10 = new Local("Local j", 8);

        jg1.adicionarLocal(local1);
        jg1.adicionarLocal(local2);
        jg1.adicionarLocal(local3);
        jg1.adicionarLocal(local4);
        jg1.adicionarLocal(local5);
        jg1.adicionarLocal(local6);
        jg1.adicionarLocal(local7);
        jg1.adicionarLocal(local8);
        jg1.adicionarLocal(local9);
        jg1.adicionarLocal(local10);

        jg1.adicionarEstrada(local1, local2, 20);
        jg1.adicionarEstrada(local2, local3, 10);
        jg1.adicionarEstrada(local3, local4, 15);
        jg1.adicionarEstrada(local4, local5, 25);
        jg1.adicionarEstrada(local5, local6, 30);
        jg1.adicionarEstrada(local6, local7, 35);
        jg1.adicionarEstrada(local7, local8, 15);
        jg1.adicionarEstrada(local9, local10, 10);

        jg1.adicionarEstrada(local2, local4, 50);
        jg1.adicionarEstrada(local1, local5, 30);

        Personagem persOrig = new Personagem("José", 200);
        Local source = local1;
        Local target = local6;
        //Caminho local 1 - local 5 - local 6 
        // dificuldade local 5 + dificuldade local 6 + estrada 1-5 + estrada 5-6
        double dificuldade = local5.getDificuldade() + local6.getDificuldade() + jg1.obterEstrada(local1, local5) + jg1.obterEstrada(local5, local6);

        Conquista expResult = null;
        Conquista result = jg1.verificarConquista(persOrig, source, target);
        assertTrue("É possível conquistar", result.consegueConquistar());
        assertTrue("A dificuldade do caminho é 60:", result.forcaNecessaria() == dificuldade);
        assertTrue("O local 5 é local intermédio", result.getLocaisIntermedios().contains(local5));

        source = local1;
        target = local10;
        result = jg1.verificarConquista(persOrig, source, target);

        assertNull("Nao é possível conquistar", result);
    }

    /**
     * Test of adicionarLocal method, of class ControloDoJogo.
     */
    @Test
    public void testAdicionarLocal_3args() {
        System.out.println("adicionarLocal");
        String nomeLocal = "";
        int dificuldade = 0;
        Personagem p = null;
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = false;
        boolean result = instance.adicionarLocal(nomeLocal, dificuldade, p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarLocal method, of class ControloDoJogo.
     */
    @Test
    public void testAdicionarLocal_Local() {
        System.out.println("adicionarLocal");
        Local local = null;
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = false;
        boolean result = instance.adicionarLocal(local);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarEstrada method, of class ControloDoJogo.
     */
    @Test
    public void testAdicionarEstrada() {
        System.out.println("adicionarEstrada");
        Local a = null;
        Local b = null;
        double e = 0.0;
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = false;
        boolean result = instance.adicionarEstrada(a, b, e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numEstradas method, of class ControloDoJogo.
     */
    @Test
    public void testNumEstradas() {
        System.out.println("numEstradas");
        ControloDoJogo instance = new ControloDoJogo();
        int expResult = 0;
        int result = instance.numEstradas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lerAlianca method, of class ControloDoJogo.
     */
    @Test
    public void testLerAlianca() {
        System.out.println("lerAlianca");
        String nomeFicheiro = "";
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerAlianca(nomeFicheiro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of devolverTodosAliados method, of class ControloDoJogo.
     */
    @Test
    public void testDevolverTodosAliados() {
        System.out.println("devolverTodosAliados");
        Personagem source = null;
        ControloDoJogo instance = new ControloDoJogo();
        Iterable<Personagem> expResult = null;
        Iterable<Personagem> result = instance.devolverTodosAliados(source);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of determinarAliancaMaisForte method, of class ControloDoJogo.
     */
    @Test
    public void testDeterminarAliancaMaisForte() {
        System.out.println("determinarAliancaMaisForte");
        AdjacencyMatrixGraph<Personagem, Alianca> graphorig = null;
        ControloDoJogo instance = new ControloDoJogo();
        instance.determinarAliancaMaisForte(graphorig);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of novaAlianca method, of class ControloDoJogo.
     */
    @Test
    public void testNovaAlianca() {
        System.out.println("novaAlianca");
        Personagem p_a = null;
        Personagem p_b = null;
        boolean tipoalianca = false;
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = false;
        boolean result = instance.novaAlianca(p_a, p_b, tipoalianca);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of possiveisNovasAliancas method, of class ControloDoJogo.
     */
    @Test
    public void testPossiveisNovasAliancas() {
        System.out.println("possiveisNovasAliancas");
        AdjacencyMatrixGraph<Personagem, Alianca> mapOriginal = null;
        ControloDoJogo instance = new ControloDoJogo();
        AdjacencyMatrixGraph<Personagem, Alianca> expResult = null;
        AdjacencyMatrixGraph<Personagem, Alianca> result = instance.possiveisNovasAliancas(mapOriginal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
