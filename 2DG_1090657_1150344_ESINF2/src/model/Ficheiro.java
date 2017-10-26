/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import graph.AdjacencyMatrixGraph;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class Ficheiro {

    static final String LOCAIS = "LOCAIS";
    static final String CAMINHOS = "CAMINHOS";

    /**
     * Método que lê um ficheiro de texto génerico e retorna uma lista com o
     * conteúdo
     *
     * @param nomeFicheiro String com o nome do ficheiro
     * @return List<String> com conteúdo do ficheiro
     */
    public List<String> lerFicheiro(String nomeFicheiro) {
        Scanner scn = null;                                                     //O(1)
        List<String> lista = new ArrayList<>();                                 //O(1)
        try {
            scn = new Scanner(new FileReader(nomeFicheiro));                    //O(1)
            while (scn.hasNext()) {                                             //O(n)
                lista.add(scn.next());                                          //O(1)
            }
        } catch (FileNotFoundException ex) {
            System.out.printf("Foi impossível ler o ficheiro %s\n", nomeFicheiro);//O(1)
        } finally {
            if (scn != null) {                                                  //O(1)
                scn.close();                                                    //O(1)
            }
        }
        return lista;                                                           //O(1)
    }

    public void lerLocais(String nomeFicheiro, AdjacencyMatrixGraph<Local, Estrada> grafoEstradas, AdjacencyMatrixGraph<Local, Double> grafoCustoEstradas) {
        List<String> conteudoFich = lerFicheiro(nomeFicheiro);
        boolean lerLocais = false;
        boolean lerCaminhos = false;

        String linhaSplit[] = null;
        for (String linha : conteudoFich) {
            if (linha.equals(LOCAIS)) {
                lerLocais = true;
                continue;
            }
            if (linha.equals(CAMINHOS)) {
                lerLocais = false;
                lerCaminhos = true;
                continue;
            }
            if (lerLocais == true) {
                linhaSplit = linha.split(",");
                Local l = new Local(linhaSplit[0], Integer.parseInt(linhaSplit[1]));
                grafoEstradas.insertVertex(l);
                continue;
            }
            if (lerCaminhos == true) {
                final int CAMPO_LOCAL_A = 0;
                final int CAMPO_LOCAL_B = 1;

                final int CAMPO_DIF_ESTRADA = 2;
                linhaSplit = linha.split(",");

                String local_a = linhaSplit[CAMPO_LOCAL_A];
                String local_b = linhaSplit[CAMPO_LOCAL_B];

                Local locala = null, localb = null;
                for (Local l : grafoEstradas.vertices()) {
                    if (local_a.equals(l.getNome())) {
                        locala = l;
                        continue;
                    }
                    if (local_b.equals(l.getNome())) {
                        localb = l;
                        continue;
                    }
                    if (locala != null && localb != null) {
                        break;
                    }
                }

                if (locala != null && localb != null) {
                    Estrada e = new Estrada(Integer.parseInt(linhaSplit[CAMPO_DIF_ESTRADA]));
                    grafoEstradas.insertEdge(locala, localb, e);
                    grafoCustoEstradas.insertEdge(locala, localb, (double) e.getDificuldade());
                }
            }
        }

    }
}
