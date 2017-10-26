/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
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
        assertTrue(("Número de locais é 5 do ficheiro de teste"), instance.numEstradas() == 10);
    }

    /**
     * Test of caminhoComMenorDificuldade method, of class Jogo.
     */
    @Test
    public void testCaminhoComMenorDificuldade() {
        System.out.println("caminhoComMenorDificuldade");
        ControloDoJogo jg1 = new ControloDoJogo();

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
