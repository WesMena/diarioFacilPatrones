/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Producto{
    int id;
   int codProveedor=0;
   int codProducto=0;
   String nombreProd="";
   int stockMinimo=0;
   int stockActual=0;
   double precio=0;
   int codCategoria=0;
 boolean borrado= false;
 String nombreProveedor="";
 String nombreCategoria="";
int Promocion;

    public int getPromocion() {
        return Promocion;
    }

    public void setPromocion(int promocion){
        this.Promocion=promocion;
    }
   

  
    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
 
    public Producto() {
        
    }
    public Producto(int id){
        
        codProducto=id;
    }
   public Producto(int id, int codProveedor, int codProd,String nom, int stockMin, int stockActual,double precio,int categoria){
       this.codProducto=codProd;
       this.codProveedor=codProveedor;
       this.nombreProd=nom;
       this.stockActual=stockActual;
       this.stockMinimo=stockMin;
       this.codCategoria=categoria;
       this.precio=precio;
       this.id=id;
   }

    public int getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(int codProveedor) {
        this.codProveedor = codProveedor;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    
    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }


  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
  
}
