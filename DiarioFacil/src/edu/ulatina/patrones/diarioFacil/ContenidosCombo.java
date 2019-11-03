/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ContenidosCombo extends CaracteristicasCombo {


    public ContenidosCombo(int id, String nombre, double precio, boolean activado, boolean borrado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.activado = activado;
        this.borrado = borrado;
        
       
    }
    
    public ContenidosCombo(int id, String nombre, boolean activado, boolean borrado){
       this.id = id;
        this.nombre = nombre;
     
        this.activado = activado;
        this.borrado = borrado;  
    }
    
   
}
