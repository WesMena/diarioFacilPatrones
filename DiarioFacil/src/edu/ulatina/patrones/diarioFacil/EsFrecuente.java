/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author wesli
 */
public class EsFrecuente {
    private MontoCompras compras=new MontoCompras();
    
    public boolean clienteFrecuente(Cliente c){
        boolean frecuente=false;
       if(compras.comprasSonSuficientes(c)){
           frecuente=true;
       }
       return frecuente;
    }
}
