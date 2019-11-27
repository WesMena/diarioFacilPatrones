/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
/**
 *
 * @author USER
 */
public class MenuProductoCombo {
Dao prodCombo=new ProductoComboDao();
   public void menu(int idCombo, boolean comboNuevo){
       
       int opcion=0; 
       int idProd=0;
       boolean invalido;
       StringBuffer s=new StringBuffer(); 
       if(comboNuevo==true){
           s.append("Ingrese una opción:\n");
           s.append("1.Agregar producto al combo \n");
           s.append("2.Ver productos del combo \n");
           s.append("3.Modificar Producto del combo \n");
           s.append("4.Borrar producto del combo \n");
           s.append("5.Completar combo \n");
           
       }else{
           s.append("Ingrese una opción:\n");
           s.append("1.Agregar producto al combo \n");
           s.append("2.Ver productos del combo \n");
           s.append("3.Modificar Producto del combo \n");
           s.append("4.Borrar producto del combo \n");
           s.append("5.Volver \n");
       }
       
       String strId;
       boolean cancelo1=false;
       do{
           do{
               invalido=false;
               try{
                   strId=JOptionPane.showInputDialog(s);
                   if(strId==null){
                       cancelo1=true;
                       break;
                   }
                opcion=Integer.parseInt(strId);
                
               }catch(NumberFormatException nfe){
                   invalido=true;
               }
           }while(invalido==true);
           
           if(cancelo1){
               break;
           }
           switch(opcion){
               case 1:
                   String strIdProd;
                    Producto prodAux=new Producto();
                   boolean existe=false;
                   boolean incorrecto=false;
                   int cantidad=0;
                   try{
                     strIdProd=JOptionPane.showInputDialog("Ingrese el id "
                             + "del producto a agregar");
                     if(strIdProd==null){
                         break;
                     }
                     idProd=Integer.parseInt(strIdProd);
                   
                    
                    
                  ProductoDao prod=new ProductoDao();
                  List<Producto> lst=prod.getAll();
                  for(Producto produ:lst){
                     
                       
                      if(produ.getCodProducto()==idProd && produ.isBorrado()==false){
                          existe=true;
                          //Poner que esto tenga en consideración 
                          //si está borrado
                          prodAux=produ;
                         
                      }
                  }
                  if(existe==false){
                    JOptionPane.showMessageDialog(null,"El id de producto ingresado "
                            + "no existe ");
                      break;    
                  }
                      
                  
                
                       
                   }catch(NumberFormatException nfe){
                       break;
                   }
                   String strCant;
                   boolean cancelo2=false;
                   do{
                       incorrecto=false;
                       try{
                           strCant=JOptionPane.showInputDialog("Ingrese la cantidad "
                             + "del producto a agregar");
                           if(strCant==null){
                               cancelo2=true;
                               break;
                           }
                           cantidad=Integer.parseInt(strCant);
                         
                       }catch(NumberFormatException nfe){
                           incorrecto=true;
                          
                       }
                       
                   }while(incorrecto==true);
                   if(cancelo2){
                       break;
                   }
                   ProductosCombo produComb=new ProductosCombo
                    (idCombo,prodAux,cantidad,false);
                   
                   prodCombo.save(produComb);
                   break;
                   
               case 2:
                  this.verProductos(idCombo);
                    break;
               case 3:
                   String strIdMod;
                   try{
                       strIdMod=JOptionPane.showInputDialog("Ingrese el id"
                              + " del producto a modificar");
                       if(strIdMod==null){
                           break;
                       }
                       idProd=Integer.parseInt(strIdMod);
            
                List<ProductosCombo> listaProductos=prodCombo.getAll();
                Optional<ProductosCombo> opProd=Optional.empty();
            
                 for(ProductosCombo prodCmb:listaProductos){
                     
                    if(prodCmb.getProd().getCodProducto()==idProd &&
                            prodCmb.getIdCombo()==idCombo){
                    opProd=Optional.of(prodCmb);
                    
                  }
   }               
               
                 
                 if(opProd.isPresent()==false ||opProd.get().isBorrado()==true){
                   JOptionPane.showMessageDialog(null,"El id ingresado no corresponde"
                               + " con ningún producto del combo");   
                   break;
                 }else{
                       Producto prod;
                     prod=opProd.get().getProd();
                     
                     int nuevaCant=0;
                     boolean formatoIncorrecto=false;
                     boolean cancelo3=false;
                     String cantMod;
                     do{
                         formatoIncorrecto=false;
                     try{
                         cantMod=JOptionPane.showInputDialog("Ingrese la nueva cantidad "
                             + "del producto \n(Si deja el espacio en "
                                   + "blanco, se mantendrá la actual).");
                         if(cantMod==null){
                             cancelo3=true;
                             break;
                         }
                         nuevaCant=Integer.parseInt(cantMod);
                       
                       }catch(NumberFormatException nfe){
                           formatoIncorrecto=true;
                           break;
                       }
                         
                     }while(formatoIncorrecto==true);
                     if(cancelo3){
                         break;
                     }
                     String comprobar=Integer.toString(nuevaCant);
                     if(comprobar.equalsIgnoreCase("0")){
                         nuevaCant=opProd.get().getCantidad();
                     }
                     
                     String cantInsertar=Integer.toString(nuevaCant);
                     
                     
                     ProductosCombo prodModifica=new ProductosCombo
                        (idCombo,prod,nuevaCant,false);
                     
                     String[]actualiza=new String[]{cantInsertar};
                     
                     prodCombo.update(prodModifica,actualiza);
                     
                 }
                
              
                  
                   }catch(NumberFormatException nfe){
                       break;
                   }
                   break;
                   
               case 4:
                   String strBorra;
                   try{
                       strBorra=JOptionPane.showInputDialog("Ingrese el id"
                              + " del producto a borrar");
                       
                       if(strBorra==null){
                           break;
                       }
                       idProd=Integer.parseInt(strBorra);
      
                List<ProductosCombo> listaProductos=prodCombo.getAll();
                Optional<ProductosCombo> opProd=Optional.empty();
                 for(ProductosCombo prodCmb:listaProductos){
                     
                    if(prodCmb.getProd().getCodProducto()==idProd &&
                            prodCmb.getIdCombo()
                            ==idCombo){
                    opProd=Optional.of(prodCmb);
                    
                  }
   }             
                   if(opProd.isPresent()==false ||opProd.get().isBorrado()==true){
                   JOptionPane.showMessageDialog(null,"El id ingresado no corresponde"
                               + " con ningún producto del combo");   
                   break;
                 }else{
                       
                         
                 Producto prod;
                     prod=opProd.get().getProd();
                     ProductosCombo prodBorra=new ProductosCombo
                        (idCombo,prod,0,false);
                     
                     prodCombo.delete(prodBorra);
                     
                      
                   }  
                   
                   
                   }catch(NumberFormatException nfe){
                       break;
                   }
                   break;
                   
               case 5:
                   break;
           }      
       }while(opcion!=5);
   }
   
   public void verProductos(int idCombo){
        List<ProductosCombo> listaAux=new ArrayList<>();
                   List<ProductosCombo> listaOriginal=new ArrayList<>();
                   ProductoComboDao daoPrueba=new ProductoComboDao();
                   
                           listaOriginal=daoPrueba.getAll();
                   for(ProductosCombo pro:((ProductoComboDao)daoPrueba).getAll()){
                       if(pro.getIdCombo()==idCombo && pro.isBorrado()==false){
                           
                          listaAux.add(pro);
                          
                       }
                   }
                  
                   String productos="Lista de productos \n";
                   for(ProductosCombo prodEnLista:listaAux){
                       productos=productos+"Código: "+prodEnLista.getProd().getCodProducto()+
                               " Nombre: "+prodEnLista.getProd().getNombreProd()+
                               " Cantidad:"+prodEnLista.getCantidad()+"\n";
                   }
                   
                   if(listaAux.isEmpty()){
                      productos="El combo no contiene ningún producto";
                   }
                    JOptionPane.showMessageDialog(null,productos);
                    
   }
}
