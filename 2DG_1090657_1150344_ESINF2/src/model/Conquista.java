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
public class Conquista {

    private boolean consegue_conquistar;
    private double forca_necessaria;
    private LinkedList<Local> locaisIntermedios;

    public Conquista(boolean consegue_conquistar, double forca_necessaria, LinkedList<Local> locaisIntermedios) {
        this.consegue_conquistar = consegue_conquistar;
        this.forca_necessaria = forca_necessaria;
        this.locaisIntermedios = locaisIntermedios;
    }

    public boolean consegueConquistar() {
        return consegue_conquistar;
    }

    public double forcaNecessaria() {
        return forca_necessaria;
    }

    public LinkedList<Local> getLocaisIntermedios() {
        return locaisIntermedios;
    }

}
