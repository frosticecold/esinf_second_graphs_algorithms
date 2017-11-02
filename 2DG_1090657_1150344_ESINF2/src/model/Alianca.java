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
public class Alianca implements Cloneable {

    private boolean tipo_alianca;
    private float fator_compatibilidade;

    public Alianca(boolean tipo_alianca, float compatibilidade) {
        this.tipo_alianca = tipo_alianca;
        fator_compatibilidade = compatibilidade;
    }

    public Alianca(boolean tipo_alianca) {
        this.tipo_alianca = tipo_alianca;
        fator_compatibilidade = (float) Math.random();
    }

    public Alianca(Alianca a) {
        this.tipo_alianca = a.tipo_alianca;
        fator_compatibilidade = a.fator_compatibilidade;
    }

    public boolean isPublic() {
        return tipo_alianca;
    }

    public float getFatorCompatibilidade() {
        return fator_compatibilidade;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Alianca(this);
    }

}
