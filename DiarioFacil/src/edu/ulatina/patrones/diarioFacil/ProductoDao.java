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
public class ProductoDao extends Conexion implements Dao<Producto> {
    public static List<Producto> productos=new ArrayList<>();

    public ProductoDao() {
         
    }

    @Override
    public Optional<Producto> get(long id) {
        Optional<Producto> producto = Optional.empty();
        List<Producto> lstProductos = new ArrayList<>();
        lstProductos = this.getAll();
        
        ResultSet rs=null;
       Statement stmt=null; 
       
           for (Producto p:lstProductos ){
           
            if  (id==p.getId()) {
                
                producto= Optional.of(p);
                
            }
        }
           
            
             
       
        return producto;
    }

    @Override
    public List<Producto> getAll() {
        ResultSet rs=null;
       Statement stmt=null; 
       try{
           this.conectar();
           stmt=conn.createStatement();
           String sql; 
           
           
           sql="SELECT idProducto,NombreProducto, MarcaProducto,"
                   + "PrecioProducto,stockActual,stockMinimo,"
                   + "categoria,proveedor,borrado FROM producto";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
               
               
             int id=rs.getInt("idProducto");
             
             String nombre=rs.getString("NombreProducto");
             String marca=rs.getString("MarcaProducto");
             double precio=rs.getDouble("PrecioProducto");
             int stockActual=rs.getInt("stockActual");
             int stockMinimo=rs.getInt("stockMinimo");
             int categoria=rs.getInt("categoria");
             int proveedor=rs.getInt("proveedor");
             int borrado=rs.getByte("borrado");
             boolean estadoBorrado;
             if(borrado==1){
                 estadoBorrado=true;
             }else{
                 estadoBorrado=false;
             }
             
            productos.add(new Producto(id,categoria,proveedor,nombre,stockMinimo,stockActual,
            precio,categoria,estadoBorrado));
            
             
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           try{
               rs.close();
               stmt.close();
               this.desconectar();
           }catch(Exception e){
               e.printStackTrace();
           }
       }
        
        return productos;
    }

    @Override
    public void save(Producto producto) {
       Statement stmt=null; 
        try{
            this.conectar();
            stmt=conn.createStatement();
            String sql; 
            sql="INSERT INTO bdpatrones.producto (`NombreProducto`,`PrecioProducto`,`nombreCategoria`,`NombreProveedor` , `Promocion`,`stockActual`)VALUES('"+
                    producto.getNombreProd()+"','"+producto.getPrecio()+"','"+producto.getNombreCategoria()+"','"+producto.getNombreProveedor()
                    +"','"+producto.getPromocion()+"','"+producto.getStockActual();
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                stmt.close();
                this.desconectar();
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Producto producto, String[] params) {
       Statement stmt=null;
        try{
            this.conectar();
            stmt=conn.createStatement();
            String sql;
            sql="UPDATE bdpatrones.producto SET NombreProducto='"+
                    producto.getNombreProd()+"',PrecioProducto='"+producto.getPrecio()+
                    "',stockActual='"+producto.getStockActual()+
                    "' WHERE idProducto="+producto.getId();
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                stmt.close();
                this.desconectar();
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Producto t) {
        Statement stmt=null; 
      try{
          this.conectar();
          stmt=conn.createStatement();
          String sql;
            sql="UPDATE bdpatrones.producto SET borrado="+
                    "1 WHERE idProducto="+t.getId();
          stmt.executeUpdate(sql);
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          try{
              stmt.close();
              this.desconectar();
              
          }catch(Exception e){
              e.printStackTrace();
          }
      }
    }
}
