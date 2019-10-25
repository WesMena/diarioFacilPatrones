/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.sql.ResultSet;
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
                   + "categoria,proveedor FROM producto";
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
            productos.add(new Producto(id,categoria,proveedor,nombre,stockMinimo,stockActual,
            precio,categoria));
            
             
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
    public void save(Producto t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Producto t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Producto t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
