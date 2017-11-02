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
public class ForcaAlianca {

    private double forca;
    private LinkedList<Personagem> listaAliados;

    public ForcaAlianca(double forca, LinkedList<Personagem> listap) {
        this.forca = forca;
        this.listaAliados = listap;
    }

    public double getForca() {
        return forca;
    }

    public LinkedList<Personagem> getListaAliados() {
        return listaAliados;
    }
}
