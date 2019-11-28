/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;


import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author josem
 */
public class BuscarFacturaMenu {

    public static Dao fact;
    public static Dao suma;
    int idFactura;
    String items = "";
    double subtotal = 0.0;
    double total = 0.0;
    
    public void BuscarOrden(){
    try{    
        idFactura = Integer.parseInt( JOptionPane.showInputDialog("Ingrese el id de la factura que desea ver"));
        fact = new FacturaDao(idFactura);
        suma = new OrdenDao();
        subtotal = ((OrdenDao)suma).geTotalOrden(idFactura);
        total = subtotal + (subtotal * 0.13);
    
        if(idFactura>0){
        List<Factura> lst= fact.getAll();
        for(Factura prod:lst){
                      items=items+"Producto: "+prod.getProducto()+" P/U: "+prod.getPrecio()+" Cant: "+prod.getCantidad()+" Monto: "+prod.getMonto()+"\n\n";
                  }
        String totales = "Subtotal: "+subtotal+"\n"+"Total: "+total;
        items = items + totales;
        JOptionPane.showMessageDialog(null, items);
    }
    } catch(NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Formato incorrecto del id de Factura");
    }
    
    }
    
    
}
