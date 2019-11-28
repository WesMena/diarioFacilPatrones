/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import static edu.ulatina.patrones.diarioFacil.ProductoDao.productos;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author josem
 */
public class FacturaDao implements Dao<Factura>{
    
    public static List<Factura> productos=new ArrayList<>();
    
    public FacturaDao(int idFactura){
    
                productos=new ArrayList<>();
        ResultSet rs=null;
       Statement stmt=null; 
       try{
           Conexion conexion = Conexion.getInstance();
           conexion.conectar();
           stmt= conexion.conn.createStatement();
           String sql; 
           
           
           sql="select producto.NombreProducto as Producto,producto.PrecioProducto as Precio_unitario, \n" +
                "            item.cantidad as Cantidad, \n" +
                "            item.subtotal as Monto, \n" +
                "            item.isCombo,\n" +
                "            item.producto as productID ,\n" +
                "            item.isCombo as isComboID ,\n" +
                "            orden.Cliente as cliente\n" +
                "            from Item\n" +
                "            inner join orden on orden.idOrden = item.orden\n" +
                "            inner join producto on producto.idProducto = item.producto \n" +
                "            where orden.idOrden =  "+ idFactura;
           rs=stmt.executeQuery(sql);
           while(rs.next()){
               
            String producto = rs.getString("Producto");
            double precio = rs.getDouble("Precio_unitario");
            int cantidad = rs.getInt("Cantidad");
            double monto = rs.getDouble("Monto");
            int isCombo = rs.getInt("isCombo");
            int productID = rs.getInt("productID");
            int isComboID = rs.getInt("isComboID");
            int Cliente = rs.getInt("cliente");
               

             productos.add(new Factura(producto,precio,cantidad,monto,isCombo,
             productID,isComboID,Cliente));
             
           }
       }catch(Exception e){
           e.printStackTrace();
       }
    
    }
    
        @Override
    public Optional<Factura> get(long id) {
       Optional<Factura> opFact=Optional.empty();
       for(Factura fact:productos){
           if(fact.getProductID()==id){
               opFact=Optional.of(fact);
           }
       }
       return opFact;
    }

    @Override
    public List<Factura> getAll() { 
        return productos;
    }

    @Override
    public void save(Factura t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Factura t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Factura t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
