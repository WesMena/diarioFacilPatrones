/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.ConcurrentModificationException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author josem
 */
public class Observador implements IObservador{

    private MenuCliente objObservado;
    public static Dao producto;
    public static Dao prov;
    

    
    public Observador(MenuCliente objObservado){
    
        this.objObservado = objObservado;
        objObservado.agregarObservador(this);
    
    }
    
    
    @Override
    public void observadoActualizado() {
        producto = new ProductoDao();
        List<Producto> lstprod=producto.getAll();
        MandaCorreos m = new MandaCorreos();
    try{    

        for (Producto prod : lstprod){
                
              int idProveedor = prod.getCodProveedor();
               int idProducto = prod.getCodProducto();
            
           if(prod.getStockActual()<=prod.getStockMinimo() && prod.isBorrado()==false){
               int diferencia=prod.getStockMinimo()-prod.getStockActual();
               int offset=(int) Math.round(prod.getStockMinimo()*0.3);
               int pedir=diferencia+offset;
               m.enviarCorreo(idProveedor, idProducto,pedir);
               int nuevoStock=pedir+prod.getStockActual();
               
               //Seteamos el stock 
               prod.setStockActual(nuevoStock);
              String params[]=new String[4];
               producto.update(prod, params);
            }
        }
       // for(int i=0;i<lstprod.size();i++){
       // System.out.print(lstprod.size());
        //    int idProveedor = lstprod.get(i).getCodProveedor();
         //   int idProducto = lstprod.get(i).getCodProducto();
            
          //  if(lstprod.get(i).getStockActual()<=lstprod.get(i).getStockMinimo() && lstprod.get(i).isBorrado()==false){
           //     m.enviarCorreo(idProveedor, idProducto, 10);
           // }
           
        //}

    }catch(ConcurrentModificationException e){
        e.printStackTrace();
    }
    
    
    
    }
    
}
