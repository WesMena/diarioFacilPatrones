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
public class Categoria {
  private int codCategoria=0;
    private String nombreCategoria="";
private boolean borrado;

    public Categoria() {
    }

    public Categoria(int cod, String nom, boolean borrado){
        this.codCategoria=cod;
        this.nombreCategoria=nom;
        this.borrado=borrado; 
        
    }
    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }


   
   
    
   
}
