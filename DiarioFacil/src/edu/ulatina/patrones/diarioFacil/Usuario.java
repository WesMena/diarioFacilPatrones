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
public abstract class Usuario {
    
    protected int Id;
    protected String emailUsuario;
    protected String password;
    protected String nombreUsuario;
    protected boolean isActive;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public Usuario(int id,String emailUsuario,String password,String nombreUsuario,boolean isActive){
        this.Id = id;
        this.emailUsuario = emailUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.isActive = isActive;
    }
    
    public Usuario(){};

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
   

    
    
    
}
