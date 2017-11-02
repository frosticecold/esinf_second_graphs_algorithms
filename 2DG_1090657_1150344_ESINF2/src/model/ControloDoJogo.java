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

    public static String LOCAIS_S = "locais_S.txt";
    public static String LOCAIS_M = "locais_M.txt";
    public static String LOCAIS_L = "locais_L.txt";
    public static String LOCAIS_XL = "locais_XL.txt";

    public static String PERSONAGEM_S = "pers_S.txt";
    public static String PERSONAGEM_M = "pers_M.txt";
    public static String PERSONAGEM_L = "pers_L.txt";
    public static String PERSONAGEM_XL = "pers_XL.txt";

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

    public double obterEstrada(Local a, Local b) {
        return mapLocaisEstradas.getEdge(a, b);
    }

    public int numEstradas() {
        return mapLocaisEstradas.numEdges();
    }

    public int numLocais() {
        return mapLocaisEstradas.numVertices();
    }

    //1A
    public void lerLocais(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, mapLocaisEstradas);

    }

    //1B
    public LinkedList<Local> caminhoComMenorDificuldade(Local source, Local target) {

        LinkedList<Local> path = new LinkedList<>();
        EdgeAsDoubleGraphAlgorithms.shortestPath(mapLocaisEstradas, source, target, path);

        return path;

    }

    //1C
    public Conquista verificarConquista(Personagem pers, Local source, Local target) {
        LinkedList<Local> menorCaminho = caminhoComMenorDificuldade(source, target);
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
    public ForcaAlianca determinarAliancaMaisForte(AdjacencyMatrixGraph<Personagem, Alianca> mapAlianca) {
        LinkedList<Personagem> listaPersonagens = new LinkedList<>();
        LinkedList<ForcaAlianca> listaForcaAlianca = new LinkedList<>();

        for (Personagem p : mapAlianca.vertices()) {
            listaPersonagens.push(p);
            double forca_alianca = 0;
            for (Personagem padj : mapAlianca.directConnections(p)) {
                forca_alianca += ((p.getForca() + padj.getForca()) * mapAlianca.getEdge(p, padj).getFatorCompatibilidade());
                listaPersonagens.push(padj);
            }
            listaForcaAlianca.add(new ForcaAlianca(forca_alianca, listaPersonagens));
            listaPersonagens = new LinkedList<>();
        }
        ForcaAlianca fa = null;
        while (!listaForcaAlianca.isEmpty()) {
            ForcaAlianca temp = listaForcaAlianca.pop();
            if (fa == null) {
                fa = temp;
            } else {
                if (fa.getForca() < temp.getForca()) {
                    fa = temp;
                }
            }
        }
        return fa;
    }

    //2d Realizar uma nova aliança entre uma personagem A e uma personagem B.
    public boolean novaAlianca(Personagem p_a, Personagem p_b, boolean tipoalianca) {
        boolean added = false;
        if (mapPersonagensAliancas.getEdge(p_a, p_b) == null) {
            mapPersonagensAliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca));
            added = true;
        } else {
            if (tipoalianca == true) {
                ArrayList<Personagem> arraylist = (ArrayList<Personagem>) mapPersonagensAliancas.directConnections(p_a);
                int numAliancasPublicas = 0;
                float compatibilidade = 0f;
                for (Personagem p : arraylist) {
                    if (mapPersonagensAliancas.getEdge(p_a, p).isPublic()) {
                        numAliancasPublicas++;
                        compatibilidade += mapPersonagensAliancas.getEdge(p_a, p).getFatorCompatibilidade();
                    }
                }
                compatibilidade = compatibilidade / numAliancasPublicas;
                mapPersonagensAliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, compatibilidade));
            } else {

                ArrayList<Personagem> arraylist = (ArrayList<Personagem>) mapPersonagensAliancas.directConnections(p_a);
                int numAliancasPublicas = 0;
                float compatibilidade = 0f;
                for (Personagem p : arraylist) {
                    numAliancasPublicas++;
                    compatibilidade += mapPersonagensAliancas.getEdge(p_a, p).getFatorCompatibilidade();
                }

                compatibilidade = compatibilidade / numAliancasPublicas;
                mapPersonagensAliancas.insertEdge(p_a, p_b, new Alianca(tipoalianca, compatibilidade));
            }
        }
        return added;
    }

    //2e Criar um novo grafo representando todas as novas alianças que podem ser realizadas entre todas as personagens
    //caso todas as alianças existentes fossem públicas
    public AdjacencyMatrixGraph<Personagem, Alianca> possiveisNovasAliancas(AdjacencyMatrixGraph<Personagem, Alianca> mapOriginal) {
        AdjacencyMatrixGraph<Personagem, Alianca> newgraph = graph.GraphAlgorithms.transitiveClosure(mapOriginal, new Alianca(true));
        for (Personagem pOrig : mapOriginal.vertices()) {
            for (Personagem pAdj : mapOriginal.directConnections(pOrig)) {
                if (newgraph.getEdge(pOrig, pAdj) != null) {
                    newgraph.removeEdge(pOrig, pAdj);
                }
            }
        }
        return newgraph;
    }

}
