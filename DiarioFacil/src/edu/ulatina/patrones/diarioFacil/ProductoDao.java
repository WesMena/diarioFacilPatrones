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
public class ProductoDao implements Dao<Producto> {
    public static List<Producto> productos=new ArrayList<>();

    public ProductoDao() {
        productos=new ArrayList<>();
        ResultSet rs=null;
       Statement stmt=null; 
       try{
           Conexion conexion = Conexion.getInstance();
           conexion.conectar();
           stmt= conexion.conn.createStatement();
           String sql; 
           
           
           sql="SELECT idProducto,NombreProducto,"+"MarcaProducto,"
                   + "PrecioProducto,stockActual,stockMinimo,"
                   + "categoria,proveedor,borrado,promo FROM producto";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
               
               
             int id=rs.getInt("idProducto");
             
             String nombre=rs.getString("NombreProducto");
             
             double precio=rs.getDouble("PrecioProducto");
             int stockActual=rs.getInt("stockActual");
             int stockMinimo=rs.getInt("stockMinimo");
             int categoria=rs.getInt("categoria");
             int proveedor=rs.getInt("proveedor");
             int borrado=rs.getByte("borrado");
             int promo=rs.getByte("promo");
             String marca=rs.getString("MarcaProducto");
             boolean estadoBorrado;
             boolean estadoPromo;
             if(promo==1){
                 estadoPromo=true;
             }else{
                 estadoPromo=false;
             }
             
             if(borrado==1){
                 estadoBorrado=true;
             }else{
                 estadoBorrado=false;
             }
             productos.add(new Producto(proveedor,id,nombre,stockMinimo,stockActual,
             precio,categoria,estadoBorrado,estadoPromo,marca));

            
             
           }
       }catch(Exception e){
           e.printStackTrace();
       }  
    }

    @Override
    public Optional<Producto> get(long id) {
       Optional<Producto> opProducto=Optional.empty();
       for(Producto prod:productos){
           if(prod.getCodProducto()==id){
               opProducto=Optional.of(prod);
           }
       }
       return opProducto;
    }

    @Override
    public List<Producto> getAll() { 
        return productos;
    }

    @Override
    public void save(Producto producto) {
        
        
        
        
        
       Statement stmt=null; 
       boolean fallido=false;
       
       
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt= conexion.conn.createStatement();
            String sql; 
            sql="INSERT INTO bdpatrones.producto VALUES("+
                    producto.getCodProducto()+",'"+producto.getNombreProd()+"','"
                    +producto.getMarca()+"',"+producto.getPrecio()+","+
                    producto.getStockActual()+","+
                    producto.getStockMinimo()+","+
                    producto.getCodCategoria()+","+
                    producto.getCodProveedor()+",0,0)";
            
                    
           // INSERT into bdpatrones.producto values(4,"lays","pepsi",420,20,10,3,2,0,0);        
                    
           try{
            stmt.executeUpdate(sql);  
            
           }catch(SQLException sqlE){
               sqlE.printStackTrace();
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
                  + "producto que ya no forma parte del sistema ");   
}else{
    productos.add(producto);
    JOptionPane.showMessageDialog(null,"Producto agregado exitosamente"); 
}
    }

    @Override
    public void update(Producto producto, String[] params) {
       Statement stmt=null;
       
       int estadoPromo=0;
       if(producto.isPromocion()){
           estadoPromo=1;
       }
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stmt= conexion.conn.createStatement();
            String sql;
            sql="UPDATE bdpatrones.producto SET NombreProducto='"+
                    producto.getNombreProd()+"',PrecioProducto="+producto.getPrecio()+
                    ",stockActual="+producto.getStockActual()+",stockMinimo="+producto.getStockMinimo()
                    +",promo="+estadoPromo+
                    " WHERE idProducto="+producto.getCodProducto();
            
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

for(Producto prodModi:productos){
    if(prodModi.getCodProducto()==producto.getCodProducto()){
        prodModi.setNombreProd(producto.getNombreProd());
        prodModi.setPrecio(producto.getPrecio());
        prodModi.setPromocion(producto.isBorrado());
        prodModi.setStockActual(producto.getStockActual());
        prodModi.setStockMinimo(producto.getStockMinimo());
        prodModi.setPromocion(producto.isPromocion());
       
    }
}
    }

    @Override
    public void delete(Producto t) {
        Statement stmt=null; 
      try{
          Conexion conexion = Conexion.getInstance();
          conexion.conectar();
          stmt= conexion.conn.createStatement();
          String sql;
            sql="UPDATE bdpatrones.producto SET borrado="+
                    "1 WHERE idProducto="+t.getCodProducto();
          stmt.executeUpdate(sql);
      }catch(Exception e){
          e.printStackTrace();
      }
      
      for(Producto prodBorra:productos){
          if(prodBorra.getCodProducto()==t.getCodProducto()){
              prodBorra.setBorrado(true);
              JOptionPane.showMessageDialog(null,"El producto ha sido borrado");
          }
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
    }
}
