/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nvidi
 */
public class ClienteDao implements Dao<Cliente>{
    
    ResultSet rset;
    Statement stm;
    CallableStatement proc;
    
    public List<Producto> getOrdenCoompra(int idCliente){
        List<Producto> orden = new ArrayList<>();
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("",idCliente));
            while(rset.next()){
                orden.add(new Producto());
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
        
        return orden;
    }
   
    public boolean login(String nombreUsuario,String password){
        boolean ok = false;
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select * from cliente where NombreCliente =\"%s\" and PassCliente=\"%s\" and borrado != 1",nombreUsuario,password));
            while(rset.next())
                ok=true;
            
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                stm.close();
//                rset.close();
//            }catch(SQLException e){
//                System.out.println(""+e.getMessage());
//            }
//        }
        
        return ok;
    }
    
    @Override
    public Optional<Cliente> get(long id) {
        
        Optional<Cliente> opRe = Optional.empty();
        try{
            Cliente client = null;
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select idCliente,CorreoCliente,PassCliente,NombreCliente,borrado,ClientePref from cliente where idCliente = %o", id));
            while(rset.next()){
                client = new Cliente(rset.getInt("idCliente"), rset.getString("CorreoCliente"), rset.getString("PassCliente"), rset.getString("NombreCliente"), rset.getBoolean("borrado"), rset.getBoolean("ClientePref"));
            }
            opRe = Optional.ofNullable(client);
        }catch(SQLException e){
            System.out.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                stm.close();
//                rset.close();
//            }catch(SQLException e){
//                System.out.println(""+e.getMessage());
//            }
//        }
        return opRe;
    }
    

    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList();
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery("select * from cliente");
            while(rset.next()){
                clientes.add(new Cliente(rset.getInt("idCliente"),rset.getString("CorreoCliente"),rset.getString("PassCliente"),rset.getString("NombreCliente"),rset.getBoolean("borrado"),rset.getBoolean("ClientePref")));
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try {
//                stm.close();
//                rset.close();
//            } catch (SQLException ex) {
//                System.err.println(""+ex.getMessage());
//            }
//            
//        }
        
        return clientes;
    }

    @Override
    public void save(Cliente t) {
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            proc = conexion.conn.prepareCall("{Call insert_user(?,?,?)}");
            proc.setString("nombre", t.getNombreUsuario());
            proc.setString("email", t.getEmailUsuario());
            proc.setString("userPassword", t.getPassword());
            proc.execute();
        }catch(SQLException e ){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
    }

    @Override
    public void update(Cliente t, String[] params) {
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            proc = conexion.conn.prepareCall("{Call update_user(?,?,?,?,?,?)}");
            proc.setString("nombre", t.getNombreUsuario());
            proc.setString("email", t.getEmailUsuario());
            proc.setString("userPassword", t.getPassword());
            proc.setInt("idMod", t.getId());
            proc.setBoolean("borrado", t.isIsActive());
            proc.setBoolean("isPref", t.isIsPref());
            proc.execute();
        }catch(SQLException e ){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
    }

    @Override
    public void delete(Cliente t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Productos vista cliente
    public List<Properties> getInventarioCliente(){
        List<Properties> returned = new ArrayList<>();
        try{
           Conexion conexion = Conexion.getInstance();
            conexion.conectar();
           stm= conexion.conn.createStatement();
           rset = stm.executeQuery(String.format("select producto.idProducto,producto.NombreProducto,producto.MarcaProducto,producto.PrecioProducto,producto.stockActual,categoria.nombreCategoria from producto \n" +
            "inner join categoria on producto.categoria = categoria.idCategoria\n" +
            "where producto.borrado!=1;",0));
           while(rset.next()){
               Properties temp = new Properties();
               temp.setProperty("IDProducto", String.valueOf(rset.getInt(1)));
               temp.setProperty("Producto",rset.getString(2));
               temp.setProperty("Marca",rset.getString(3));
               temp.setProperty("Precio",String.valueOf(rset.getDouble(4)));
               temp.setProperty("Disponibles", String.valueOf(rset.getInt(5)));
               temp.setProperty("Categoria", rset.getString(6));
               returned.add(temp);
           }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try {
//                stm.close();
//                rset.close();
//            } catch (SQLException ex) {
//                System.err.println(""+ex.getMessage());
//            }
//        }
        return returned;
    }
    
    
    
    public int getDiferenciaStock(int idProducto,int cantidadSolicitada){
        int diferencia=0;
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            
            String query =String.format("select  (producto.stockActual-%2$o) as Faltante  from producto \n" +
            "where producto.idProducto = %1$o;",idProducto,cantidadSolicitada);
           
            rset = stm.executeQuery(query);
            
            while(rset.next())
                diferencia = rset.getInt("Faltante");
            
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
            diferencia =-999999999;
        }
//        finally{
//            super.desconectar();
//            try {
//                stm.close();
//                rset.close();
//            } catch (SQLException ex) {
//                System.err.println(""+ex.getMessage());
//            }
//        }
        
        return diferencia;
    }
    
    public Cliente getByPassAndUser(String user,String pass){
        Cliente returned = new Cliente();
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select cliente.idCliente,cliente.NombreCliente,cliente.ClientePref from cliente \n" +
            "where cliente.PassCliente = '%s'\n" +
            "and cliente.NombreCliente='%s'", pass,user));
            while(rset.next()){
                returned.setNombreUsuario(rset.getString(2));
                returned.setIsPref(rset.getBoolean(3));
                returned.setId(rset.getInt(1));
            }
                
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try {
//                stm.close();
//                rset.close();
//            } catch (SQLException ex) {
//                System.err.println(""+ex.getMessage());
//            }
//        }
        return returned;
    }
    
    public void addItemCarrito(int solicitado,int idProducto,int idCliente){
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            proc  = conexion.conn.prepareCall("{Call insert_Carrito(?,?,?)}");
            proc.setInt("solicitado", solicitado);
            proc.setInt("idProductoIn", idProducto);
            proc.setInt("idCliente", idCliente);
            proc.execute();
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
    }
    
    public StockManager comprar(int idUser){
        StockManager returned= null;
        int outValue=-1;
        String errors = "";
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            proc = conexion.conn.prepareCall("Call finalizar_compra(?,?,?)");
            proc.setInt(1, idUser);
            proc.registerOutParameter(2,Types.VARCHAR);
            proc.registerOutParameter(3, Types.INTEGER);
            proc.execute();
            
            errors  = proc.getString(2);
            outValue = proc.getInt(3);
            returned = new StockManager(outValue,errors);
            
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
        return returned;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Clase Interna">
    public class StockManager{
      private int looped;
      private String errors;
      public StockManager(int looped,String errors){
          this.errors = errors;
          this.looped = looped;
      }
      public int getLooped(){
          return this.looped;
      }
      public String errors(){
          return this.errors;
      }
    }
    //</editor-fold>
    
    public List<Properties> getCarritoCliente(int idCliente){
        List<Properties>  returned  = new ArrayList<>();
        try{
            String producto="Producto",precio_unitario="Precio_unitario",cantidad="Cantidad",monto="Monto",isCombo="Combo";
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            String query;
            query = String.format("select producto.NombreProducto as Producto,producto.PrecioProducto as Precio_unitario,\n" +
            "item.cantidad as Cantidad,\n" +
            "item.subtotal as Monto\n," +
            "item.isCombo\n,"+
            "item.producto as productID \n,"+
            "item.isCombo as isComboID\n"+
            "from Item\n" +
            "inner join orden on orden.idOrden = item.orden\n" +
            "inner join producto on producto.idProducto = item.producto\n" +
            "where orden.cliente = %o \n" +
            "and orden.finalizada = 0", idCliente);
            stm  = conexion.conn.createStatement();
            rset =  stm.executeQuery(query);
            while(rset.next()){
                Properties prop = new Properties();
                prop.setProperty(producto, rset.getString(producto));
                prop.setProperty(precio_unitario, String.valueOf(rset.getDouble(precio_unitario)));
                prop.setProperty(cantidad, String.valueOf(rset.getInt(cantidad)));
                prop.setProperty(monto, String.valueOf(rset.getDouble(monto)));
                prop.setProperty("isCombo", String.valueOf(rset.getInt("isCombo")));
                prop.setProperty("ProductoID", String.valueOf(rset.getInt("productID")));
                prop.setProperty("isComboID", String.valueOf(rset.getInt("isComboID")));
                returned.add(prop);
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                rset.close();
//                stm.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
        return returned;
    }
    
    public void insertar_Combo_Carrito(int idCliente,int idCombo,int solicitud){
        
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            proc = conexion.conn.prepareCall("Call insertar_ComboCarrito(?,?,?,?)");
            proc.setInt(1, idCliente);
            proc.setInt(2, idCombo);
            proc.setInt(3, solicitud);
            proc.registerOutParameter(4,Types.VARCHAR);
            proc.execute();
            
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
    }
    
    public String getComboByID(int idCombo){
        String returned ="";
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select combos.NombreCombo from combos where combos.idCombo = %o", idCombo));
            while(rset.next()){
                returned = rset.getString(1);
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                stm.close();
//                rset.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
        return returned;
    }
    
    public double costo_combos(int idOrden){
       double returned =0.0;
       try{
           Conexion conexion = Conexion.getInstance();
           conexion.conectar();
           proc = conexion.conn.prepareCall("Call costo_Combos(?,?)");
           proc.setInt(1, idOrden);
           proc.registerOutParameter(2, Types.DECIMAL);
           proc.execute();
           returned = proc.getDouble(2);
       }catch(SQLException e){
           System.err.println(" "+e.getMessage());
        }
//       finally{
//            super.desconectar();
//            try{
//               proc.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
       
       
       return returned;
    }
    
    public int Orden_actual(int idCliente){
        int returned =0;
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select orden.idOrden from orden where orden.cliente = %o and orden.finalizada = 0;", idCliente));
            while(rset.next())
                returned = rset.getInt(1);
        }catch(SQLException e){
            
        }
//        finally{
//            super.desconectar();
//            try{
//                stm.close();
//                rset.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
        
        return returned;
    }

    public double precioCombo_byID(int idCombo){
        double returned=0.0;
        try{
            Conexion conexion = Conexion.getInstance();
            conexion.conectar();
            stm = conexion.conn.createStatement();
            rset = stm.executeQuery(String.format("select combos.precio from combos where combos.idCombo = %o", idCombo));
            while(rset.next()){
                returned = rset.getDouble(1);
            }
        }catch(SQLException e){
            System.err.println(" "+e.getMessage());
        }
//        finally{
//            super.desconectar();
//            try{
//                rset.close();
//                stm.close();
//            }catch(SQLException e){
//                System.err.println(""+e.getMessage());
//            }
//        }
        
        return returned;
    }
    
    public void update_or_delete_Item(int idProducto,int idCliente,int nuevacantidad){
        try{
            Conexion conexion = Conexion.getInstance();
            proc = conexion.conn.prepareCall("Call updateProductoCarrito(?,?,?)");
            proc.setInt(1, idProducto);
            proc.setInt(2, idCliente);
            proc.setInt(3, nuevacantidad);
            proc.execute();
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
    }
    
}
