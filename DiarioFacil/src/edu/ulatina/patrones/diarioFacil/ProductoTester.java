/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.ArrayList;
import java.util.List;


public class ProductoTester  {
     
    
    
    public static void main(String[] args) {
       ProductoDao productodao = new ProductoDao();
        List<Producto> lstProductos = new ArrayList<>();
        
        
       
        lstProductos = productodao.getAll();
        
        for (int i = 0; i < lstProductos.size(); i++) {
            System.out.println(lstProductos.get(i).getNombreProd());
            
            
        }
        
            System.out.println(productodao.get(2).get().getId()+productodao.get(2).get().getNombreProd());   
    }
    
}
