/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graphbase.Graph;
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
        int NUM_PERSONAGENS = 11;
        //No map de adjacencias o número de arestas é o dobro da matriz de adjacencia
        int NUM_ALIANCAS = 30;
        assertTrue("Para o ficheiro de teste , o número de personagens é 10", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro de teste, o número de aliancas é 28", NUM_ALIANCAS == instance.numAliancas());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        NUM_PERSONAGENS = 100;
        NUM_ALIANCAS = 910;
        assertTrue("Para o ficheiro XL , o número de personagens é 100", NUM_PERSONAGENS == instance.numPersonagens());
        assertTrue("Para o ficheiro XL, o número de aliancas é 910", NUM_ALIANCAS == instance.numAliancas());
    }

    /**
     * Test of lerLocais method, of class Jogo.
     */
    @Test
    public void testLerLocais() {
        System.out.println("lerLocais");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        int NUM_ESTRADAS_TESTE = 12;
        int NUM_LOCAIS_TESTE = 11;
        assertTrue(("Número de estradas é 11 do ficheiro de teste"), instance.numEstradas() == NUM_ESTRADAS_TESTE);
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
        assertTrue("Local 0", path.pop().getNome().equals("Local0"));
        assertTrue("Local 4", path.pop().getNome().equals("Local4"));

        //O caminho é l0-l1-l2-l3
        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local3");
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 4;
        assertTrue("Caminho mais curto entre local 0 e local 3 é de tamanho 4", path.size() == tamanho);
        assertTrue("Local 0", path.pop().getNome().equals("Local0"));
        assertTrue("Local 1", path.pop().getNome().equals("Local1"));
        assertTrue("Local 2", path.pop().getNome().equals("Local2"));
        assertTrue("Local 3", path.pop().getNome().equals("Local3"));

        //O caminho é l3-l4-l9-l8
        local_a = instance.obterLocalPorNome("Local3");
        local_b = instance.obterLocalPorNome("Local8");
        tamanho = 4;
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        assertTrue("Caminho mais curto entre local 3 e local 8 é de tamanho 4", path.size() == tamanho);
        assertTrue("Local 3", path.pop().getNome().equals("Local3"));
        assertTrue("Local 4", path.pop().getNome().equals("Local4"));
        assertTrue("Local 9", path.pop().getNome().equals("Local9"));
        assertTrue("Local 8", path.pop().getNome().equals("Local8"));

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_L);

        local_a = instance.obterLocalPorNome("Local0");
        local_b = instance.obterLocalPorNome("Local100");
        path = instance.caminhoComMenorDificuldade(local_a, local_b);
        tamanho = 3;
        assertTrue("Caminho mais curto entre local 0 e local 100 é de tamanho 3", path.size() == tamanho);
        assertTrue("Local 0", path.pop().getNome().equals("Local0"));
        assertTrue("Local 151", path.pop().getNome().equals("Local151"));
        assertTrue("Local 100", path.pop().getNome().equals("Local100"));

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
        double dificuldade = 0;
        Conquista result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Pers0 não consegue conquistar local1", result.consegueConquistar());
        dificuldade = 378;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);

        //Pers 0 Para Local 3 Tem que passar por local4
        l_target = instance.obterLocalPorNome("Local3");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Pers0 não consegue conquistar local3", result.consegueConquistar());
        dificuldade = 254;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
        assertTrue("Existe 1 local intermédio", result.getLocaisIntermedios().size() == 1);
        assertTrue("Local é local4", result.getLocaisIntermedios().getFirst().getNome().equalsIgnoreCase("Local4"));

        //Pers0 não consegue conquistar local6
        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Nao é possível conquistar", result.consegueConquistar());
        dificuldade = 215;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
        assertTrue("Só existe um local intermédio", result.getLocaisIntermedios().size() == 1);
        assertTrue("Local intermédio é local5", result.getLocaisIntermedios().getFirst().getNome().equals("Local5"));

        //Pers5 não consegue conquistar local6
        persOrig = instance.obterPersonagemPorNome("Pers5");
        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        dificuldade = 75;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
        assertTrue("Não existe locais intermédios", result.getLocaisIntermedios().size() == 0);

        //Personagem 1 consegue conquistar local6
        persOrig = instance.obterPersonagemPorNome("Pers1");
        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("Pers1 consegue conquistar local6", result.consegueConquistar());
        dificuldade = 309;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
        assertTrue("Número de locais intermédios é 2", result.getLocaisIntermedios().size() == 2);
        assertTrue("Local intermédio 1 é local0", result.getLocaisIntermedios().pop().getNome().equals("Local0"));
        assertTrue("Local intermédio 2 é local5", result.getLocaisIntermedios().pop().getNome().equals("Local5"));

        //Personagem 1 conquista local4
        persOrig = instance.obterPersonagemPorNome("Pers1");
        l_target = instance.obterLocalPorNome("Local4");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("Pers1 consegue conquistar local4", result.consegueConquistar());
        dificuldade = 220;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
        assertTrue("Número de locais intermédios é 1", result.getLocaisIntermedios().size() == 1);
        assertTrue("Local intermédio 1 é local0", result.getLocaisIntermedios().getFirst().getNome().equals("Local0"));

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
        assertEquals(result.forcaNecessaria(), dificuldade, 0.1);

        l_target = instance.obterLocalPorNome("Local100");
        result = instance.verificarConquista(persOrig, l_target);
        assertFalse("Não é possível conquistar", result.consegueConquistar());
        dificuldade = 516;
        assertTrue("Existe dificuldade", result.forcaNecessaria() != -1);
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);

        l_target = instance.obterLocalPorNome("Local6");
        result = instance.verificarConquista(persOrig, l_target);
        assertTrue("É possível conquistar", result.consegueConquistar());
        assertTrue("Existe dificuldade", result.forcaNecessaria() != -1);
        dificuldade = 466;
        assertEquals(dificuldade, result.forcaNecessaria(), 0.1);
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

        //Teste para a personagem p0     
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

        //Teste para a personagem P8
        Personagem p5 = instance.obterPersonagemPorNome("Pers5");
        Personagem p3 = instance.obterPersonagemPorNome("Pers3");
        Personagem p9 = instance.obterPersonagemPorNome("Pers9");
        Personagem p10 = instance.obterPersonagemPorNome("Pers10");
        result = instance.devolverTodosAliados(p8);
        numaliados_contados = 0;
        for (Personagem p : result) {
            numaliados_contados++;
        }
        numAliados_expected = 6;
        assertEquals(numAliados_expected, numaliados_contados);
        assertTrue("Pers8 é aliada com p0", instance.saoAliados(p8, p0));
        assertTrue("Pers8 é aliada com p2", instance.saoAliados(p8, p2));
        assertTrue("Pers8 é aliado com p3", instance.saoAliados(p8, p3));
        assertTrue("Pers8 é aliada com p5", instance.saoAliados(p8, p5));
        assertTrue("Pers8 é aliada com p9", instance.saoAliados(p8, p9));
        assertTrue("Pers8 é aliada com p10", instance.saoAliados(p8, p10));
        assertFalse("Pers8 não é aliado com p1", instance.saoAliados(p8, p1));
        Personagem p6 = instance.obterPersonagemPorNome("Pers6");
        assertFalse("Pers8 não é aliado com p6", instance.saoAliados(p8, p6));

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);

        //Teste para a personagem 0, contando as linhas no ficheiro tem 9 aliados
        p0 = instance.obterPersonagemPorNome("Pers0");
        numAliados_expected = 9; // Personagem 0 tem 9 aliados

        result = instance.devolverTodosAliados(p0);
        numaliados_contados = 0;
        for (Personagem p : result) {
            numaliados_contados++;
        }
        assertEquals(numAliados_expected, numaliados_contados);
        p9 = instance.obterPersonagemPorNome("Pers9");
        Personagem p16 = instance.obterPersonagemPorNome("Pers16");
        Personagem p18 = instance.obterPersonagemPorNome("Pers18");
        Personagem p19 = instance.obterPersonagemPorNome("Pers19");
        Personagem p20 = instance.obterPersonagemPorNome("Pers20");
        assertTrue("Pers0 é aliado com Pers9", instance.saoAliados(p0, p9));
        assertTrue("Pers0 é aliado com Pers16", instance.saoAliados(p0, p16));
        assertTrue("Pers0 é aliado com Pers18", instance.saoAliados(p0, p18));
        assertTrue("Pers0 é aliado com Pers19", instance.saoAliados(p0, p19));
        assertFalse("Pers0 não é aliada com Pers20", instance.saoAliados(p0, p20));

        //Teste para a personagem 1, contando as linhas no ficheiro tem 8 aliados
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

        //É p1-p7
        Personagem a = instance.obterPersonagemPorNome("Pers1");
        Personagem b = instance.obterPersonagemPorNome("Pers7");
        AliancaMaisForte expResult = new AliancaMaisForte(272.2, a, b);
        assertEquals(expResult.getForca(), result.getForca(), 0.1);
        assertEquals(expResult.getPers_a(), result.getPers_a());
        assertEquals(expResult.getPers_b(), result.getPers_b());

        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_S);
        result = instance.determinarAliancaMaisForte();
        a = instance.obterPersonagemPorNome("Pers0");
        b = instance.obterPersonagemPorNome("Pers3");
        expResult = new AliancaMaisForte(499.2, a, b);
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

        //Vamos repetir adicionar uma nova alianca igual
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
        double fator_esperado = 0.8;
        assertEquals(fator_esperado, comp, 0.1);

        //Adicionar um vértice novo
        tipoalianca = true;
        Personagem p10 = new Personagem("Cátia", 200);
        instance.adicionarPersonagem(p10);
        result = instance.novaAlianca(p_a, p10, true, 1);
        expResult = true;
        assertTrue("Adiciona uma nova alianca ao ficheiro Teste", result == expResult);
        comp = instance.obterFatorCompAlianca(p_a, p10);
        assertTrue("Alianca é a média das aliancas", comp == 1);

        //Realizar uma nova alianca entre dois pontos com ligações privadas
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        tipoalianca = true;
        p_a = instance.obterPersonagemPorNome("Pers3");
        p_b = instance.obterPersonagemPorNome("Pers6");
        result = instance.novaAlianca(p_b, p_a, tipoalianca, 1);
        expResult = true;
        fator_esperado = (0.2 + 0.9 + 0.7 + 0.8) / 4;
        assertTrue("Foi adicionada uma nova aliança entre pers3--pers6", result == expResult);
        assertEquals(fator_esperado, instance.obterFatorCompAlianca(p_a, p_b), 0.05);

        //Realizar mais uma nova alianca com pontos com ligações privadas
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);
        tipoalianca = true;
        p_a = instance.obterPersonagemPorNome("Pers5");
        p_b = instance.obterPersonagemPorNome("Pers7");
        result = instance.novaAlianca(p_b, p_a, tipoalianca, 1);
        expResult = true;
        fator_esperado = (0.8 + 0.2 + 0.9 + 0.3 + 0.7) / 5;
        assertTrue("Foi adicionada uma nova aliança entre pers5--pers7", result == expResult);
        assertEquals(fator_esperado, instance.obterFatorCompAlianca(p_a, p_b), 0.05);

        //Ficheiros XL
        instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_XL);
        p_a = instance.obterPersonagemPorNome("Pers0");
        p_b = instance.obterPersonagemPorNome("Pers1");
        result = instance.novaAlianca(p_a, p_b, true, 1);
        expResult = true;
        assertTrue("Adicionar uma nova alianca aos ficheiros XL", result == expResult);
        comp = instance.obterFatorCompAlianca(p_a, p_b);
        assertTrue("Alianca é a média das aliancas", comp != 1);

        //Pers 0 aliada com Pers9 -0.2
        //Pers9 aliada com Pers45 -0.5
        //Alianca entre Pers0 e Pers45 é a média 0.7/2 = 0.35
        Personagem p45 = instance.obterPersonagemPorNome("Pers45");
        assertTrue("Adicionar uma nova alianca aos ficheiros XL", instance.novaAlianca(p_a, p45, true, 1));
        assertTrue("Alianca é a média das aliancas", instance.obterFatorCompAlianca(p_a, p_b) != 1);

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

    /**
     * Test of conquistarComAliados
     */
    @Test
    public void testconquistarComAliados() {
        System.out.println("conquistarComAliados");
        ControloDoJogo instance = new ControloDoJogo();
        double forca_necessaria = 0;
        instance.lerDados(ControloDoJogo.FICH_TESTE);

        Personagem porig = instance.obterPersonagemPorNome("Pers0");
        Local target = instance.obterLocalPorNome("Local9");
        ConquistaComAliado result = instance.conquistarComAliados(porig, target);
        assertFalse("Não existe possível conquista com aliado para o local9", result.consegueConquistar());

        //Aliado de pers0 conquistar local3
        porig = instance.obterPersonagemPorNome("Pers0");
        target = instance.obterLocalPorNome("Local3");
        result = instance.conquistarComAliados(porig, target);
        assertTrue("Um aliado de Pers0 consegue conquistar Local3", result.consegueConquistar());
        forca_necessaria = (30 + 38 + 55 + 10 + 37 + 71);
        assertEquals(forca_necessaria, result.forcaNecessaria(), 0.05);
        assertEquals(instance.obterPersonagemPorNome("Pers1"), result.getAliado());

        //Aliado de pers0 conquistar local5
        target = instance.obterLocalPorNome("Local5");
        result = instance.conquistarComAliados(porig, target);
        assertTrue("Para a pers0, o aliado pers8 consegue conquistar o local5", result.consegueConquistar());
        forca_necessaria = 15 + 40 + 30;
        assertEquals(forca_necessaria, result.forcaNecessaria(), 0.05);

        //Aliado de pers4 conquistar local5
        porig = instance.obterPersonagemPorNome("Pers4");
        target = instance.obterLocalPorNome("Local5");
        result = instance.conquistarComAliados(porig, target);
        assertFalse("Não existe conquista com aliado para Pers4", result.consegueConquistar());

        //Aliado de pers7 consegue conquistar local5
        porig = instance.obterPersonagemPorNome("Pers7");
        target = instance.obterLocalPorNome("Local5");
        result = instance.conquistarComAliados(porig, target);
        assertTrue("Um aliado de Pers7 consegue conquistar local5", result.consegueConquistar());
        forca_necessaria = 20 + 27 + 47 + 70 + 40 + 30;
        assertEquals(forca_necessaria, result.forcaNecessaria(), 0.05);

        instance.lerDados(ControloDoJogo.FICH_L);
        porig = instance.obterPersonagemPorNome("Pers0");
        target = instance.obterLocalPorNome("Local0");
        result = instance.conquistarComAliados(porig, target);
        assertTrue("Existe conquista com aliado para o local10", result.consegueConquistar());

        porig = instance.obterPersonagemPorNome("Pers10");
        target = instance.obterLocalPorNome("Local35");
        result = instance.conquistarComAliados(porig, target);
        assertFalse("Consegue conquistar", result.consegueConquistar());

        porig = instance.obterPersonagemPorNome("Pers11");
        target = instance.obterLocalPorNome("Local161");
        result = instance.conquistarComAliados(porig, target);
        assertNotNull("Para a Pers11 existe conquista com aliado para o local161", result);
        assertTrue("Consegue conquistar", result.consegueConquistar());

        porig = instance.obterPersonagemPorNome("Pers12");
        target = instance.obterLocalPorNome("Local100");
        result = instance.conquistarComAliados(porig, target);
        assertFalse("Para a Pers12, não existe conquista com aliado para o local100", result.consegueConquistar());

        porig = instance.obterPersonagemPorNome("Pers13");
        target = instance.obterLocalPorNome("Local85");
        result = instance.conquistarComAliados(porig, target);
        assertTrue("Consegue conquistar", result.consegueConquistar());

    }

    @Test
    public void testGerarGrafoAliancasPublicas() {
        System.out.println("gerarGrafoAliancasPublicas");

        ControloDoJogo instance = new ControloDoJogo();
        instance.lerDados(ControloDoJogo.FICH_TESTE);

        Graph<Personagem, Boolean> grafo_aliancas_publicas = instance.gerarGrafoAliancasPublicas();

        Personagem p0 = instance.obterPersonagemPorNome("Pers0");
        Personagem p1 = instance.obterPersonagemPorNome("Pers1");
        Personagem p2 = instance.obterPersonagemPorNome("Pers2");
        Personagem p3 = instance.obterPersonagemPorNome("Pers3");
        Personagem p4 = instance.obterPersonagemPorNome("Pers4");
        Personagem p5 = instance.obterPersonagemPorNome("Pers5");
        Personagem p6 = instance.obterPersonagemPorNome("Pers6");
        Personagem p7 = instance.obterPersonagemPorNome("Pers7");
        Personagem p8 = instance.obterPersonagemPorNome("Pers8");
        Personagem p9 = instance.obterPersonagemPorNome("Pers9");
        Personagem p10 = instance.obterPersonagemPorNome("Pers10");

        //Personagem 0
        assertEquals(grafo_aliancas_publicas.getEdge(p0, p1).getWeight(), 0.3, 0.05);
        assertEquals(grafo_aliancas_publicas.getEdge(p0, p4).getWeight(), 0.7, 0.05);
        assertEquals(grafo_aliancas_publicas.getEdge(p0, p8).getWeight(), 0.9, 0.05);
        //Personagem 8
        assertEquals(grafo_aliancas_publicas.getEdge(p8, p3).getWeight(), 0.2, 0.05);
        assertEquals(grafo_aliancas_publicas.getEdge(p8, p9).getWeight(), 0.5, 0.05);
        assertEquals(grafo_aliancas_publicas.getEdge(p8, p2).getWeight(), 0.1, 0.05);

        assertNull(grafo_aliancas_publicas.getEdge(p3, p4));
        assertNull(grafo_aliancas_publicas.getEdge(p8, p5));
        assertNull(grafo_aliancas_publicas.getEdge(p9, p3));
        assertNull(grafo_aliancas_publicas.getEdge(p8, p10));

    }

}
