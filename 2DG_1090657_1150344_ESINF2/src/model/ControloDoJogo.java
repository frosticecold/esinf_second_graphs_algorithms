/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import graph.EdgeAsDoubleGraphAlgorithms;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class ControloDoJogo {

    AdjacencyMatrixGraph<Local, Estrada> mapLocais;
    AdjacencyMatrixGraph<Personagem, Alianca> mapAliancas;
    AdjacencyMatrixGraph<Local, Double> mapCustoEstradas;

    private static String LOCAIS_S = "locais_S.txt";
    private static String LOCAIS_M = "locais_M.txt";
    private static String LOCAIS_L = "locais_L.txt";
    private static String LOCAIS_XL = "locais_XL.txt";

    private static String PERSONAGEM_S = "pers_S.txt";
    private static String PERSONAGEM_M = "pers_M.txt";
    private static String PERSONAGEM_L = "pers_L.txt";
    private static String PERSONAGEM_XL = "pers_XL.txt";

    ControloDoJogo() {
        mapLocais = new AdjacencyMatrixGraph<>();
        mapAliancas = new AdjacencyMatrixGraph<>();
        mapCustoEstradas = new AdjacencyMatrixGraph<>();

    }

    public boolean adicionarLocal(String nomeLocal, int dificuldade, Personagem p) {
        Local l = new Local(nomeLocal, dificuldade, p);
        if (!mapLocais.checkVertex(l)) {
            return mapLocais.insertVertex(l);
        }
        return false;
    }

    public boolean adicionarEstrada(Local a, Local b, Estrada e) {
        if (mapLocais.getEdge(a, b) != null) {
            mapCustoEstradas.insertEdge(a, b, (double) e.getDificuldade());
            return mapLocais.insertEdge(a, b, e);
        }
        return false;
    }

    //1A
    public void lerLocais(String nomeFicheiro) {
        Ficheiro f = new Ficheiro();
        f.lerLocais(nomeFicheiro, mapLocais, mapCustoEstradas);

    }

    //1B
    public LinkedList<Local> caminhoComMenorDificuldade(Local source, Local target) {

        LinkedList<Local> path = new LinkedList<>();
        EdgeAsDoubleGraphAlgorithms.shortestPath(mapCustoEstradas, source, target, path);

        return path;

    }

    //1C
    public Conquista verificarConquista(Personagem pers, Local source, Local target) {
        LinkedList<Local> menorCaminho = caminhoComMenorDificuldade(source, target);

        Iterator<Local> it = menorCaminho.iterator();
        Local local_a = source;
        Local local_b = null;
        double dificuldade = 0;

        //Remover o caminho inicial
        if (menorCaminho.peek() == source) {
            menorCaminho.pop();
        }
        while (it.hasNext()) {
            if (local_a == source && local_b == null) {
                local_b = it.next();
                dificuldade += mapCustoEstradas.getEdge(local_a, local_b) + local_b.getDificuldade();
                if (local_b.getDono() != null) {
                    dificuldade += local_b.getDono().getForca();
                }
            } else {
                local_a = local_b;
                local_b = it.next();
                dificuldade += local_b.getDificuldade() + mapCustoEstradas.getEdge(local_a, local_b);
                if (local_b.getDono() != null) {
                    dificuldade += local_b.getDono().getForca();
                }
            }

        }
        if (menorCaminho.peekLast() == target) {
            menorCaminho.removeLast();
        }
        boolean consegue_conquistar = false;
        if (pers.getForca() >= dificuldade) {
            consegue_conquistar = true;
        }
        Conquista cq = new Conquista(consegue_conquistar, dificuldade, menorCaminho);
        return cq;
    }

    public int numEstradas() {
        return mapLocais.numVertices();
    }
}
