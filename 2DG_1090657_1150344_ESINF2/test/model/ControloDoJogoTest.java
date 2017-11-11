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
 * @author RaÃºl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogoTest {

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
        assertTrue("Para o ficheiro de teste , o nÃºmero de personagens Ã© 10", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o nÃºmero de aliancas Ã© 14", NUM_ALIANCAS == instance.numAliancas());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_PERSONAGENS = 100;
        NUM_ALIANCAS = 455;
        assertTrue("Para o ficheiro de teste , o nÃºmero de personagens Ã© 100", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o nÃºmero de aliancas Ã© 455", NUM_ALIANCAS == instance.numAliancas());
    }

    /**
     * Test of lerLocais method, of class Jogo.
     */
    @Test
    public void testLerLocais() {
        System.out.println("lerLocais");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        int NUM_ESTRADAS_TESTE = 9;
        int NUM_LOCAIS_TESTE = 10;
        assertTrue(("NÃºmero de estradas Ã© 9 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
        assertTrue(("NÃºmero de Locais Ã© 10 do ficheiro de teste"), instance.numLocais() == NUM_LOCAIS_TESTE);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_LOCAIS_TESTE = 500;
        NUM_ESTRADAS_TESTE = 6273;
        assertTrue(("NÃºmero de estradas Ã© 500 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
        assertTrue(("NÃºmero de Locais Ã© 6273 do ficheiro de teste"), instance.numLocais() == NUM_LOCAIS_TESTE);
    }

    /**
     * Test of caminhoComMenorDificuldade method, of class Jogo.
     */
    @Test
    public void testCaminhoComMenorDificuldade() {
        System.out.println("caminhoComMenorDificuldade");
        ControloDoJogo instance;

        LinkedList<Local> lista1;
        int tamanho;

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        Local local_a = instance.obterLocalPorNome("Local0");
        Local local_b = instance.obterLocalPorNome("Local4");

        lista1 = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 2;
        //Caminho Ã© l0-l4
        assertTrue("Caminho mais curto entre local0 e local4 Ã© de tamanho 2", lista1.size() == tamanho);

        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local3");
        lista1 = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 4;
        assertTrue("Caminho mais curto entre local 0 e local 3 Ã© de tamanho 4", lista1.size() == tamanho);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);
        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local100");
        lista1 = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 100 Ã© de tamanho 3", lista1.size() == tamanho);

        local_b = instance.obterLocalPorNome("Local197");
        lista1 = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 197 Ã© de tamanho 3", lista1.size() == tamanho);

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

        Personagem persOrig = new Personagem("JosÃ©", 200);
        Local source = local1;
        Local target = local6;
        //Caminho local 1 - local 5 - local 6 
        // dificuldade local 5 + dificuldade local 6 + estrada 1-5 + estrada 5-6
        double dificuldade = local5.getDificuldade() + local6.getDificuldade() + jg1.obterEstrada(local1, local5) + jg1.obterEstrada(local5, local6);

        Conquista result = jg1.verificarConquista(persOrig, source, target);
        assertTrue("Ã‰ possÃ­vel conquistar", result.consegueConquistar());
        assertTrue("A dificuldade do caminho Ã© 60:", result.forcaNecessaria() == dificuldade);
        assertTrue("O local 5 Ã© local intermÃ©dio", result.getLocaisIntermedios().contains(local5));

        source = local1;
        target = local10;
        result = jg1.verificarConquista(persOrig, source, target);

        assertNull("Nao Ã© possÃ­vel conquistar", result);
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

        instance.adicionarAlianca(p1, p2, true, 1);
        instance.adicionarAlianca(p2, p5, true, 1);
        instance.adicionarAlianca(p1, p4, true, 1);
        instance.adicionarAlianca(p3, p7, false, 1);
        instance.adicionarAlianca(p4, p6, false, 1);
        instance.adicionarAlianca(p7, p8, false, 1);
        instance.adicionarAlianca(p5, p7, true, 1);
        instance.adicionarAlianca(p7, p4, false, 1);
        instance.adicionarAlianca(p2, p3, true, 1);
        instance.adicionarAlianca(p2, p6, true, 1);
        ForcaAlianca result = instance.determinarAliancaMaisForte();

        //Ã‰ p2-p6
        ForcaAlianca expResult = new ForcaAlianca(27.0, p2, p6);
        assertEquals(expResult.getPers_a(), result.getPers_a());
        assertEquals(expResult.getPers_b(), result.getPers_b());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        Personagem a = instance.obterPersonagemPorNome("Pers36");
        Personagem b = instance.obterPersonagemPorNome("Pers46");
        expResult = new ForcaAlianca(977, a, b);
        result = instance.determinarAliancaMaisForte();
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

        //Vamos repetir adicionar uma nova alianca
        tipoalianca = true;
        expResult = false;
        result = instance.novaAlianca(p_a, p_b, tipoalianca, 1);
        assertEquals(expResult, result);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        Personagem a = instance.obterPersonagemPorNome("Pers0");
        Personagem b = instance.obterPersonagemPorNome("Pers1");
        result = instance.novaAlianca(a, b, true, 1);
        expResult = true;
        assertTrue("Adicionar uma nova alianca aos ficheiros XL", result == expResult);
    }

    /**
     * Test of possiveisNovasAliancas method, of class ControloDoJogo.
     */
    @Test
    public void testPossiveisNovasAliancas() {
        System.out.println("possiveisNovasAliancas");
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        AdjacencyMatrixGraph<Personagem, Alianca> result = instance.possiveisNovasAliancas();
        boolean valido = true;
        AdjacencyMatrixGraph<Personagem, Alianca> clone = instance.cloneAliancas();

    }

}
