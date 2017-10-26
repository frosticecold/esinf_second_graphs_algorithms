/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

    private static String nomeFicheiro = "locais_TEST.txt";

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
        instance.lerLocais(nomeFicheiro);
        final int NUM_ESTRADAS_TESTE = 10;
        assertTrue(("Número de locais é 21 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
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

        jg1.adicionarEstrada(local1, local2, new Estrada(20));
        jg1.adicionarEstrada(local2, local3, new Estrada(10));
        jg1.adicionarEstrada(local3, local4, new Estrada(15));
        jg1.adicionarEstrada(local4, local5, new Estrada(25));
        jg1.adicionarEstrada(local5, local6, new Estrada(30));
        jg1.adicionarEstrada(local6, local7, new Estrada(35));
        jg1.adicionarEstrada(local7, local8, new Estrada(15));
        jg1.adicionarEstrada(local9, local10, new Estrada(10));

        jg1.adicionarEstrada(local2, local4, new Estrada(50));
        jg1.adicionarEstrada(local1, local5, new Estrada(30));

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
        Personagem pers = null;
        Local source = null;
        Local target = null;
        ControloDoJogo instance = new ControloDoJogo();
        Conquista expResult = null;
        Conquista result = instance.verificarConquista(pers, source, target);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
