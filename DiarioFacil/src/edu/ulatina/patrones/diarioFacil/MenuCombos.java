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
public class MenuCombos {
    public static Dao combo; 
    public void menu(){
        combo=new ComboDao();
        int opcion=0; 
        int id=0; 
        boolean invalido=false; 
        
        
        StringBuilder s=new StringBuilder(); 
        s.append("Ingrese una opción:\n");
        s.append("1.Nuevo combo \n"); 
        s.append("2.Ver combos \n");
        s.append("3.Ver productos por combo \n");
        s.append("4.Modificar combo \n");
        s.append("5.Borrar combo \n");
        s.append("6.Volver \n");
        
        String strOp; 
        boolean cancelo1=false; 
      do{
          do{
              invalido=false; 
              try{
                  strOp=JOptionPane.showInputDialog(s);
                  if(strOp==null){
                      cancelo1=true;
                      break;
                  }
                  opcion=Integer.parseInt(strOp);
               
              }catch(NumberFormatException nfe){
                  invalido=true; 
              }
                      
          }while(invalido==true);
          if(cancelo1){
              break;
          }
          switch(opcion){
              case 1: 
                 String strId; 
                 boolean repetido=false; 
                 try{
                     strId=JOptionPane.showInputDialog("Ingrese el id "
                             + "del combo");
                     if(strId==null){
                         break;
                     }
                     
                     id=Integer.parseInt(strId);
                 
                    List<ArmaCombos> lst=combo.getAll();
                    for(ArmaCombos comboAux:lst){
                        if(comboAux.getId()==id){
                            repetido=true;
                        }
                    }
                    if(repetido==true){
                      JOptionPane.showMessageDialog(null,"El id ingresado ya existe");
                      break;      
                    }
                 }catch(NumberFormatException nfe){
                     break;
                 }
                 
                 String nombre=JOptionPane.showInputDialog("Ingrese el nombre del "
                           + "combo ");
                 if(nombre==null){
                     break;
                 }
                 ContenidosCombo caracCombo=new ContenidosCombo
                    (id,nombre,false,false);
                 
                 ComboNuevoFactory comboN=new ComboNuevoFactory(caracCombo);
                 
                 ArmaCombos comboNuevo=new ArmaCombos(comboN);
                 
                 combo.save(comboNuevo);
                 
                 MenuProductoCombo menuProdu=new MenuProductoCombo();
                 menuProdu.menu(id,true);
                 
                 boolean precioInvalido=false;
                 double precioCombo;
                 String strPrecio;
                 boolean cancelo2=false;
                 do{
                     precioInvalido=false; 
                     try{
                    strPrecio=JOptionPane.showInputDialog("Ingrese el precio "
                             + "del combo");
                    if(strPrecio==null){
                        cancelo2=true;
                        break;
                    }
                    precioCombo=Double.parseDouble(strPrecio);
                          
                     
                     String precioStr=Double.toString(precioCombo);
                     String nombreAux=comboNuevo.getNombre();
                     boolean activado=comboNuevo.isActivado();
                     
                     if(activado==true){
                       String[] actualiza={nombreAux,precioStr,"1"};  
                        combo.update(comboNuevo, actualiza);
                     }else{
                        String[] actualiza={nombreAux,precioStr,"0"};    
                         combo.update(comboNuevo, actualiza);
                     }
                         
                     }catch(Exception e){
                        precioInvalido=true; 
                     }
                        
                     
                 }while(precioInvalido==true);
                 
              if(cancelo2){
                  break;
              }
               
                  break;
                  
              case 2: 
                  //Da un listado de los combos activados, con sus códigos, nombres y precios 
                   List<ArmaCombos> lst=combo.getAll();
                  String combos="Lista de combos \n"; 
                  boolean entro=false;
                  String estado;
                  for(ArmaCombos cmb:lst){
                      if(cmb.isBorrado()==false){
                          
                          if(cmb.isActivado()==true){
                              estado="activado";
                          }else{
                              estado="desactivado";
                          }
                          entro=true;
                      combos=combos+"Código: "+cmb.getId()+
                              " Nombre: "+cmb.getNombre()+
                              " Precio: "+cmb.getPrecio()+" Estado: "+estado+" \n";    
                      
                      
                      }
                          
                      
                      
                      
                  }
                  
                  if(lst.isEmpty() || entro==false){
                      combos="No hay ningún combo registrado";
                  }
                   JOptionPane.showMessageDialog(null,combos);
                  
                  break;
                  
                  
              case 3: 
                  MenuProductoCombo mpc=new MenuProductoCombo();
                   try{
             String strMod;          
             int idMod;
             strMod=JOptionPane.showInputDialog("Ingrese el id del "
                               + "combo a buscar");
             if(strMod==null){
                 break;
             }
             idMod=Integer.parseInt(strMod);
               
                       
                       Optional<ArmaCombos> opComb=combo.get(idMod);
                       
                       if(opComb.isPresent()==false){
                        JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                        
                       }else{
                          mpc.verProductos(idMod);  
                       }
                   }catch(NumberFormatException nfe){
                       break;
                   }
                       
           
                 
              break; 
              
              case 4: 
                  int opModifica=0; 
                  boolean opInvalida;
                  int idComboMod=0;
                  
                  String nuevoNombre;
                  double nuevoPrecio;
                  StringBuffer strModifica=new StringBuffer(); 
                  String strIdMod;
                  try{
                      strIdMod=JOptionPane.showInputDialog
                                    ("Ingrese el código del combo");
                      if(strIdMod==null){
                          break;
                      }
                      idComboMod=Integer.parseInt(strIdMod);
        
                      List<ArmaCombos> listaModifica=combo.getAll();
                     
                     Optional<ArmaCombos> opCombo=Optional.empty();
                     
                     for(ArmaCombos armCmb:listaModifica){
                         if(armCmb.getId()==idComboMod){
                             opCombo=Optional.of(armCmb);
                         }
                     }
                     
                    
                     if(opCombo.isPresent()==false ||opCombo.get().isBorrado()==true){
                      JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                      break;
                     }else{
                        
                          
                          ArmaCombos armaModifica=opCombo.get();
                          
                          
                      strModifica.append("Ingrese una opción:\n");
                  strModifica.append("1.Cambiar nombre del combo \n");
                  strModifica.append("2.Cambiar precio del combo \n");
                  strModifica.append("3.Editar productos del combo \n");
                  strModifica.append("4.Activar o desactivar el combo \n");
                  strModifica.append("5.Volver");
                  
                  boolean cancelo4=false;
                  String opMod;
                   do{
                       do{
                        opInvalida=false; 
                        try{
                            opMod=JOptionPane.showInputDialog
                                    (strModifica);
                            if(opMod==null){
                                cancelo4=true;
                                break;
                            }
                            opModifica=Integer.parseInt(opMod);
                       
                            
                        }catch(NumberFormatException nfe){
                            opInvalida=true;
                        }
                       }while(opInvalida==true);
                       if(cancelo4){
                           break;
                       }
                   switch(opModifica){
                       case 1:
                           nuevoNombre=JOptionPane.showInputDialog("Ingrese el nuevo nombre "
                             + "del combo");
                           if(nuevoNombre==null){
                               break;
                           }
                           if(nuevoNombre.equalsIgnoreCase("")){
                               nuevoNombre=armaModifica.getNombre();
                           }
                           String precioNom=Double.toString(armaModifica.getPrecio());
                           
                           String[] actualizaNom={nuevoNombre,precioNom,"1"};
                           combo.update(armaModifica, actualizaNom);
                           
                           
                           break;
                           
                       case 2:
                    
                           boolean cancelo5=false;
                           String strPrecioMod;
                 do{
                     precioInvalido=false; 
                     try{
                    strPrecioMod=JOptionPane.showInputDialog("Ingrese el nuevo precio "
                             + "del combo");
                    if(strPrecioMod==null){
                        cancelo5=true;
                        break;
                    }
                    nuevoPrecio=Double.parseDouble(strPrecioMod);
                  
                          
                          String precioAux=Double.toString(nuevoPrecio);
                          if(precioAux.equalsIgnoreCase("")){
                              nuevoPrecio=armaModifica.getPrecio();
                              precioAux=Double.toString(nuevoPrecio);
                          }
                          //Si se deja en blanco, deja lo de antes
                         
                         armaModifica.setPrecio(nuevoPrecio);
                     String precioStr=Double.toString(nuevoPrecio);
                     String nombreAux=armaModifica.getNombre();
                     boolean activado=armaModifica.isActivado();
                     
                     if(activado==true){
                       String[] actualiza={nombreAux,precioStr,"1"};  
                        combo.update(armaModifica, actualiza);
                     }else{
                        String[] actualiza={nombreAux,precioStr,"0"};    
                         combo.update(armaModifica, actualiza);
                     }
                         
                     }catch(Exception e){
                        precioInvalido=true; 
                     }
                        
                     
                 }while(precioInvalido==true);    
                        if(cancelo5)   {
                            break;
                        }
                       
                       break;    
                       
                       case 3:
                           MenuProductoCombo menuModifica=new MenuProductoCombo();
                           menuModifica.menu(armaModifica.getId(),false);
                           break;
                      
                       case 4:
                           String strEstado;
                           int estadoNuevo;
                           String estadoActual="";
                           try{
                               if(armaModifica.isActivado()==true){
                                   estadoActual="Activado";
                               }else{
                                   estadoActual="Desactivado";
                               }
                               strEstado=JOptionPane.showInputDialog("Digite 1 para activar el  "
                             + "combo o 0 para desactivarlo \n"+"Estado actual: "+estadoActual);
                               if(strEstado==null){
                                   break;
                               }
                               estadoNuevo=Integer.parseInt(strEstado);
                              
                             
                               
                               
                           }catch(NumberFormatException nfe){
                               break;
                           }
                           if(estadoNuevo!=1 && estadoNuevo!=0){
                               break;
                           }
                           String precioEstado=Double.toString(armaModifica.getPrecio());
                           String nombreEstado=armaModifica.getNombre();
                           if(estadoNuevo==1){
                               String[] actualizaEstado={nombreEstado,precioEstado,"1"};
                               combo.update(armaModifica, actualizaEstado);
                           }else{
                               String[] actualizaEstado={nombreEstado,precioEstado,"0"};
                                combo.update(armaModifica, actualizaEstado);
                           }
                           
                       break;    
                   }   
                       
                       
                   }while(opModifica!=5);
                   
                  
                         
                         
                     }
                  }catch(NumberFormatException nfe){
                      break;
                  }
                  
                  
                  //Pide el id del combo antes(para armarlo)
                  

                  
                  //Tira un submenú con las opciones 
                  //Cambiar nombre de combo
                  //Cambiar precio del combo 
                  //Editar productos del combo(borrar, agregar, cambiar cantidades)
                  //(reciclar el código del case 1)
                  //Activar o desactivar el combo
                break; 
                
              case 5: 
                  String strBorra;
                  int idComboBorra=0;
                try{
                    strBorra=JOptionPane.showInputDialog
                                    ("Ingrese el código del combo");
                    if(strBorra==null){
                        break;
                    }
                    idComboBorra=Integer.parseInt(strBorra);
                   
                      List<ArmaCombos> listaBorra=combo.getAll();
                     
                     Optional<ArmaCombos> opCombo=Optional.empty();
                     
                     for(ArmaCombos armCmb:listaBorra){
                         if(armCmb.getId()==idComboBorra){
                             opCombo=Optional.of(armCmb);
                         }
                     } 
                      
                      
                 
                     if(opCombo.isPresent()==false ||opCombo.get().isBorrado()==true){
                      JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                      break;
                     }else{
                     
                          ArmaCombos armaBorra=opCombo.get();   
                          combo.delete(armaBorra);
                          
                     }  
                }catch(NumberFormatException nfe){
                    break;
                }
                  
                break; 
                
              case 6: 
         
               
                break; 
                 
                  
                  
                  
                  
          }
      }while(opcion!=6); 
    }
    public void verProductos(){
         try{
             int id;
                       id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del "
                               + "combo a buscar")); 
                       
                       Optional<ArmaCombos> opComb=combo.get(id);
                       
                       if(opComb.isPresent()==false){
                        JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                        
                       }else{
                           String cmbId="";
                           
                           cmbId="Productos del combo "
                                   +opComb.get().getNombre()+"\n";
                           
                           
                           
                           
                           Dao prodCombo=new ProductoComboDao();
                      
                          List<ProductosCombo> listaAux=new ArrayList<>();
                   List<ProductosCombo> listaOriginal=prodCombo.getAll();
                   for(ProductosCombo pro:listaOriginal){
                       if(pro.getIdCombo()==id && pro.isBorrado()==false){
                           
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
                      
                            
                       
                    }catch(NumberFormatException nfe){

                    
                    }
    }
}
