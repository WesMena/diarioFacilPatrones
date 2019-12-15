/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author Nvidi
 */
public class Memento {
    
    private String pass;
    
    public Memento(String pass){
        this.pass = pass;
    }

    public String getEstado() {
        return this.pass;
    }

    public void setEstado(String estado) {
        this.pass = pass;
    }
}
