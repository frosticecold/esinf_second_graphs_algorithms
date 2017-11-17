/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class AliancaMaisForte {

    /**
     * Atributo da forca da aliança mais forte
     */
    private double forca;

    /**
     * Personagem A da Aliança
     */
    private Personagem pers_a;

    /**
     * Personagem B da Aliança
     */
    private Personagem pers_b;

    /**
     * Construtor que recebe como parâmetro a força, pers_a e pers_b
     *
     * @param forca Força da aliança
     * @param pers_a Pers_a
     * @param pers_b Pers_b
     */
    public AliancaMaisForte(double forca, Personagem pers_a, Personagem pers_b) {
        this.forca = forca;
        this.pers_a = pers_a;
        this.pers_b = pers_b;
    }

    /**
     * Retorna a força da aliança
     *
     * @return
     */
    public double getForca() {
        return forca;
    }

    /**
     * Devolve a PersA da aliança
     *
     * @return
     */
    public Personagem getPers_a() {
        return pers_a;
    }

    /**
     * Devolve a PersB da aliança
     *
     * @return
     */
    public Personagem getPers_b() {
        return pers_b;
    }
}
