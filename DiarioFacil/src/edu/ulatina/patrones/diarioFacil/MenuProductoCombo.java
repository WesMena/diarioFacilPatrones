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

   public void menu(int idCombo, boolean comboNuevo){
       Dao prodCombo=new productoComboDao();
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
       
       do{
           do{
               invalido=false;
               try{
                opcion=Integer.parseInt(JOptionPane.showInputDialog(s));
                
               }catch(NumberFormatException nfe){
                   invalido=true;
               }
           }while(invalido==true);
           
           switch(opcion){
               case 1:
                    Producto prodAux=new Producto();
                   boolean existe=false;
                   boolean incorrecto=false;
                   int cantidad=0;
                   try{
                     
                    idProd=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id "
                             + "del producto a agregar"));
                    
                    
                  ProductoDao prod=new ProductoDao();
                  List<Producto> lst=prod.getAll();
                  for(Producto produ:lst){
                      
                       
                      if(produ.getCodProducto()==idProd){
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
                   do{
                       incorrecto=false;
                       try{
                           cantidad=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad "
                             + "del producto a agregar"));
                       }catch(NumberFormatException nfe){
                           incorrecto=true;
                           break;
                       }
                       
                   }while(incorrecto==true);
                   
                   ProductosCombo produComb=new ProductosCombo
                    (idCombo,prodAux,cantidad,false);
                   
                   prodCombo.save(produComb);
                   break;
                   
               case 2:
                   List<ProductosCombo> listaAux=new ArrayList<>();
                   List<ProductosCombo> listaOriginal=prodCombo.getAll();
                   for(ProductosCombo pro:listaOriginal){
                       if(pro.getIdCombo()==idCombo && pro.isBorrado()==false){
                           
                          listaAux.add(pro);
                          
                       }
                   }
                   
                   String productos="Lista de productos \n";
                   for(ProductosCombo prodEnLista:listaAux){
                       productos="Código: "+prodEnLista.getProd().getId()+
                               " Nombre: "+prodEnLista.getProd().getNombreProd()+
                               " Cantidad:"+prodEnLista.getCantidad()+"\n";
                   }
                   
                   if(listaAux.isEmpty()){
                      productos="El combo no contiene ningún producto";
                   }
                    JOptionPane.showMessageDialog(null,productos);
                    break;
                    
               case 3:
                   try{
                     idProd=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id"
                              + " del producto a modificar")); 
                List<ProductosCombo> listaProductos=prodCombo.getAll();
                Optional<ProductosCombo> opProd=Optional.empty();
                 for(ProductosCombo prodCmb:listaProductos){
                     
                    if(prodCmb.getProd().getId()==idProd &&
                            prodCmb.getIdCombo()
                            ==idCombo){
                    opProd=Optional.of(prodCmb);
                    
                  }
   }               
                 Producto prod;
                     prod=opProd.get().getProd();
                 
                 if(opProd.isPresent()==false ||opProd.get().isBorrado()==true){
                   JOptionPane.showMessageDialog(null,"El id ingresado no corresponde"
                               + " con ningún producto del combo");   
                   break;
                 }else{
                     int nuevaCant=0;
                     boolean formatoIncorrecto=false;
                     do{
                         formatoIncorrecto=false;
                     try{
                           nuevaCant=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad "
                             + "del producto \n(Si deja el espacio en "
                                   + "blanco, se mantendrá la actual)."));
                       }catch(NumberFormatException nfe){
                           formatoIncorrecto=true;
                           break;
                       }
                         
                     }while(formatoIncorrecto==true);
                     
                     String comprobar=Integer.toString(nuevaCant);
                     if(comprobar.equalsIgnoreCase("")){
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
                   try{
                       idProd=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id"
                              + " del producto a borrar")); 
                List<ProductosCombo> listaProductos=prodCombo.getAll();
                Optional<ProductosCombo> opProd=Optional.empty();
                 for(ProductosCombo prodCmb:listaProductos){
                     
                    if(prodCmb.getProd().getId()==idProd &&
                            prodCmb.getIdCombo()
                            ==idCombo){
                    opProd=Optional.of(prodCmb);
                    
                  }
   }               
                 Producto prod;
                     prod=opProd.get().getProd();
                   if(opProd.isPresent()==false ||opProd.get().isBorrado()==true){
                   JOptionPane.showMessageDialog(null,"El id ingresado no corresponde"
                               + " con ningún producto del combo");   
                   break;
                 }else{
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
}
