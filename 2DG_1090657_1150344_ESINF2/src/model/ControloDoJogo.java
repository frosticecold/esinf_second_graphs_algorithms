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

    AdjacencyMatrixGraph<Local, Double> mapLocaisEstradas;
    AdjacencyMatrixGraph<Personagem, Alianca> mapPersonagensAliancas;

    private static String LOCAIS_S = "locais_S.txt";
    private static String LOCAIS_M = "locais_M.txt";
    private static String LOCAIS_L = "locais_L.txt";
    private static String LOCAIS_XL = "locais_XL.txt";

    private static String PERSONAGEM_S = "pers_S.txt";
    private static String PERSONAGEM_M = "pers_M.txt";
    private static String PERSONAGEM_L = "pers_L.txt";
    private static String PERSONAGEM_XL = "pers_XL.txt";

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
                dificuldade += mapLocaisEstradas.getEdge(local_a, local_b) + local_b.getDificuldade();
                if (local_b.getDono() != null) {
                    dificuldade += local_b.getDono().getForca();
                }
            } else {
                local_a = local_b;
                local_b = it.next();
                dificuldade += local_b.getDificuldade() + mapLocaisEstradas.getEdge(local_a, local_b);
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

    //2B
    public int numEstradas() {
        return mapLocaisEstradas.numVertices();
    }
}
