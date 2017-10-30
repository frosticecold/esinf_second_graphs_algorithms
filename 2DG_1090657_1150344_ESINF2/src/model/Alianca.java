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

    private boolean tipo_alianca;
    private float fator_compatibilidade;

    public Alianca(boolean tipo_alianca) {
        this.tipo_alianca = tipo_alianca;
        fator_compatibilidade = (float) Math.random();
    }

    public boolean getisPublic() {
        return tipo_alianca;
    }

    public float getFatorCompatibilidade() {
        return fator_compatibilidade;
    }
}
