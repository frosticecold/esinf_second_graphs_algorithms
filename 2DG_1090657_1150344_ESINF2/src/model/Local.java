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
public class Local implements Cloneable {

    private String nome;
    private Personagem personagem;
    private int dificuldade;

    public Local(String nome, int dificuldade, Personagem personagem) {
        this.nome = nome;
        this.personagem = personagem;
        this.dificuldade = dificuldade;

    }

    public Local(String nome, int dificuldade) {
        this.nome = nome;
        this.personagem = null;
        this.dificuldade = dificuldade;
    }

    public Local(Local local) {
        this.nome = local.nome;
        this.personagem = local.personagem;
        this.dificuldade = local.dificuldade;
    }

    public String getNome() {
        return nome;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public Personagem getDono() {
        return personagem;
    }

    public void setDono(Personagem p) {
        personagem = p;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.nome) + Objects.hashCode(dificuldade);
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
        final Local other = (Local) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(personagem, other.personagem)) {
            return false;
        }
        return dificuldade == other.dificuldade;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Local(this);
    }

}
