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
                 
                  //si se agrega el mismo producto 2 veces, suma las cantidades 
                  //Pide id y nombre
                  //si id está disponible, arma un combo y despliega un subMenú
                  //con las opciones: agregar producto a combo, ver productos en combo
                  //Modificar producto del combo, borrar producto del combo
                  //completar combo(pide un precio y coloca la lista de productos 
                  //dentro del combo, luego lanza el sql
              
                  
                
                  break;
                  
              case 2: 
                  //Da un listado de los combos, con sus códigos, nombres y precios 
                   List<ArmaCombos> lst=combo.getAll();
                  String combos="Lista de combos \n"; 
                  
                  for(ArmaCombos cmb:lst){
                      combos=combos+"Código: "+cmb.getId()+
                              " Nombre: "+cmb.getNombre()+
                              " Precio: "+cmb.getPrecio()+" \n";
                      
                  }
                  
                  if(lst.isEmpty()){
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
                           
                           for(ProductosCombo prod:opComb.get().getProductos()){
                              cmbId.append("Id Prod: "+prod.getIdCombo()+" Nombre: "+prod.getProd().getNombreProd()+" Cantidad: "
                                      +prod.getIdCombo()+"\n");
                           }
                           JOptionPane.showMessageDialog(null, cmbId);
                                   
                       }
                      
                            
                       
                    }catch(NumberFormatException nfe){
                        break;
                    
                    }
                  
                  //Pide un id y con ese busca en la lista para imprimir los productos 
                  //de este
              break; 
              
              case 4: 
                  //Tira un submenú con las opciones 
                  //Cambiar nombre de combo
                  //Cambiar precio del combo 
                  //Editar productos del combo(borrar, agregar, cambiar cantidades)
                  //(reciclar el código del case 1)
                  //Activar o desactivar el combo
                break; 
                
              case 5: 
                  //Pide un id, busca en la lista, si encuentra coincidencias, 
                  //lanza el sql
                break; 
                
              case 6: 
                  //volver(no hay que ponerle nada :V) 
               
                break; 
                 
                  
                  
                  
                  
          }
      }while(opcion!=6); 
    }
}
