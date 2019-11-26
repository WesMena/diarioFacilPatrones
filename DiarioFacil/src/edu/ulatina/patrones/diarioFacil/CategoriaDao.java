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
public class CategoriaDao implements Dao<Categoria> {
public static List<Categoria> categorias=new ArrayList<>();
    public CategoriaDao() {
        categorias=new ArrayList<>();
           ResultSet rs=null;
       Statement stmt=null; 
       try{
           Conexion conexion = Conexion.getInstance();
           conexion.conectar();
           stmt = conexion.conn.createStatement();
           String sql; 
           sql="SELECT idCategoria,nombreCategoria,borrado FROM categoria";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
             int id=rs.getInt("idCategoria");
             String nombre=rs.getString("nombreCategoria");
             int borrado=rs.getByte("borrado");
             if(borrado==0){
        categorias.add(new Categoria(id,nombre,false));                
             }else{
            categorias.add(new Categoria(id,nombre,true));         
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

    
    
    public Optional<Categoria> get(long id) {
        Optional<Categoria> opCategoria=Optional.empty();
        for(Categoria cat:categorias){
            if(cat.getCodCategoria()==id){
                opCategoria=Optional.of(cat);
            }
        }
        return opCategoria; 
    }

   
    public List<Categoria> getAll() {
       return categorias; 
    }


    public void save(Categoria t) {
         Statement stmt=null; 
         boolean fallido=false;
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt = conexion.conn.createStatement();
            String sql; 
            sql="INSERT INTO bdpatrones.categoria VALUES("+
                    t.getCodCategoria()+",'"+t.getNombreCategoria()
                    +"',0)";
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
          JOptionPane.showMessageDialog(null,"El id ingresado pertenece a una "
                  + "categoría que ya no forma parte del sistema ");   
        }else{
        categorias.add(t);
        JOptionPane.showMessageDialog(null,"Categoría agregada exitosamente");    
        }
        
    }

 
    public void update(Categoria t, String[] params) {
  //Se usa un array de 1 posición
        //Posición 0:Nombre Categoría
        
        
        Statement stmt=null;
        String nuevoNombre=params[0];
      
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt = conexion.conn.createStatement();
            String sql;
            sql="UPDATE bdpatrones.categoria SET nombreCategoria='"+
                    nuevoNombre+
                    "' WHERE idCategoria="+t.getCodCategoria();
            
    
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
        
     for(Categoria cate:categorias){
         if(cate.getCodCategoria()==t.getCodCategoria()){
             cate.setNombreCategoria(nuevoNombre);
         }
     }
     JOptionPane.showMessageDialog(null,"Categoría actualizada exitosamente");   
    }

 
   

   
    public void delete(Categoria t) {
       Statement stmt=null; 
      try{
          Conexion conexion = Conexion.getInstance();
          conexion.conectar();
          stmt = conexion.conn.createStatement();
          String sql;
          sql="UPDATE bdpatrones.categoria SET borrado=1 WHERE idCategoria="+t.getCodCategoria();
          
          stmt.executeUpdate(sql);
          
           JOptionPane.showMessageDialog(null,"Categoría borrada exitosamente");
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
      
      
      for(Categoria cat:categorias){
          if(cat.getCodCategoria()==t.getCodCategoria()){
              cat.setBorrado(true);
          }
      }
 
      
    }
    
}
