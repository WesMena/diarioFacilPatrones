/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.List;

/**
 *
 * @author Nvidi
 */
public class Item  extends ItemDecorador{

    OrdenCompra agregados;
    
    public Item(OrdenCompra items){
        this.agregados = items;
    }
    
    public Item(OrdenCompra items,int cantidad,String nombre,double costo){
        this.agregados = items;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costo = costo;
       //Directo a la BD
    }
    
    public Item(int cantidad,String nombre,double costo){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costo = costo;
    }
    
    
    private double costo;
    
    private String nombre;
    
    private int cantidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    Item() {
       
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    @Override
    public double costo() {
        return ((costo*cantidad)+agregados.costo());
    }   





  

   
    
}
