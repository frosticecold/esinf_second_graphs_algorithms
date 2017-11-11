/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
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
        assertTrue("Para o ficheiro de teste , o número de personagens é 10", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o número de aliancas é 14", NUM_ALIANCAS == instance.numAliancas());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_PERSONAGENS = 100;
        NUM_ALIANCAS = 455;
        assertTrue("Para o ficheiro de teste , o número de personagens é 100", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o número de aliancas é 455", NUM_ALIANCAS == instance.numAliancas());
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
        assertTrue(("Número de estradas é 9 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
        assertTrue(("Número de Locais é 10 do ficheiro de teste"), instance.numLocais() == NUM_LOCAIS_TESTE);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_LOCAIS_TESTE = 500;
        NUM_ESTRADAS_TESTE = 6273;
        assertTrue(("Número de estradas é 500 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
        assertTrue(("Número de Locais é 6273 do ficheiro de teste"), instance.numLocais() == NUM_LOCAIS_TESTE);
    }

    /**
     * Test of caminhoComMenorDificuldade method, of class Jogo.
     */
    @Test
    public void testCaminhoComMenorDificuldade() {
        System.out.println("caminhoComMenorDificuldade");
        ControloDoJogo instance;

        LinkedList<Local> path = new LinkedList<>();
        int tamanho;
        int dif_caminho_expected = 0;
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        Local local_a = instance.obterLocalPorNome("Local0");
        Local local_b = instance.obterLocalPorNome("Local4");

        double dif = instance.caminhoComMenorDificuldade(local_a, local_b, path);

        tamanho = 2;
        //Caminho é l0-l4
        assertTrue("Caminho mais curto entre local0 e local4 é de tamanho 2", path.size() == tamanho);
        dif_caminho_expected = 50;
        assertEquals(dif_caminho_expected, dif, 1d);

        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local3");
        dif = instance.caminhoComMenorDificuldade(local_a, local_b, path);
        dif_caminho_expected = 60;
        tamanho = 4;
        assertTrue("Caminho mais curto entre local 0 e local 3 é de tamanho 4", path.size() == tamanho);
        assertEquals(dif_caminho_expected, dif, 1d);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);
        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local100");
        dif = instance.caminhoComMenorDificuldade(local_a, local_b, path);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 100 é de tamanho 3", path.size() == tamanho);
        dif_caminho_expected = 43;
        assertEquals(dif_caminho_expected, dif, 1d);

        local_b = instance.obterLocalPorNome("Local197");
        dif = instance.caminhoComMenorDificuldade(local_a, local_b, path);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 197 é de tamanho 3", path.size() == tamanho);
        dif_caminho_expected = 48;
        assertEquals(dif_caminho_expected, dif, 1d);

    }

    /**
     * Test of verificarConquista method, of class Jogo.
     */
    @Test
    public void testVerificarConquista() {
        System.out.println("verificarConquista");

        ControloDoJogo instance = new ControloDoJogo();

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

        instance.adicionarLocal(local1);
        instance.adicionarLocal(local2);
        instance.adicionarLocal(local3);
        instance.adicionarLocal(local4);
        instance.adicionarLocal(local5);
        instance.adicionarLocal(local6);
        instance.adicionarLocal(local7);
        instance.adicionarLocal(local8);
        instance.adicionarLocal(local9);
        instance.adicionarLocal(local10);

        instance.adicionarEstrada(local1, local2, 20);
        instance.adicionarEstrada(local2, local3, 10);
        instance.adicionarEstrada(local3, local4, 15);
        instance.adicionarEstrada(local4, local5, 25);
        instance.adicionarEstrada(local5, local6, 30);
        instance.adicionarEstrada(local6, local7, 35);
        instance.adicionarEstrada(local7, local8, 15);
        instance.adicionarEstrada(local9, local10, 10);

        instance.adicionarEstrada(local2, local4, 50);
        instance.adicionarEstrada(local1, local5, 30);

        Personagem persOrig = new Personagem("José", 200);
        Local source = local1;
        Local target = local6;
        //Caminho local 1 - local 5 - local 6 
        // dificuldade local 5 + dificuldade local 6 + estrada 1-5 + estrada 5-6
        double dificuldade = local5.getDificuldade() + local6.getDificuldade() + instance.obterEstrada(local1, local5) + instance.obterEstrada(local5, local6);

        Conquista result = instance.verificarConquista(persOrig, source, target);
        assertTrue("É possível conquistar", result.consegueConquistar());
        assertTrue("A dificuldade do caminho é 60:", result.forcaNecessaria() == dificuldade);
        assertTrue("O local 5 é local intermédio", result.getLocaisIntermedios().contains(local5));

        source = local1;
        target = local10;
        result = instance.verificarConquista(persOrig, source, target);

        assertNull("Nao é possível conquistar", result);

        instance.lerDados(ControloDoJogo.FICH_L);
        Local l_a = instance.obterLocalPorNome("local0");
        Local l_b = instance.obterLocalPorNome("local50");
        result = instance.verificarConquista(persOrig, l_a, l_b);
        assertTrue("Para o mapa L consegue conquistar entre local0 e local50", result.consegueConquistar());

        Personagem outraPers = new Personagem("Ana", 50);
        l_b = instance.obterLocalPorNome("local199");
        result = instance.verificarConquista(outraPers, l_a, l_b);
        assertFalse("Não é possível conquistar", result.consegueConquistar());

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
        Personagem source;
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        source = instance.obterPersonagemPorNome("Pers0");
        Personagem p1 = instance.obterPersonagemPorNome("Pers1");
        Personagem p4 = instance.obterPersonagemPorNome("Pers4");
        Personagem p8 = instance.obterPersonagemPorNome("Pers8");
        Iterable<Personagem> result = instance.devolverTodosAliados(source);
        ArrayList<Personagem> resultArray = (ArrayList<Personagem>) result;
        assertTrue("Pers0 é aliado com Pers1", resultArray.contains(p1));
        assertTrue("Pers0 é aliado com Pers4", resultArray.contains(p4));
        assertTrue("Pers0 é aliado com Pers8", resultArray.contains(p8));
        int numAliados = 3;
        assertEquals(numAliados, instance.numAliadosDiretos(source));

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        Personagem p0 = instance.obterPersonagemPorNome("Pers0");
        numAliados = 9; // Personagem 0 tem 9 aliados
        assertEquals(numAliados, instance.numAliadosDiretos(p0));
        resultArray = (ArrayList<Personagem>) instance.devolverTodosAliados(p0);
        Personagem p9 = instance.obterPersonagemPorNome("Pers9");
        Personagem p16 = instance.obterPersonagemPorNome("Pers16");
        assertTrue("Pers0 é aliado com Pers9", resultArray.contains(p9));
        assertTrue("Pers0 é aliado com Pers16", resultArray.contains(p16));
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

        //É p2-p6
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

        //Pers 0 aliada com Pers9 -0.2
        //Pers9 aliada com Pers45 -0.5
        //Alianca entre Pers0 e Pers45 é a média 0.7/2 = 0.35
        Personagem p26 = instance.obterPersonagemPorNome("Pers45");
        assertTrue("Adicionar uma nova alianca aos ficheiros XL", instance.novaAlianca(a, p26, true, 1));
        Float f_comp_expected = 0.35f;
        assertEquals(f_comp_expected, instance.obterFatorCompAlianca(a, b), 0.2f);

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

        Personagem p0 = instance.obterPersonagemPorNome("Pers0");
        Personagem p1 = instance.obterPersonagemPorNome("Pers1");
        Personagem p2 = instance.obterPersonagemPorNome("Pers2");
        Personagem p3 = instance.obterPersonagemPorNome("Pers3");
        Personagem p4 = instance.obterPersonagemPorNome("Pers4");
        assertNotNull(result.getEdge(p0, p2));
        assertNotNull(result.getEdge(p0, p3));
        assertNull(result.getEdge(p0, p4));
        assertNull(result.getEdge(p0, p1));

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);
        result = instance.possiveisNovasAliancas();
        p0 = instance.obterPersonagemPorNome("Pers0");
        p1 = instance.obterPersonagemPorNome("Pers1");
        p2 = instance.obterPersonagemPorNome("Pers2");
        p3 = instance.obterPersonagemPorNome("Pers3");
        p4 = instance.obterPersonagemPorNome("Pers4");
        Personagem p5 = instance.obterPersonagemPorNome("Pers5");
        assertNotNull(result.getEdge(p0, p1));
        assertNotNull(result.getEdge(p0, p2));
        assertNull(result.getEdge(p0, p3));
        assertNotNull(result.getEdge(p0, p4));

        assertNull(result.getEdge(p1, p5));
        assertNotNull(result.getEdge(p1, p2));
        assertNotNull(result.getEdge(p1, p3));
        assertNotNull(result.getEdge(p1, p4));

        assertNotNull(result.getEdge(p2, p3));
        assertNotNull(result.getEdge(p2, p4));

        assertNotNull(result.getEdge(p3, p4));

    }

}
