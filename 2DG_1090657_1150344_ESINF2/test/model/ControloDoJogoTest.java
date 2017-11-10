/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogoTest {

    private static String LOCAIS_TEST = "locais_TEST.txt";
    private static String PERS_TEST = "pers_TEST.txt";

    /**
     * Test of lerAlianca method, of class ControloDoJogo.
     */
    @Test
    public void testLerAlianca() {
        System.out.println("lerAlianca");
        String nomeFicheiro = "pers_TEST.txt";
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerAlianca(nomeFicheiro);
        int NUM_PERSONAGENS = 10;
        int NUM_ALIANCAS = 14;
        assertTrue("Para o ficheiro de teste , o número de personagens é 10", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o número de aliancas é 14", NUM_ALIANCAS == instance.numAliancas());

    }

    /**
     * Test of lerLocais method, of class Jogo.
     */
    @Test
    public void testLerLocais() {
        System.out.println("lerLocais");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerAlianca(PERS_TEST);
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
        String nomeLocal = "Local a";
        int dificuldade = 10;
        Personagem p = new Personagem("Personagem a", 10);
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = true;
        boolean result = instance.adicionarLocal(nomeLocal, dificuldade, p);
        assertEquals(expResult, result);
        result = instance.adicionarLocal(nomeLocal, dificuldade, p);
        assertFalse(result);
    }

    /**
     * Test of adicionarLocal method, of class ControloDoJogo.
     */
    @Test
    public void testAdicionarLocal_Local() {
        System.out.println("adicionarLocal");
        Local local = new Local("Local a", 10);
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = true;
        boolean result = instance.adicionarLocal(local);
        assertEquals(expResult, result);
        result = instance.adicionarLocal(local);
        assertFalse(result);
    }

    /**
     * Test of adicionarEstrada method, of class ControloDoJogo.
     */
    @Test
    public void testAdicionarEstrada() {
        System.out.println("adicionarEstrada");
        Personagem p1 = new Personagem("Personagem a", 10);
        Personagem p2 = new Personagem("Personagem b", 12);
        Local a = new Local("Local a", 10, p1);
        Local b = new Local("Local b", 15, p2);
        double e = 3.7;
        ControloDoJogo instance = new ControloDoJogo();
        instance.adicionarLocal(a);
        instance.adicionarLocal(b);
        boolean expResult = true;
        boolean result = instance.adicionarEstrada(a, b, e);
        assertEquals(expResult, result);

        result = instance.adicionarEstrada(a, b, e);
        assertFalse(result);
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
    }

    /**
     * Test of determinarAliancaMaisForte method, of class ControloDoJogo.
     */
    @Test
    public void testDeterminarAliancaMaisForte() {
        System.out.println("determinarAliancaMaisForte");
        ControloDoJogo instance = new ControloDoJogo();

        Personagem p1 = new Personagem("Personagem a", 10);
        Personagem p2 = new Personagem("Personagem b", 12);
        Personagem p3 = new Personagem("Personagem c", 8);
        Personagem p4 = new Personagem("Personagem d", 9);
        Personagem p5 = new Personagem("Personagem e", 4);
        Personagem p6 = new Personagem("Personagem f", 15);
        Personagem p7 = new Personagem("Personagem g", 4);
        Personagem p8 = new Personagem("Personagem h", 7);

        instance.adicionarPersonagem(p1);
        instance.adicionarPersonagem(p2);
        instance.adicionarPersonagem(p3);
        instance.adicionarPersonagem(p4);
        instance.adicionarPersonagem(p5);
        instance.adicionarPersonagem(p6);
        instance.adicionarPersonagem(p7);
        instance.adicionarPersonagem(p8);

        instance.novaAlianca(p1, p2, true, 1);
        instance.novaAlianca(p2, p5, true, 1);
        instance.novaAlianca(p1, p4, true, 1);
        instance.novaAlianca(p3, p7, false, 1);
        instance.novaAlianca(p4, p6, false, 1);
        instance.novaAlianca(p7, p8, false, 1);
        instance.novaAlianca(p5, p7, true, 1);
        instance.novaAlianca(p7, p4, false, 1);
        instance.novaAlianca(p2, p3, true, 1);
        ForcaAlianca result = instance.determinarAliancaMaisForte();

        //Não é p2-p6 porque existe caminho entre eles - p2-p3-p3-p7-p4-p6
        ForcaAlianca expResult = new ForcaAlianca(24.0, p4, p6);
        assertEquals(expResult.getPers_a(), result.getPers_a());
        assertEquals(expResult.getPers_b(), result.getPers_b());
    }

    /**
     * Test of novaAlianca method, of class ControloDoJogo.
     */
    @Test
    public void testNovaAlianca() {
        System.out.println("novaAlianca");
        Personagem p_a = new Personagem("Personagem 1", 10);
        Personagem p_b = new Personagem("Personagem 2", 12);
        boolean tipoalianca = false;
        ControloDoJogo instance = new ControloDoJogo();
        boolean expResult = true;
        instance.adicionarPersonagem(p_a);
        instance.adicionarPersonagem(p_b);
        boolean result = instance.novaAlianca(p_a, p_b, tipoalianca, 1);
        assertEquals(expResult, result);
        tipoalianca = true;
        expResult = false;
        result = instance.novaAlianca(p_a, p_b, tipoalianca, 1);
        assertEquals(expResult, result);
    }

    /**
     * Test of possiveisNovasAliancas method, of class ControloDoJogo.
     */
    @Test
    public void testPossiveisNovasAliancas() {
        System.out.println("possiveisNovasAliancas");
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerAlianca(PERS_TEST);
        instance.lerLocais(LOCAIS_TEST);
        AdjacencyMatrixGraph<Personagem, Alianca> result = instance.possiveisNovasAliancas();
        boolean valido = true;
        AdjacencyMatrixGraph<Personagem, Alianca> clone = instance.cloneAliancas();

    }

}
