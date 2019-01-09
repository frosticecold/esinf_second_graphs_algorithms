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

    /**
     * Atributo aliado
     */
    private Personagem aliado;

    /**
     * Construtor que recebe se se pode conquistar um local, a força necessária,
     * os locais intermédios e a personagem aliado
     *
     * @param consegue_conquistar Se consegue conquistar
     * @param forca_necessaria A força necessária
     * @param localinterm A lista de locais intermédios
     * @param aliado O aliado que ajudou na conquista
     */
    public ConquistaComAliado(boolean consegue_conquistar, double forca_necessaria, LinkedList<Local> localinterm, Personagem aliado) {
        super(consegue_conquistar, forca_necessaria, localinterm);
        this.aliado = aliado;
    }

    /**
     * Construtor que recebe uma conquista como parâmetro e a personagem aliado
     * que ajudou a conquistar
     *
     * @param conquista Conquista
     * @param aliado Aliado
     */
    public ConquistaComAliado(Conquista conquista, Personagem aliado) {
        super(conquista.consegueConquistar(), conquista.forcaNecessaria(), conquista.getLocaisIntermedios());
        this.aliado = aliado;
    }

    /**
     * Retorna o aliado que ajudou a conquistar
     *
     * @return aliado ou null
     */
    public Personagem getAliado() {
        return aliado;
    }

}
