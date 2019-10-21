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
public class Cliente extends  Usuario{
    private boolean isPref;

    public boolean isIsPref() {
        return isPref;
    }

    public void setIsPref(boolean isPref) {
        this.isPref = isPref;
    }
    
    public Cliente(){
        super();
    }
    
    public Cliente(int id,String emailUsuario,String password,String nombreUsuario,boolean isActive,boolean isPref){
        super(id,emailUsuario,password,nombreUsuario,isActive);
        this.isPref = isPref;
    }

}
