/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class Personagem implements Cloneable {

    /**
     * Atributo nome da Personagem
     */
    private String nome;

    /**
     * Força da Personagem
     */
    private int forca;

    /**
     * Construtor Personagem que recebe como parâmetro o nome e a força da
     * personagem
     *
     * @param nome Nome da personagem
     * @param forca Forca da personagem
     */
    public Personagem(String nome, int forca) {
        this.nome = nome;
        this.forca = forca;
    }

    /**
     * Construtor cópia de Personagem
     *
     * @param p Personagem a copiar
     */
    public Personagem(Personagem p) {
        this.nome = p.nome;
        this.forca = p.forca;
    }

    /**
     * Devolve o nome da personagem
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve a força da personagem
     *
     * @return
     */
    public int getForca() {
        return forca;
    }

    /**
     * Modifica a força da personagem
     *
     * @param forca
     */
    public void setForca(int forca) {
        this.forca = forca;
    }

    /**
     * Método toString de uma Personagem
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("Nome: %s Forca:%d", nome, forca);
    }

    /**
     * Método para clonar uma personagem
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Personagem(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Personagem other = (Personagem) obj;
        if (this.forca != other.forca) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

}
