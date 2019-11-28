/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author josem
 */
public class Factura {
    
    String producto;
    double precio;
    int cantidad;
    double monto;
    int isCombo;
    int productID;
    int isComboID;
    int Cliente;

    public Factura(String producto, double precio, int cantidad, double monto, int isCombo, int productID, int isComboID, int Cliente) {
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.monto = monto;
        this.isCombo = isCombo;
        this.productID = productID;
        this.isComboID = isComboID;
        this.Cliente = Cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIsCombo() {
        return isCombo;
    }

    public void setIsCombo(int isCombo) {
        this.isCombo = isCombo;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getIsComboID() {
        return isComboID;
    }

    public void setIsComboID(int isComboID) {
        this.isComboID = isComboID;
    }

    public int getCliente() {
        return Cliente;
    }

    public void setCliente(int Cliente) {
        this.Cliente = Cliente;
    }
    
}
