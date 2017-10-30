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
    static final String PERSONAGENS = "PERSONAGENS";
    static final String ALIANCAS = "ALIANÇAS";

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

    public void lerLocais(String nomeFicheiro, AdjacencyMatrixGraph<Local, Double> grafoEstradas) {
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
                    }
                    if (local_b.equals(l.getNome())) {
                        localb = l;
                    }
                    if (locala != null && localb != null) {
                        double e = Double.parseDouble(linhaSplit[CAMPO_DIF_ESTRADA]);
                        grafoEstradas.insertEdge(locala, localb, e);
                        break;
                    }

                }
            }

        }
    }

    public void lerPersonagensAliancas(String nomeFicheiro, AdjacencyMatrixGraph<Local, Double> mapLocaisEstradas, AdjacencyMatrixGraph<Personagem, Alianca> mapPersonagens) {
        List<String> conteudoFich = lerFicheiro(nomeFicheiro);
        boolean lerPersonagens = false;
        boolean lerAliancas = false;

        String linhaSplit[] = null;
        for (String linha : conteudoFich) {
            if (linha.equals(PERSONAGENS)) {
                lerPersonagens = true;
                continue;
            }
            if (linha.equals(ALIANCAS)) {
                lerPersonagens = false;
                lerAliancas = true;
                continue;
            }
            if (lerPersonagens == true) {
                linhaSplit = linha.split(",");
                Personagem p = new Personagem(linhaSplit[0], Integer.parseInt(linhaSplit[1]));
                mapPersonagens.insertVertex(p);
                continue;
            }
            if (lerAliancas == true) {
                final int CAMPO_PERS_A = 0;
                final int CAMPO_PERS_B = 1;

                final int CAMPO_TIPO_ALIANCA = 2;
                linhaSplit = linha.split(",");

                String pers_a = linhaSplit[CAMPO_PERS_A];
                String pers_b = linhaSplit[CAMPO_PERS_B];
                Alianca alianca = new Alianca(Boolean.parseBoolean(linhaSplit[CAMPO_TIPO_ALIANCA]));
                Personagem persA = null, persB = null;
                for (Personagem p : mapPersonagens.vertices()) {
                    if (pers_a.equals(p.getNome())) {
                        persA = p;
                        atribuirPersonagemLocal(persA, mapLocaisEstradas);
                        continue;
                    }
                    if (pers_b.equals(p.getNome())) {
                        persB = p;
                        atribuirPersonagemLocal(persB, mapLocaisEstradas);
                        continue;
                    }
                    if (persA != null && persB != null) {
                        mapPersonagens.insertEdge(persA, persB, alianca);
                        break;
                    }
                }

            }
        }
    }

    private void atribuirPersonagemLocal(Personagem p, AdjacencyMatrixGraph<Local, Double> mapLocaisEstradas) {
        ArrayList<Local> locais = (ArrayList<Local>) mapLocaisEstradas.vertices();
        for (int i = 0; i < mapLocaisEstradas.numVertices(); i++) {
            int rnd = (int) (Math.random() * mapLocaisEstradas.numVertices());
            Local l = locais.get(rnd);
            if (l.getDono() == null) {
                l.setDono(p);
                break;
            }
        }
    }
}
