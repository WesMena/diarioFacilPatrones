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
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nvidi
 */
public class AdminDao extends Conexion implements Dao<Administrador>{

    ResultSet rset;
    Statement stm;
    CallableStatement proc;
    
    public boolean login(String user,String password){
        boolean ok = false;
        try{
            super.conectar();
            stm = conn.createStatement();
            rset = stm.executeQuery(String.format("select*from admin where NombreAdmin = '%s' and passAdmin = '%s' and borrado != 1", user,password));
            while(rset.next())
                ok =true;
        }catch(SQLException e){
            System.err.println(""+e.getMessage());
        }finally{
            super.desconectar();
            try{
                stm.close();
                rset.close();
            }catch(SQLException e){
                System.err.println(""+e.getMessage());
            }
        }
        return ok;
    }
    
    
    @Override
    public Optional<Administrador> get(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Administrador> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Administrador t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Administrador t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Administrador t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
