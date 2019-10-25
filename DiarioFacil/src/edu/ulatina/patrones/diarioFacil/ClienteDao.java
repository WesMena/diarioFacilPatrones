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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nvidi
 */
public class ClienteDao extends Conexion implements Dao<Cliente>{
    
    ResultSet rset;
    Statement stm;
    CallableStatement proc;
   
    public boolean login(String nombreUsuario,String password){
        boolean ok = false;
        try{
            super.conectar();
            stm = conn.createStatement();
            rset = stm.executeQuery(String.format("select * from cliente where NombreCliente =\"%s\" and PassCliente=\"%s\" and borrado != 1",nombreUsuario,password));
            while(rset.next())
                ok=true;
            
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try{
                stm.close();
                rset.close();
            }catch(SQLException e){
                System.out.println(""+e.getMessage());
            }
        }
        
        return ok;
    }
    
    @Override
    public Optional<Cliente> get(long id) {
        
        Optional<Cliente> opRe = Optional.empty();
        try{
            Cliente client = null;
            super.conectar();
            stm = conn.createStatement();
            rset = stm.executeQuery(String.format("select idCliente,CorreoCliente,PassCliente,NombreCliente,borrado,ClientePref from cliente where idCliente = %o", id));
            while(rset.next()){
                client = new Cliente(rset.getInt("idCliente"), rset.getString("CorreoCliente"), rset.getString("PassCliente"), rset.getString("NombreCliente"), rset.getBoolean("borrado"), rset.getBoolean("ClientePref"));
            }
            opRe = Optional.ofNullable(client);
        }catch(SQLException e){
            System.out.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try{
                stm.close();
                rset.close();
            }catch(SQLException e){
                System.out.println(""+e.getMessage());
            }
        }
        return opRe;
    }
    

    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList();
        try{
            super.conectar();
            stm = conn.createStatement();
            rset = stm.executeQuery("select * from cliente");
            while(rset.next()){
                clientes.add(new Cliente(rset.getInt("idCliente"),rset.getString("CorreoCliente"),rset.getString("PassCliente"),rset.getString("NombreCliente"),rset.getBoolean("borrado"),rset.getBoolean("ClientePref")));
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try {
                stm.close();
                rset.close();
            } catch (SQLException ex) {
                System.err.println(""+ex.getMessage());
            }
            
        }
        
        return clientes;
    }

    @Override
    public void save(Cliente t) {
        try{
            super.conectar();
            proc = conn.prepareCall("{Call insert_user(?,?,?)}");
            proc.setString("nombre", t.getNombreUsuario());
            proc.setString("email", t.getEmailUsuario());
            proc.setString("userPassword", t.getPassword());
            proc.execute();
        }catch(SQLException e ){
            System.err.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try{
                proc.close();
            }catch(SQLException e){
                System.err.println(""+e.getMessage());
            }
        }
    }

    @Override
    public void update(Cliente t, String[] params) {
        try{
            super.conectar();
            proc = conn.prepareCall("{Call update_user(?,?,?,?,?,?)}");
            proc.setString("nombre", t.getNombreUsuario());
            proc.setString("email", t.getEmailUsuario());
            proc.setString("userPassword", t.getPassword());
            proc.setInt("idMod", t.getId());
            proc.setBoolean("borrado", t.isIsActive());
            proc.setBoolean("isPref", t.isIsPref());
            proc.execute();
        }catch(SQLException e ){
            System.err.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try{
                proc.close();
            }catch(SQLException e){
                System.err.println(""+e.getMessage());
            }
        }
    }

    @Override
    public void delete(Cliente t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
