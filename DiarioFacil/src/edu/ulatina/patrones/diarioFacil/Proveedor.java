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
public class Proveedor {
     int codigo=0;
    String nombre="";
    String correo="";
    boolean borrado;
    public Proveedor() {
    }
    
    
  public Proveedor(int cod, String nom, String correo, boolean borrado){
    this.codigo=cod;
    this.nombre=nom;
    this.correo=correo;
    this.borrado=borrado; 
    
}
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }  

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

  
   
    
    
}
