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
public class ComboExistenteFactory extends ComboFactory  {

    public ComboExistenteFactory(int id) {
        ComboDao daoFactory=new ComboDao();
      
        for(ArmaCombos combo:daoFactory.getAll()){
              
            if(combo.id==id){
                   
                this.caracteristicas.id=combo.getId();
                
                this.caracteristicas.nombre=combo.nombre;
               
                this.caracteristicas.precio=combo.precio;
                this.caracteristicas.activado=combo.activado;
                this.caracteristicas.borrado=combo.borrado;
            }
        }
    }
   
    public CaracteristicasCombo decirCaracteristicas() {
    return caracteristicas; 
    }
}
