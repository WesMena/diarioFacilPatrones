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
        System.out.println("Llego aquí 4");
        for(ArmaCombos combo:daoFactory.getAll()){
               System.out.println("Llego aquí 5");
            if(combo.id==id){
                   System.out.println("Llego aquí 6");
                this.caracteristicas.id=combo.getId();
                System.out.println("Llego aquí 7");
                this.caracteristicas.nombre=combo.nombre;
                System.out.println("Llego aquí 8");
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
