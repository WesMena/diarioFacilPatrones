package edu.ulatina.patrones.diarioFacil;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nvidi
 */
public class OrdenDao implements Dao<Orden>{

    ResultSet rset;
    Statement stm;
    CallableStatement proc;
    
    
    @Override
    public Optional<Orden> get(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Orden> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Orden t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Orden t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Orden t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public double geTotalOrden(int idOrden){
        Double returned = 0.0;
        try{
            Conexion conexion = Conexion.getInstance();
            stm = conexion.conn.createStatement();
            rset  = stm.executeQuery(String.format("select orden.total from orden where orden.idOrden = %o", idOrden));
            while(rset.next()){
                returned = rset.getDouble(1);
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
        
        
        return returned;
    }
    
    public double getTotalUltimaOrden(int idCliente){
        Double returned = 0.0;
        try{
            Conexion conexion = Conexion.getInstance();
            stm = conexion.conn.createStatement();
            rset  = stm.executeQuery(String.format("select orden.total from orden where orden.cliente = %1$o \n" +
            "and orden.fechaOrden = (select max(o.fechaOrden) from orden as o where o.cliente = %1$o \n" +
            "and o.finalizada =1)\n" +
            "and orden.finalizada = 1;", idCliente));
            while(rset.next()){
                returned = rset.getDouble(1);
            }
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }
        return returned;
    }
    
    
}
