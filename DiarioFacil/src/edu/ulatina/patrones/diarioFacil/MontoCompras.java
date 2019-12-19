

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author wesli
 */
public class MontoCompras {
    boolean comprasSonSuficientes(Cliente c){
      int idCliente=c.getId();
      boolean elegible=false;
        int cantCompras=0;
                double totalCompras=0;
        ResultSet rs=null;
        Statement stmt=null;
        try{
        Conexion conexion = Conexion.getInstance();
        conexion.conectar();
        stmt = conexion.conn.createStatement();
        String sql;
        
        sql=String.format("Select sum(subtotal) as total from bdpatrones.item, bdpatrones.orden"+
" WHERE idOrden=orden and cliente="+idCliente+" group by orden,cliente");
              
               rs=stmt.executeQuery(sql);
               while(rs.next()){
                   cantCompras++;
                    totalCompras=totalCompras+rs.getDouble("total");
               }
        }catch(Exception e){
            e.printStackTrace();
        }
//        finally{
//            this.desconectar();
//        }
        
        
        //Condiciones para ser frecuente
       if(cantCompras>=5 && totalCompras>50000) {
           
           elegible=true;
       }
       
       return elegible;
    }
}
