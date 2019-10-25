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
public class Administrador extends Usuario {
    public Administrador(){
        super();
    };
    
    public Administrador(int id,String emailUsuario,String password,String nombreUsuario,boolean isActive){
        super(id,emailUsuario,password,nombreUsuario,isActive);
    }

  
}
