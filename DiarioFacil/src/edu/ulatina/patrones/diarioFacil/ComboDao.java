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
public class ComboDao extends Conexion implements Dao<ArmaCombos > {
 public static List<ArmaCombos> combos=new ArrayList<>();

    public ComboDao() {
           ResultSet rs=null;
       Statement stmt=null; 
    
       
       try{
           this.conectar();
           stmt=conn.createStatement();
           String sql; 
           sql="SELECT idCombo,NombreCombo,activado,borrado,precio FROM combos";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
             
             
             
             int id=rs.getInt("idCombo");
             String nombre=rs.getString("NombreCombo");
             int activado=rs.getByte("activado");
             boolean boolActivo;
             
             if(activado==0){
                 boolActivo=true;
             }else{
                 boolActivo=false;
             }
             
             
             
             
             int borrado=rs.getByte("borrado");
             
             
             double precio=rs.getDouble("precio");
             
            
             if(borrado==0){
                 ContenidosCombo rellenoCombo=new ContenidosCombo(id,nombre,precio,boolActivo
                    ,false);
             ComboFactory comboAgrega=new ComboNuevoFactory(rellenoCombo);
             }else{
              ContenidosCombo rellenoCombo=new ContenidosCombo(id,nombre,precio,boolActivo
                    ,true);   
              ComboFactory comboAgrega=new ComboNuevoFactory(rellenoCombo);
             }
             
             
             
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
        
        
    }

   
    public Optional<ArmaCombos> get(long id) {
        
        
        Optional<ArmaCombos> opCombo=Optional.empty();
        for(ArmaCombos combitos:combos){
            if(combitos.getId()==id ){
                opCombo=Optional.of(combitos);
            }
        }
        
        
        return opCombo; 
    }

    public List<ArmaCombos> getAll() {
        return combos; 
    }

    public void save(ArmaCombos comboGuarda) {
        Statement stmt=null; 
        boolean fallido=false; 

        try{
            this.conectar();
            stmt=conn.createStatement();
            String sql;
            
            
            sql="INSERT INTO bdpatrones.combos VALUES("+
                    comboGuarda.id+",'"+comboGuarda.nombre+"',"+
                    "0,0,"+comboGuarda.precio+")";
            try{
                stmt.executeUpdate(sql);
                
            }catch(SQLException sqlE){
                fallido=true;
            }
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
        
        if(fallido){
            JOptionPane.showMessageDialog(null,"El id ingresado pertenece a un "
                  + "combo que ya no forma parte del sistema ");  
        }else{
         
            combos.add(comboGuarda);
            
        }
        
    }

 
    public void update(ArmaCombos combo, String[] params) {
       //El array funciona así
       //0:nombre del combo
       //1:Precio del combo
       //2:Combo activado o no(enviar 0 o 1)
      
       String nuevoNombre=params[0];
       double nuevoPrecio=Double.parseDouble(params[1]);
       int activado=Integer.parseInt(params[2]);
      
       
       
       //Envía los datos de parámetro al objeto armaCombo
       combo.setNombre(nuevoNombre);
       combo.setPrecio(nuevoPrecio);
       
       if(activado==1){
       combo.setActivado(true);      
       }else{
       combo.setActivado(false);
  
       }
     
       
       
       Statement stmt=null; 
       
       
       try{
           this.conectar();
           stmt=conn.createStatement();
           String sql; 
          sql="UPDATE bdpatrones.combos SET NombreCombo='"+nuevoNombre+
                  "',activado="+activado+",precio="+nuevoPrecio+"WHERE"
                  + "idCombo="+combo.getId();
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
      for(ArmaCombos com:combos){
          if(com.getId()==combo.getId()){
                com=combo;
          }
      }
      JOptionPane.showMessageDialog(null,"Combo actualizado exitosamente"); 
    
     
      
    }

 
    public void delete(ArmaCombos combo) {
      Statement stmt=null; 
      try{
          this.conectar();
          stmt=conn.createStatement();
          String sql; 
          sql="UPDATE bdpatrones.combos SET borrado=1 WHERE idProveedor="+
                  combo.getId();
          stmt.executeUpdate(sql);
          JOptionPane.showMessageDialog(null,"Combo borrado exitosamente");
          
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

 
 for(ArmaCombos c:combos){
    if(c.getId()==combo.getId()){
        c.setBorrado(true);
        
    }
 }

      
    }

   
 
  
    
}
