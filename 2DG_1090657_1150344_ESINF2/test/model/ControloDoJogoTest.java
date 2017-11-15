/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graphbase.Graph;
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
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        int NUM_PERSONAGENS = 10;
        int NUM_ALIANCAS = 28;
        assertTrue("Para o ficheiro de teste , o número de personagens é 10", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o número de aliancas é 14", NUM_ALIANCAS == instance.numAliancas());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_PERSONAGENS = 100;
        NUM_ALIANCAS = 910;
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
        int NUM_ESTRADAS_TESTE = 10;
        int NUM_LOCAIS_TESTE = 10;
        assertTrue(("Número de estradas é 10 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
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
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        Local local_a = instance.obterLocalPorNome("Local0");
        Local local_b = instance.obterLocalPorNome("Local4");

        path = instance.caminhoComMenorDificuldade(local_a, local_b);

        tamanho = 2;
        //Caminho é l0-l4
        assertTrue("Caminho mais curto entre local0 e local4 é de tamanho 2", path.size() == tamanho);

        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local3");
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 4;
        assertTrue("Caminho mais curto entre local 0 e local 3 é de tamanho 4", path.size() == tamanho);

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);

        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local100");
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 100 é de tamanho 3", path.size() == tamanho);

        local_b = instance.obterLocalPorNome("Local197");
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 197 é de tamanho 3", path.size() == tamanho);

    }

    /**
     * Test of verificarConquista method, of class Jogo.
     */
    @Test
    public void testVerificarConquista() {
        System.out.println("verificarConquista");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);

        Personagem persOrig = instance.obterPersonagemPorNome("Pers0");
        Local l_target = instance.obterLocalPorNome("Local1");
        //Dificuldade do local
        Local source = instance.obterLocalPorNome("Local0");
        Personagem p_b = instance.obterPersonagemPorNome("Pers1");
        double dificuldade = instance.obterEstrada(source, l_target) + l_target.getDificuldade() + p_b.getForca();

        Conquista result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        assertTrue("A dificuldade do caminho é igual ao calculado:", result.forcaNecessaria() == dificuldade);

        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Nao é possível conquistar", result.consegueConquistar());

        persOrig = instance.obterPersonagemPorNome("Pers5");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);
        persOrig = instance.obterPersonagemPorNome("Pers1");
        l_target = instance.obterLocalPorNome("Local2");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("É possível conquistar", result.consegueConquistar());

        l_target = instance.obterLocalPorNome("Local23");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("É possível conquistar", result.consegueConquistar());

        l_target = instance.obterLocalPorNome("Local111");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("É possível conquistar", result.consegueConquistar());

        l_target = instance.obterLocalPorNome("Local38");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        dificuldade = 570;
        assertTrue("A dificuldade é 570", result.forcaNecessaria() == dificuldade);

        l_target = instance.obterLocalPorNome("Local100");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        assertTrue("Existe dificuldade", result.forcaNecessaria() != -1);

        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        assertTrue("Existe dificuldade", result.forcaNecessaria() != -1);

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

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        Personagem p0 = instance.obterPersonagemPorNome("Pers0");
        Personagem p1 = instance.obterPersonagemPorNome("Pers1");
        Personagem p2 = instance.obterPersonagemPorNome("Pers2");
        Personagem p4 = instance.obterPersonagemPorNome("Pers4");
        Personagem p8 = instance.obterPersonagemPorNome("Pers8");
        Iterable<Personagem> result = instance.devolverTodosAliados(p0);
        int numaliados_contados = 0;
        for (Personagem p : result) {
            numaliados_contados++;
        }
        int numAliados_expected = 3;
        assertEquals(numAliados_expected, numaliados_contados);
        assertTrue("Pers 0 é aliada com Pers1", instance.saoAliados(p0, p1));
        assertFalse("Pers 0 não é aliada com Pers2", instance.saoAliados(p0, p2));
        assertTrue("Pers 0 é aliada com Pers4", instance.saoAliados(p0, p4));
        assertTrue("Pers 0 é aliada com Pers8", instance.saoAliados(p0, p8));

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        p0 = instance.obterPersonagemPorNome("Pers0");
        numAliados_expected = 9; // Personagem 0 tem 9 aliados

        result = instance.devolverTodosAliados(p0);
        numaliados_contados = 0;
        for (Personagem p : result) {
            numaliados_contados++;
        }
        assertEquals(numAliados_expected, numaliados_contados);
        p1 = instance.obterPersonagemPorNome("Pers1");
        result = instance.devolverTodosAliados(p1);
        numAliados_expected = 8;
        numaliados_contados = 0;
        for (Personagem p : result) {
            numaliados_contados++;
        }
        assertEquals(numAliados_expected, numaliados_contados);
    }

    /**
     * Test of determinarAliancaMaisForte method, of class ControloDoJogo.
     */
    @Test
    public void testDeterminarAliancaMaisForte() {
        System.out.println("determinarAliancaMaisForte");
        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        AliancaMaisForte result = instance.determinarAliancaMaisForte();

        //É p3-p5
        Personagem a = instance.obterPersonagemPorNome("Pers3");
        Personagem b = instance.obterPersonagemPorNome("Pers5");
        AliancaMaisForte expResult = new AliancaMaisForte(113.6, a, b);
        assertEquals(expResult.getForca(), result.getForca(), 0.1);
        assertEquals(expResult.getPers_a(), result.getPers_a());
        assertEquals(expResult.getPers_b(), result.getPers_b());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_M);
        a = instance.obterPersonagemPorNome("Pers21");
        b = instance.obterPersonagemPorNome("Pers29");
        result = instance.determinarAliancaMaisForte();
        expResult = new AliancaMaisForte(676, a, b);
        assertEquals(expResult.getForca(), result.getForca(), 0.1);
        assertEquals(expResult.getPers_a(), result.getPers_a());
        assertEquals(expResult.getPers_b(), result.getPers_b());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        a = instance.obterPersonagemPorNome("Pers36");
        b = instance.obterPersonagemPorNome("Pers46");
        expResult = new AliancaMaisForte(781.6, a, b);
        result = instance.determinarAliancaMaisForte();
        assertEquals(expResult.getForca(), result.getForca(), 0.1);
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
        p_a = instance.obterPersonagemPorNome("Pers8");
        p_b = instance.obterPersonagemPorNome("Pers6");
        result = instance.novaAlianca(p_a, p_b, true, 1);
        expResult = true;
        assertTrue("Adiciona uma nova alianca ao ficheiro Teste", result == expResult);
        double comp = instance.obterFatorCompAlianca(p_a, p_b);
        assertTrue("Alianca é a média das aliancas", comp != 1f);

        //Adicionar um vértice novo
        Personagem p10 = new Personagem("Cátia", 200);
        instance.adicionarPersonagem(p10);
        result = instance.novaAlianca(p_a, p10, true, 1);
        expResult = true;
        assertTrue("Adiciona uma nova alianca ao ficheiro Teste", result == expResult);
        comp = instance.obterFatorCompAlianca(p_a, p10);
        assertTrue("Alianca é a média das aliancas", comp == 1f);

        //Ficheiros XL
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        Personagem a = instance.obterPersonagemPorNome("Pers0");
        Personagem b = instance.obterPersonagemPorNome("Pers1");
        result = instance.novaAlianca(a, b, true, 1);
        expResult = true;
        assertTrue("Adicionar uma nova alianca aos ficheiros XL", result == expResult);
        comp = instance.obterFatorCompAlianca(a, b);
        assertTrue("Alianca é a média das aliancas", comp != 1f);

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
        Graph<Personagem, Boolean> result = instance.possiveisNovasAliancas();

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
