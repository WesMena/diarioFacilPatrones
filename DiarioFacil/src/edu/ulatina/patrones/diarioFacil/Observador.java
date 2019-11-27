/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author josem
 */
public class Observador implements IObservador{

    private MenuCliente objObservado;
    
    public Observador(MenuCliente objObservado){
    
        this.objObservado = objObservado;
        objObservado.agregarObservador(this);
    
    }
    
    
    @Override
    public void observadoActualizado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
