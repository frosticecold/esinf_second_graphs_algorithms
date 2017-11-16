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

    private String nome;

    private int forca;

    public Personagem(String nome, int forca) {
        this.nome = nome;
        this.forca = forca;
    }

    public Personagem(Personagem p) {
        this.nome = p.nome;
        this.forca = p.forca;
    }

    public String getNome() {
        return nome;
    }

    public int getForca() {
        return forca;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s Forca:%d", nome, forca);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Personagem(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.nome);
        hash = 67 * hash + this.forca;
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
