/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JOptionPane;


/**
 *
 * @author USER
 */
public class ProveedorDao implements Dao<Proveedor> {
public static List<Proveedor> proveedores=new ArrayList<>();

    public ProveedorDao() {
        proveedores=new ArrayList<>();
        
       ResultSet rs=null;
       Statement stmt=null; 
       try{
           Conexion conexion = Conexion.getInstance();
           conexion.conectar();
           stmt = conexion.conn.createStatement();
           String sql; 
           sql="SELECT idProveedor,CorreoProveedor, NombreProveedor,borrado FROM proveedor";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
             int id=rs.getInt("idProveedor");
             String nombre=rs.getString("NombreProveedor");
             String correo=rs.getString("CorreoProveedor");
             int borrado=rs.getByte("borrado");
             if(borrado==0){
             proveedores.add(new Proveedor(id,nombre,correo,false));     
             }else{
             proveedores.add(new Proveedor(id,nombre,correo,true));      
             }
            
             
           }
       }catch(Exception e){
           e.printStackTrace();
       }
//       finally{
//           try{
//               rs.close();
//               stmt.close();
//               this.desconectar();
//           }catch(Exception e){
//               e.printStackTrace();
//           }
//       }
    }

    
   
    public Optional<Proveedor> get(long id) {
        Optional<Proveedor> opProveedor=Optional.empty();
        for(Proveedor p:proveedores){
            if(p.getCodigo()==id ){
                opProveedor=Optional.of(p);
            }
        }
        
        
        return opProveedor;
    }

 
    public List<Proveedor> getAll() {
        return proveedores; 
    }
    

  
    public void save(Proveedor proveedor) {
        Statement stmt=null; 
        boolean fallido=false; 
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt = conexion.conn.createStatement();
            String sql; 
            sql="INSERT INTO bdpatrones.proveedor VALUES("+
                    proveedor.getCodigo()+",'"+proveedor.getCorreo()
                    +"','"+proveedor.getNombre()+"',0)";
            try{
            stmt.executeUpdate(sql);     
            }catch(SQLException sqlE){
                fallido=true;
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
//        finally{
//            try{
//                stmt.close();
//                this.desconectar();
//                
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
        if(fallido){
            JOptionPane.showMessageDialog(null,"El id ingresado pertenece a un "
                  + "proveedor que ya no forma parte del sistema ");
            
        }else{
        proveedores.add(proveedor);
        JOptionPane.showMessageDialog(null,"Proveedor agregado exitosamente");     
        }
       
    }

 
    public void update(Proveedor proveedor, String[] params) {
        
        //Se usa un array de 2 posiciones
        //Posición 0:Nombre
        //Posición 1:Correo
        
        Statement stmt=null;
        String nuevoNombre=params[0];
        String nuevoCorreo=params[1];
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt = conexion.conn.createStatement();
            String sql;
            sql="UPDATE bdpatrones.proveedor SET CorreoProveedor='"+
                    nuevoCorreo+"',NombreProveedor='"+nuevoNombre+
                    "' WHERE idProveedor="+proveedor.getCodigo();
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
//        finally{
//            try{
//                stmt.close();
//                this.desconectar();
//                
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
        
     for(Proveedor prov:proveedores){
         if(prov.getCodigo()==proveedor.getCodigo()){
             prov.setCorreo(nuevoCorreo);
             prov.setNombre(nuevoNombre);
             
         }
     }
     JOptionPane.showMessageDialog(null,"Proveedor actualizado exitosamente");   
    }

  
    public void delete(Proveedor p) {
      Statement stmt=null; 
      try{
          Conexion conexion = Conexion.getInstance();
          conexion.conectar();
          stmt = conexion.conn.createStatement();
       
          
          
          String sql;
          
          sql="UPDATE bdpatrones.proveedor SET borrado=1 WHERE idProveedor="+p.getCodigo();
            
          stmt.executeUpdate(sql);
          
         
 
           JOptionPane.showMessageDialog(null,"Proveedor borrado exitosamente");
      }catch(Exception e){
          e.printStackTrace();
      }
//      finally{
//          try{
//              stmt.close();
//              this.desconectar();
//              
//          }catch(Exception e){
//              e.printStackTrace();
//          }
//      }
      
  
      for(Proveedor proveedor:proveedores){
         
          if(proveedor.getCodigo()==p.getCodigo()){
          proveedor.setBorrado(true);     
          }
      }
   
    }
    
}
