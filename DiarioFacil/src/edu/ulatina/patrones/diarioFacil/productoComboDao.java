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

//Al agregar un producto al combo, si el producto ya existe, suma cantidades,
//pero si existe y está borrado, las reescribe

//Al ver todos los productos del combo, lo que hace es traer todos y crear una
//una segunda lista solo con los que tienen el id de combo que coincide


public class productoComboDao extends Conexion implements Dao<ProductosCombo> {
public static List<ProductosCombo> prodCombo;

    public productoComboDao() {
        prodCombo=new ArrayList<>();
        ResultSet rs=null;
        Statement stmt=null;
         try{
             this.conectar();
             stmt=conn.createStatement();
             String sql; 
             sql="SELECT combo,producto,cantidad,borrado FROM productoscombo";
             rs=stmt.executeQuery(sql);
             while(rs.next()){
                 int idCombo=rs.getInt("combo");
                 int idProd=rs.getInt("producto");
                 int cantidad=rs.getInt("cantidad");
                 int borrado=rs.getByte("borrado");
                Producto prodAux=new Producto(); 
                ProductoDao daoAux=new ProductoDao();
                
                 for(Producto produ:daoAux.getAll()){
                   
                     if(produ.getId()==idProd){
                        prodAux=produ;
                      
                     }
                 }
               
                 
                 if(borrado==0){
                     ProductosCombo prodcmb=new ProductosCombo(idCombo,prodAux,cantidad,false);
                     prodCombo.add(prodcmb);
                 }else{
                      ProductosCombo prodcmb=new ProductosCombo(idCombo,prodAux,cantidad,true);
                       prodCombo.add(prodcmb);
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
    


    public Optional<ProductosCombo> get(long id) {
   Optional<ProductosCombo> opProd=Optional.empty();
   for(ProductosCombo prodCmb:prodCombo){
       if(prodCmb.getProd().getId()==id){
           opProd=Optional.of(prodCmb);
       }
   }
   //Desde afuera hay que actualizarle el id de combo
   //No confiar en la cantidad o estado de borrado
   //que sale aquí(porque solo va a 
   //tirar el último objeto que use el producto indicado)
   //usar el método de abajo como referencia para solucionar esto
   
   return opProd; 
    }
    
    public Optional<ProductosCombo> getProducto(long idCombo, long idProd){
      Optional<ProductosCombo> opProd=Optional.empty();
   for(ProductosCombo prodCmb:prodCombo){
       if(prodCmb.getProd().getId()==idProd && prodCmb.getIdCombo()==idCombo){
           opProd=Optional.of(prodCmb);
       }
   }
  
   
   return opProd;    
    }

    @Override
    public List<ProductosCombo> getAll() {
        return prodCombo;
    }

  
    public void save(ProductosCombo t) {
        Statement stmt=null; 
        boolean fallido=false; 
        //Al agregar un producto al combo, si el producto ya existe, hay que sumar cantidades,
        //pero si existe y está borrado, las reescribe, para esto una solución es
        //recorrer la lista
        //de ese combo y hacer un update, en lugar de un save(se puede hacer al 
        //detectar que falla
        
        try{
            this.conectar();
            stmt=conn.createStatement();
            String sql;
            sql="INSERT INTO bdpatrones.productoscombo VALUES("+t.getIdCombo()+
                    ","+t.getProd().getId()+","+t.getCantidad()+",0)";
            try{
                stmt.executeUpdate(sql);
            }catch(SQLException sqlE){
                fallido=true;
            }
            //combo,producto,cantidad,borrado
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
          
               
               for(ProductosCombo prod:prodCombo){
                   if(prod.getIdCombo()==t.getIdCombo() &&
                           prod.getProd().getId()==t.getProd().getId()){
                       if(prod.isBorrado()){
                           String cant;
                           cant=String.valueOf(t.getCantidad());
                           String[] reemplazo=new String[]{cant};
                           this.update(t, reemplazo);
                           for(ProductosCombo prodCmb:prodCombo)
                           {
                               if(prodCmb.getProd().getId()==t.getProd().getId()){
                                   prodCmb.setCantidad(t.getCantidad());
                               }
                           }
                          
                                   
                           //tiene que recorrer prodCombo, encontrarlo y 
                          //reemplazar la cantidad 
                          
                       }else{
                           String cant;
                           int sumaCant=t.getCantidad()+prod.getCantidad();
                           cant=String.valueOf(sumaCant);
                           String[] reemplazo=new String[]{cant};
                      
                           for(ProductosCombo prodCmb:prodCombo)
                           {
                               if(prodCmb.getProd().getId()==t.getProd().getId()){
                                   prodCmb.setCantidad(sumaCant);
                               }
                           }
                           
                           
                           this.update(t, reemplazo);
                           
                    
                       }
                       
                       
                   }
               }
           }else{
               prodCombo.add(t);
           }
    }


    public void update(ProductosCombo t, String[] params) {
      //El array solo usa la posición 0 para modificar la cantidad 
      int cantidad=Integer.parseInt(params[0]);
      t.setCantidad(cantidad);
      Statement stmt=null; 
      try{
          this.conectar();
          stmt=conn.createStatement();
          String sql="UPDATE bdpatrones.productoscombo SET cantidad="
                  +cantidad+" WHERE combo="+t.getIdCombo()+" AND producto="+
                  t.getProd().getId();
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
      for(ProductosCombo prod:prodCombo){
                   if(prod.getIdCombo()==t.getIdCombo() &&
                           prod.getProd().getId()==t.getProd().getId()){
                       
                           prod.setCantidad(cantidad);
                   }
        }
      
    }

  
    public void delete(ProductosCombo t) {
     Statement stmt=null; 
     try{ 
         this.conectar();
         stmt=conn.createStatement(); 
          String sql="UPDATE bdpatrones.productoscombo SET borrado=1"
                  +" WHERE combo="+t.getIdCombo()+" AND producto="+
                  t.getProd().getId();
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
     
        for(ProductosCombo prod:prodCombo){
                   if(prod.getIdCombo()==t.getIdCombo() &&
                           prod.getProd().getId()==t.getProd().getId()){
                           prod.setBorrado(true);
                   }
        }
     
    }
    
}
