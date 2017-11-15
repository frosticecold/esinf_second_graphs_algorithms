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
public class ConquistaComAliado extends Conquista {

    private Personagem aliado;

    public ConquistaComAliado(boolean consegue_conquistar, double forca_necessaria, LinkedList<Local> localinterm, Personagem aliado) {
        super(consegue_conquistar, forca_necessaria, localinterm);
        this.aliado = aliado;
    }

    public ConquistaComAliado(Conquista conquista, Personagem aliado) {
        super(conquista.consegueConquistar(), conquista.forcaNecessaria(), conquista.getLocaisIntermedios());
        this.aliado = aliado;
    }

    public Personagem getAliado() {
        return aliado;
    }

}
