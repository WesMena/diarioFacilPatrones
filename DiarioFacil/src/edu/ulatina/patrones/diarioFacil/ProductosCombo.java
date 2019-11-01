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
public class ProductosCombo {
     int idCombo; 
   Producto prod; 
   int cantidad; 
   boolean borrado; 
   
   
    
 //combos, producto, cantidad, borrado   

    public ProductosCombo() {
    }

    public ProductosCombo(int idCombo, Producto prod, int cantidad) {
        this.idCombo = idCombo;
        this.prod = prod;
        this.cantidad = cantidad;
    }

    public int getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(int idCombo) {
        this.idCombo = idCombo;
    }

    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

 
}
