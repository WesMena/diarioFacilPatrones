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
public class Originator {
    private String pass;
    
    private String estado ;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Memento guardar(){
        return new Memento(estado);
    }
    
    public void restaurar(Memento m){
        this.estado = m.getEstado();
    }
    
    
}
