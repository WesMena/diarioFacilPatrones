/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
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
        
       convertirEnFrecuente();
      enviaCorreos();
      
    }
    
    void enviaCorreos(){
          producto = new ProductoDao();
       
          String params[]=new String[4];
      
        List<Producto> lstprod=producto.getAll();
        MandaCorreos m = new MandaCorreos();
        
   
        //manda un correo si stockActual<=stockMinimo
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
            
               producto.update(prod, params);
            }
        }

    }catch(ConcurrentModificationException e){
        e.printStackTrace();
    }
    
    
    }
    
    void convertirEnFrecuente(){
            //Convierte a un cliente en frecuente si cumple con los criterios 
                 String params[]=new String[4];
         ClienteDao cli=new ClienteDao();
        Boolean cumpleCriterios;
        EsFrecuente frecuente= new EsFrecuente();
       Optional<Cliente> cliente; 
      cliente=cli.get(Constantes.USUARIOLOGUEADO.getId());
     
        cumpleCriterios=frecuente.clienteFrecuente(cliente.get());
        
       
        if(cumpleCriterios){
           
           cliente.get().setIsPref(true);
           
            cli.update(cliente.get(), params);
        }
         
    }
    
}
