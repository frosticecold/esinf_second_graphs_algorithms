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

    /**
     * Atributo se consegue conquistar
     */
    private boolean consegue_conquistar;
    /**
     * Atributo força necessária
     */
    private double forca_necessaria;
    /**
     * Locais intermédios a conquistar
     */
    private LinkedList<Local> locaisIntermedios;

    /**
     * Construtor para uma conquista
     *
     * @param consegue_conquistar Se consegue conquistar
     * @param forca_necessaria A força necessária
     * @param locaisIntermedios Os locais intermédios
     */
    public Conquista(boolean consegue_conquistar, double forca_necessaria, LinkedList<Local> locaisIntermedios) {
        this.consegue_conquistar = consegue_conquistar;
        this.forca_necessaria = forca_necessaria;
        this.locaisIntermedios = locaisIntermedios;
    }

    /**
     * Retorna se consegue conquistar
     *
     * @return
     */
    public boolean consegueConquistar() {
        return consegue_conquistar;
    }

    /**
     * Retorna a força necessária de conquista
     *
     * @return
     */
    public double forcaNecessaria() {
        return forca_necessaria;
    }

    /**
     * Retorna a lista com os locais intermédios
     *
     * @return
     */
    public LinkedList<Local> getLocaisIntermedios() {
        return locaisIntermedios;
    }

}
