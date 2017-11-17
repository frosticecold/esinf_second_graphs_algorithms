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

    /**
     * Atributo do nome do local
     */
    private String nome;

    /**
     * Atributo da personagem associada a local (pode ser null)
     */
    private Personagem personagem;

    /**
     * Dificuldade do local apenas
     */
    private int dificuldade;

    /**
     * Construtor de Local que recebe como parâmetro o nome, a dificuldade e
     * Personagem dona do local
     *
     * @param nome Nome da Local
     * @param dificuldade Dificuldade do local
     * @param personagem Personagem dona do local
     */
    public Local(String nome, int dificuldade, Personagem personagem) {
        this.nome = nome;
        this.personagem = personagem;
        this.dificuldade = dificuldade;

    }

    /**
     * Construtor de Local que recebe como parâmetro o nome e a dificuldade do
     * local
     *
     * @param nome Nome do Local
     * @param dificuldade Dificuldade do local
     */
    public Local(String nome, int dificuldade) {
        this.nome = nome;
        this.personagem = null;
        this.dificuldade = dificuldade;
    }

    /**
     * Construtor cópia de um Local
     *
     * @param local local a copiar
     */
    public Local(Local local) {
        this.nome = local.nome;
        this.personagem = local.personagem;
        this.dificuldade = local.dificuldade;
    }

    /**
     * Retorna o nome do local
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a dificuldade de um local
     *
     * @return
     */
    public int getDificuldade() {
        return dificuldade;
    }

    /**
     * Retorna o dono do local
     *
     * @return
     */
    public Personagem getDono() {
        return personagem;
    }

    /**
     * Muda o dono do local
     *
     * @param p Personagem a alterar
     */
    public void setDono(Personagem p) {
        personagem = p;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.nome);
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
