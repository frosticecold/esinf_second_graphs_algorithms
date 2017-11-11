/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graph.EdgeAsDoubleGraphAlgorithms;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogo {

    private AdjacencyMatrixGraph<Local, Double> mapLocaisEstradas;
    private AdjacencyMatrixGraph<Personagem, Alianca> mapPersonagensAliancas;

    private final static String LOCAIS_S = "locais_S.txt";
    private final static String LOCAIS_M = "locais_M.txt";
    private final static String LOCAIS_L = "locais_L.txt";
    private final static String LOCAIS_XL = "locais_XL.txt";
    private final static String LOCAIS_TESTE = "locais_TEST.txt";

    private final static String PERSONAGEM_S = "pers_S.txt";
    private final static String PERSONAGEM_M = "pers_M.txt";
    private final static String PERSONAGEM_L = "pers_L.txt";
    private final static String PERSONAGEM_XL = "pers_XL.txt";
    private final static String PERSONAGEM_TESTE = "pers_TEST.txt";

    public final static String FICH_S = "S";
    public final static String FICH_M = "M";
    public final static String FICH_L = "L";
    public final static String FICH_XL = "XL";
    public final static String FICH_TESTE = "TEST";

    ControloDoJogo() {
        mapLocaisEstradas = new AdjacencyMatrixGraph<>();
        mapPersonagensAliancas = new AdjacencyMatrixGraph<>();

    }

    public boolean adicionarLocal(String nomeLocal, int dificuldade, Personagem p) {
        Local l = new Local(nomeLocal, dificuldade, p);
        if (!mapLocaisEstradas.checkVertex(l)) {
            return mapLocaisEstradas.insertVertex(l);
        }
        return false;
    }

    public boolean adicionarLocal(Local local) {
        if (!mapLocaisEstradas.checkVertex(local)) {
            return mapLocaisEstradas.insertVertex(local);
        }
        return false;
    }

    public boolean adicionarEstrada(Local a, Local b, double e) {
        if (mapLocaisEstradas.getEdge(a, b) == null) {
            return mapLocaisEstradas.insertEdge(a, b, e);
        }
        return false;
    }

    public boolean adicionarPersonagem(Personagem p) {
        if (!mapPersonagensAliancas.checkVertex(p)) {
            return mapPersonagensAliancas.insertVertex(p);
        }
        return false;
    }

    public boolean adicionarAlianca(Personagem a, Personagem b, boolean tipoAlianca, float fator_comp) {
        if (!mapPersonagensAliancas.checkVertex(a) || !mapPersonagensAliancas.checkVertex(b)) {
            return false;
        }
        if (mapPersonagensAliancas.getEdge(a, b) == null) {
            mapPersonagensAliancas.insertEdge(a, b, new Alianca(tipoAlianca, fator_comp));
            return true;
        }
        return false;
    }

    public double obterEstrada(Local a, Local b) {
        return mapLocaisEstradas.getEdge(a, b);
    }

    public Personagem obterPersonagemPorNome(String nome) {
        for (Personagem p : mapPersonagensAliancas.vertices()) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public Local obterLocalPorNome(String nome) {
        for (Local l : mapLocaisEstradas.vertices()) {
            if (l.getNome().equalsIgnoreCase(nome)) {
                return l;
            }
        }
        return null;
    }

    public int numEstradas() {
        return mapLocaisEstradas.numEdges();
    }

    public int numLocais() {
        return mapLocaisEstradas.numVertices();
    }

    public int numPersonagens() {
        return mapPersonagensAliancas.numVertices();
    }

    public int numAliancas() {
        return mapPersonagensAliancas.numEdges();
    }

    public int numAliadosDiretos(Personagem source) {
        if (!mapPersonagensAliancas.checkVertex(source)) {
            return 0;
        }
        Iterable<Personagem> itrbl = mapPersonagensAliancas.directConnections(source);
        int num = 0;
        for (Personagem p : itrbl) {
            num++;
        }
        return num;
    }

    public float obterFatorCompAlianca(Personagem a, Personagem b) {
        if (!mapPersonagensAliancas.checkVertex(a) || !mapPersonagensAliancas.checkVertex(b)) {
            return -1f;
        }
        return mapPersonagensAliancas.getEdge(a, b).getFatorCompatibilidade();
    }

    public AdjacencyMatrixGraph<Personagem, Alianca> cloneAliancas() {
        return (AdjacencyMatrixGraph< Personagem, Alianca>) mapPersonagensAliancas.clone();
    }

    //1A
    public void lerLocais(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, mapLocaisEstradas, mapPersonagensAliancas);

    }

    //1B
    public double caminhoComMenorDificuldade(Local source, Local target, LinkedList<Local> path) {
        return EdgeAsDoubleGraphAlgorithms.shortestPath(mapLocaisEstradas, source, target, path);

    }

    //1C
    public Conquista verificarConquista(Personagem pers, Local source, Local target) {
        LinkedList<Local> menorCaminho = new LinkedList<>();
        double dif = caminhoComMenorDificuldade(source, target, menorCaminho);
        if (!menorCaminho.isEmpty()) {
            LinkedList<Local> caminhointermedio = (LinkedList<Local>) menorCaminho.clone();
            Local local_a = source;
            Local local_b = null;
            double dificuldade = 0;

            //Remover o caminho inicial
            if (menorCaminho.peek() == source) {
                menorCaminho.pop();
            }
            while (!menorCaminho.isEmpty()) {
                if (local_a == source && local_b == null) {
                    local_b = menorCaminho.pop();
                    if (local_b.getDono() != pers) {
                        dificuldade += mapLocaisEstradas.getEdge(local_a, local_b) + local_b.getDificuldade();
                        if (local_b.getDono() != null) {
                            dificuldade += local_b.getDono().getForca();
                        }
                    }
                } else {
                    local_a = local_b;
                    local_b = menorCaminho.pop();
                    if (local_b.getDono() != pers) {
                        dificuldade += local_b.getDificuldade() + mapLocaisEstradas.getEdge(local_a, local_b);
                        if (local_b.getDono() != null) {
                            dificuldade += local_b.getDono().getForca();
                        }
                    }
                }

            }
            if (caminhointermedio.peekFirst() == source) {
                caminhointermedio.pop();
            }
            if (caminhointermedio.peekLast() == target) {
                caminhointermedio.removeLast();
            }
            boolean consegue_conquistar = false;
            if (pers.getForca() > dificuldade) {
                consegue_conquistar = true;
            }
            Conquista cq = new Conquista(consegue_conquistar, dificuldade, caminhointermedio);
            return cq;
        }
        return null;
    }

    //2a Construir o grafo  a partir de um ficheiro de texto
    public void lerAlianca(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerPersonagensAliancas(nomeFicheiro, mapLocaisEstradas, mapPersonagensAliancas);
    }

    //2b Devolver uma lista com todos os aliados de uma dada personagem.
    public Iterable<Personagem> devolverTodosAliados(Personagem source) {
        return mapPersonagensAliancas.directConnections(source);
    }

    //2c Determinar qual a aliança mais forte, retornando a força e as personagens dessa aliança
    public ForcaAlianca determinarAliancaMaisForte() {
        Personagem p_a = null;
        Personagem p_b = null;

        double forca_alianca = -1;
        for (Personagem p : mapPersonagensAliancas.vertices()) {
            for (Personagem pAdj : mapPersonagensAliancas.directConnections(p)) {
                if (forca_alianca == -1) {
                    p_a = p;
                    p_b = pAdj;
                    forca_alianca = (p.getForca() + pAdj.getForca()) * mapPersonagensAliancas.getEdge(p, pAdj).getFatorCompatibilidade();
                } else {
                    double outra_forca = (p.getForca() + pAdj.getForca()) * mapPersonagensAliancas.getEdge(p, pAdj).getFatorCompatibilidade();
                    if (outra_forca > forca_alianca) {
                        p_a = p;
                        p_b = pAdj;
                        forca_alianca = outra_forca;
                    }

                }
            }
        }
        if (p_a != null && p_b != null) {

            ForcaAlianca fa = new ForcaAlianca(forca_alianca, p_a, p_b);
            return fa;

        }
        return null;
    }

    public boolean novaAlianca(Personagem p_a, Personagem p_b, boolean tipoalianca, float fator_compatibilidade) {
        if (!mapPersonagensAliancas.checkVertex(p_a) || !mapPersonagensAliancas.checkVertex(p_b)) {
            return false;
        }
        if (mapPersonagensAliancas.getEdge(p_a, p_b) != null) {
            return false;
        }
        AdjacencyMatrixGraph<Personagem, Double> mapaAliancaPeso = copiarAliancaParaAliancaComPeso(mapPersonagensAliancas);
        LinkedList<Personagem> path = new LinkedList<>();
        double dist = graph.EdgeAsDoubleGraphAlgorithms.shortestPath(mapaAliancaPeso, p_a, p_b, path);
        if (dist == -1) {
            mapPersonagensAliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, fator_compatibilidade));
            return true;
        } else {
            int numPersonagens = path.size();
            float fator_comp = 0f;
            Personagem a = null;
            Personagem b = null;
            LinkedList<Personagem> clone = (LinkedList<Personagem>) path.clone();
            while (!clone.isEmpty()) {
                if (a == null && b == null) {
                    a = clone.pop();
                    b = clone.pop();
                    fator_comp = mapPersonagensAliancas.getEdge(a, b).getFatorCompatibilidade();
                } else {
                    a = b;
                    b = clone.pop();
                    fator_comp += mapPersonagensAliancas.getEdge(a, b).getFatorCompatibilidade();
                }
            }
            float mediaComp = fator_comp / numPersonagens;
            mapPersonagensAliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, mediaComp));
            return true;
        }
    }

    //2e Criar um novo grafo representando todas as novas alianças que podem ser realizadas entre todas as personagens
    //caso todas as alianças existentes fossem públicas
    public AdjacencyMatrixGraph<Personagem, Alianca> possiveisNovasAliancas() {
        AdjacencyMatrixGraph<Personagem, Alianca> newgraph = graph.GraphAlgorithms.transitiveClosure(mapPersonagensAliancas, new Alianca(true, 1));
        for (Personagem pOrig : mapPersonagensAliancas.vertices()) {
            for (Personagem pAdj : mapPersonagensAliancas.directConnections(pOrig)) {
                if (newgraph.getEdge(pOrig, pAdj) != null) {
                    newgraph.removeEdge(pOrig, pAdj);
                }
            }
        }

        return newgraph;
    }

    private AdjacencyMatrixGraph<Personagem, Double> copiarAliancaParaAliancaComPeso(AdjacencyMatrixGraph<Personagem, Alianca> mapa) {
        AdjacencyMatrixGraph<Personagem, Double> outromap = new AdjacencyMatrixGraph<>();
        double PESO = 1;
        for (Personagem p : mapa.vertices()) {
            if (!outromap.checkVertex(p)) {
                outromap.insertVertex(p);
            }
            for (Personagem pAdj : mapa.directConnections(p)) {
                if (!outromap.checkVertex(pAdj)) {
                    outromap.insertVertex(pAdj);
                }
                outromap.insertEdge(p, pAdj, PESO);
            }

        }
        return outromap;
    }

    public void lerDados(String nomeFicheiro) {
        switch (nomeFicheiro) {
            case FICH_S:
                lerLocais(LOCAIS_S);
                lerAlianca(PERSONAGEM_S);
                break;
            case FICH_M:
                lerLocais(LOCAIS_M);
                lerAlianca(PERSONAGEM_M);
                break;
            case FICH_L:
                lerLocais(LOCAIS_L);
                lerAlianca(PERSONAGEM_L);
                break;
            case FICH_XL:
                lerLocais(LOCAIS_XL);
                lerAlianca(PERSONAGEM_XL);
                break;
            case FICH_TESTE:
                lerLocais(LOCAIS_TESTE);
                lerAlianca(PERSONAGEM_TESTE);
                break;
            default:
                break;
        }

    }
}
