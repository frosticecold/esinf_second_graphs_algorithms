/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Raúl Correia <1090657@isep.ipp.pt>
 */
public class Estrada implements Cloneable {

    private int dificuldade;

    public Estrada(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Estrada(Estrada e) {
        this.dificuldade = e.dificuldade;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
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
        final Estrada other = (Estrada) obj;
        if (this.dificuldade != other.dificuldade) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estrada{" + "dificuldade=" + dificuldade + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Estrada(this);
    }

}
