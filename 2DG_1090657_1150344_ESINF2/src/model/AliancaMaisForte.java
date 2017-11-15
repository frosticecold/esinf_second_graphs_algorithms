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

    private double forca;
    private Personagem pers_a;
    private Personagem pers_b;

    public AliancaMaisForte(double forca, Personagem pers_a, Personagem pers_b) {
        this.forca = forca;
        this.pers_a = pers_a;
        this.pers_b = pers_b;
    }

    public double getForca() {
        return forca;
    }

    public Personagem getPers_a() {
        return pers_a;
    }

    public Personagem getPers_b() {
        return pers_b;
    }
}
