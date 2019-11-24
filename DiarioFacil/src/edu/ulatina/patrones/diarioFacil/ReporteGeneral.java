/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author wesli
 */
public class ReporteGeneral {
    void verOrdenes(){
        ResultSet rs=null; 
        Statement stmt=null; 
        String listaOrdenes="                   "
                + "                             "
                + "Lista de ordenes \n";
        int idOrdenActual;
        int clienteActual; 
        String fechaActual;
        String fecha;
        String hora;
        double montoActual; 
        try{
            Conexion conexion=Conexion.getInstance();
            conexion.conectar();
            stmt=conexion.conn.createStatement();
            String sql; 
            sql=String.format("Select idOrden,cliente,fechaOrden,total from bdpatrones.orden");
            rs=stmt.executeQuery(sql);
            
            while(rs.next()){
              idOrdenActual=rs.getInt("idOrden");
              clienteActual=rs.getInt("cliente");
              fechaActual=rs.getString("fechaOrden");
              montoActual=rs.getDouble("total");
              fecha=fechaActual.substring(0,11);
              hora=fechaActual.substring(11,16);
              listaOrdenes=listaOrdenes+"# Factura: "+idOrdenActual+" Cod.Cliente: "
                      +clienteActual+" Fecha: "+fecha+" Hora: "+hora+" Monto:₡ "+montoActual+"\n";
            }
            
            JOptionPane.showMessageDialog(null,listaOrdenes);
        }catch(Exception e){
            e.printStackTrace();
        }

        
        
    }
}
