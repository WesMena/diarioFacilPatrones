/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
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
                 boolean repetido=false; 
                 try{
                    id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id "
                             + "del combo"));    
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
                 
                 ContenidosCombo caracCombo=new ContenidosCombo
                    (id,nombre,false,false);
                 
                 ComboNuevoFactory comboN=new ComboNuevoFactory(caracCombo);
                 
                 ArmaCombos comboNuevo=new ArmaCombos(comboN);
                 
                 combo.save(comboNuevo);
                 
                 MenuProductoCombo menuProdu=new MenuProductoCombo();
                 menuProdu.menu(id,true);
                 
                 boolean precioInvalido=false;
                 double precioCombo;
                 do{
                     precioInvalido=false; 
                     try{
                    
                          precioCombo=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio "
                             + "del combo"));
                     
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
                  try{
                       id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del "
                               + "combo a buscar")); 
                       
                       Optional<ArmaCombos> opComb=combo.get(id);
                       
                       if(opComb.isPresent()==false){
                        JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                        break; 
                       }else{
                           StringBuffer cmbId=new StringBuffer(); 
                           cmbId.append("Productos del combo "
                                   +opComb.get().getNombre()+"\n");
                           productoComboDao productos=new productoComboDao();
                          
                           for(ProductosCombo prod:productos.getAll()){
                               if(prod.getIdCombo()==id){
                                cmbId.append("Id Prod: "+prod.getIdCombo()+" Nombre: "+prod.getProd().getNombreProd()+" Cantidad: "
                                      +prod.getIdCombo()+"\n");      
                               }
                           }
                           //Hay que cambiar esto :,(
                        
                           JOptionPane.showMessageDialog(null, cmbId);
                                   
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
                  try{
                      idComboMod=Integer.parseInt(JOptionPane.showInputDialog
                                    ("Ingrese el código del combo"));
                     Optional<ArmaCombos> opCombo=combo.get(idComboMod);
                     if(opCombo.isPresent()==false ||opCombo.get().isBorrado()==true){
                      JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                      break;
                     }else{
                          ComboExistenteFactory comboModifica=new ComboExistenteFactory(opCombo.get().getId()); 
                          ArmaCombos armaModifica=new ArmaCombos(comboModifica);
                      strModifica.append("Ingrese una opción:\n");
                  strModifica.append("1.Cambiar nombre del combo \n");
                  strModifica.append("2.Cambiar precio del combo \n");
                  strModifica.append("3.Editar productos del combo \n");
                  strModifica.append("4.Activar o desactivar el combo \n");
                  strModifica.append("5.Volver");
                  
                   do{
                       do{
                        opInvalida=false; 
                        try{
                            opModifica=Integer.parseInt(JOptionPane.showInputDialog
                                    (strModifica));
                            
                        }catch(NumberFormatException nfe){
                            opInvalida=true;
                        }
                       }while(opInvalida==true);
                       
                   }while(opModifica!=5);
                   
                   switch(opModifica){
                       case 1:
                           nuevoNombre=JOptionPane.showInputDialog("Ingrese el nuevo nombre "
                             + "del combo");
                           if(nuevoNombre.equalsIgnoreCase("")){
                               nuevoNombre=armaModifica.getNombre();
                           }
                           String precioNom=Double.toString(armaModifica.getPrecio());
                           
                           String[] actualizaNom={nuevoNombre,precioNom,"1"};
                           combo.update(armaModifica, actualizaNom);
                           JOptionPane.showMessageDialog(null, "El nombre ha sido modificado");
                           
                           break;
                           
                       case 2:
                      
                 do{
                     precioInvalido=false; 
                     try{
                    
                          nuevoPrecio=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo precio "
                             + "del combo"));
                          
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
                           
                       JOptionPane.showMessageDialog(null, "El precio ha sido modificado");    
                       break;    
                       
                       case 3:
                           MenuProductoCombo menuModifica=new MenuProductoCombo();
                           menuModifica.menu(armaModifica.getId(),false);
                           break;
                      
                       case 4:
                           int estadoNuevo;
                           String estadoActual="";
                           try{
                               if(armaModifica.isActivado()==true){
                                   estadoActual="Activado";
                               }else{
                                   estadoActual="Desactivado";
                               }
                               
                               estadoNuevo=Integer.parseInt(JOptionPane.showInputDialog("Digite 1 para activar el  "
                             + "combo o 0 para desactivarlo \n"+"Estado actual: "+estadoActual));
                             
                               
                               
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
                  int idComboBorra=0;
                try{
                      idComboBorra=Integer.parseInt(JOptionPane.showInputDialog
                                    ("Ingrese el código del combo"));
                     Optional<ArmaCombos> opCombo=combo.get(idComboBorra);
                     if(opCombo.isPresent()==false ||opCombo.get().isBorrado()==true){
                      JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún combo");       
                      break;
                     }else{
                      ComboExistenteFactory comboBorra=new ComboExistenteFactory(opCombo.get().getId()); 
                          ArmaCombos armaBorra=new ArmaCombos(comboBorra);   
                          combo.delete(armaBorra);
                          
                     }  
                }catch(NumberFormatException nfe){
                    break;
                }
                  
                break; 
                
              case 6: 
                  //volver(no hay que ponerle nada :V) 
               
                break; 
                 
                  
                  
                  
                  
          }
      }while(opcion!=6); 
    }
}
