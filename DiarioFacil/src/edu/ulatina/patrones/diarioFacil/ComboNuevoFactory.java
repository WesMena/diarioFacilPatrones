/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author USER
 */
public class ComboNuevoFactory extends ComboFactory {

    public ComboNuevoFactory(ContenidosCombo contenidos) {
     caracteristicas=contenidos;    
        
    }
     
    public CaracteristicasCombo decirCaracteristicas() {
       return caracteristicas; 
    }

  
    
}
