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
public class Alianca {

    private Personagem pers_a;

    private Personagem pers_b;

    private boolean tipoalianca;

    private float fator_compatibilidade;

    public Alianca(Personagem pers_a, Personagem pers_b, boolean tipoalianca, float compatibilidade) {
        this.pers_a = pers_a;
        this.pers_b = pers_b;
        this.tipoalianca = tipoalianca;
        this.fator_compatibilidade = compatibilidade;
    }

    public Alianca(Personagem pers_a, Personagem pers_b, boolean tipoalianca) {
        this.pers_a = pers_a;
        this.pers_b = pers_b;
        this.tipoalianca = tipoalianca;
        this.fator_compatibilidade = (float) Math.random();
    }

    public Personagem getPersonagemA() {
        return pers_a;
    }

    public Personagem getPersonagemB() {
        return pers_b;
    }

    public boolean getTipoalianca() {
        return tipoalianca;
    }

    public float getFatorCompatibilidade() {
        return fator_compatibilidade;
    }

    public float calcForcaAlianca() {
        return (pers_a.getForca() + pers_b.getForca()) * fator_compatibilidade;
    }

}
