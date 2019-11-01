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
       ResultSet rs2=null;
       Statement stmt2=null;
       ProductoDao prod=new ProductoDao();
       
       try{
           this.conectar();
           stmt=conn.createStatement();
           String sql; 
           sql="SELECT idCombo,NombreCombo,activado,borrado,precio FROM combos";
           rs=stmt.executeQuery(sql);
           while(rs.next()){
             List<ProductosCombo> productosCombo=new ArrayList<>();
             
             
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
               sql="SELECT producto,cantidad,borrado FROM productoscombo WHERE "
                       + "combo="+id;
               rs2=stmt2.executeQuery(sql);
               while(rs2.next()){
                   int idProd=rs2.getInt("producto");
                   int cantidad=rs2.getInt("cantidad");
                   int prodBorrado=rs2.getByte("borrado");
                   if(prodBorrado==0){
                       for(Producto produ:prod.getAll()){
                           if(produ.getId()==idProd){
                               ProductosCombo item=new ProductosCombo(id,produ,cantidad);
                               productosCombo.add(item);
                           }
                       }
                   }
               }
              
               ContenidosCombo rellenoCombo=new ContenidosCombo(id,nombre,precio,
               boolActivo,false,productosCombo);
               
               ComboFactory comboAgrega=new ComboNuevoFactory(rellenoCombo);
               ArmaCombos creador=new ArmaCombos(comboAgrega);
               combos.add(creador);
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
            if(combitos.id==id ){
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
           
            for(ProductosCombo prodCom:comboGuarda.productos){
                if(prodCom.borrado==false){
                try{
                    this.conectar();
                    stmt=conn.createStatement();
                    String sql2;
                    sql2="INSERT INTO bdpatrones.productoscombo VALUES("+comboGuarda.id+
                            ","+prodCom.getProd().getId()+","+prodCom.cantidad+",0)";
                    
                 try{
                     stmt.executeUpdate(sql2);
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
                    
                    
                }
                
            }
            combos.add(comboGuarda);
             JOptionPane.showMessageDialog(null,"Combo agregado exitosamente");     
        }
        
    }

 
    public void update(ArmaCombos combo, String[] params) {
       //El array funciona así
       //0:nombre del combo
       //1:Precio del combo
       //2:Combo activado o no(enviar 0 o 1)
       //3:id del producto del combo que sufrió modificaciones
       //(si no se cambia ninguno,mandarle un número negativo)
       String nuevoNombre=params[0];
       double nuevoPrecio=Double.parseDouble(params[1]);
       int activado=Integer.parseInt(params[2]);
       int productoModificar=Integer.parseInt(params[3]);
       
       
       //Envía los datos de parámetro al objeto armaCombo
       combo.setNombre(nuevoNombre);
       combo.setPrecio(nuevoPrecio);
       
       if(activado==1){
       combo.setActivado(true);      
       }else{
       combo.setActivado(false);
  
       }
     
       
       
       Statement stmt=null; 
       
       //Hay 2 queries, la primera modifica los datos del 
       //combo, la segunda cambia los datos de un producto del combo
       
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
       
       if(productoModificar>=0){
           
           
           try{
             ProductosCombo prodModificado=new ProductosCombo(); 
               //Recorre la lista de productos del combo y almacena el 
               //que fue modificado
               int borrado=0;
               for(ProductosCombo item:combo.getProductos()){
                   if(productoModificar==item.getProd().getId()){
                       prodModificado=item;
                        
                        if(prodModificado.isBorrado()==true){
                            borrado=1;
                         }else{
                            borrado=0;
                        }
                   }
               }
                   
              int cantidad=prodModificado.getCantidad();
               
               
               this.conectar();
               stmt=conn.createStatement();
               String sql2;
               sql2="UPDATE bdpatrones.productoscombo SET cantidad="+
                       cantidad+",borrado="+borrado+"WHERE combo="+
                       prodModificado.getIdCombo()+" AND producto="+productoModificar;
               stmt.executeUpdate(sql2);
               
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
       
       //Recorre la lista de combos y reemplaza el que tenga el mismo id
       for(ArmaCombos cmb:combos){
           if(cmb.getId()==combo.getId()){
               cmb=combo;
           }
       }
     
       
 

       
       
       
      
       
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
 ComboExistenteFactory comboF=new ComboExistenteFactory(combo.getId());
 ArmaCombos comboAux=new ArmaCombos(comboF); 
 
 for(ArmaCombos c:combos){
    if(c.getId()==combo.getId()){
        comboAux=c;
        
    }
 }
  combos.remove(comboAux);
      
    }

   
 
  
    
}
