/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;



public class MenuProveedores {
    public static Dao proveedor;
    public void menu(){
        proveedor=new ProveedorDao(); 
        int opcion=0;
        int id=0; 
        boolean invalido=false;
        String nombre; 
        String correo;
        StringBuffer s=new StringBuffer(); 
        s.append("Ingrese una opción:\n");
        s.append("1.Nuevo proveedor \n");
        s.append("2.Ver proveedores \n");
        s.append("3.Buscar proveedor por id \n");
        s.append("4.Modificar proveedor \n");
        s.append("5.Borrar proveedor \n");
        s.append("6.Volver");
        
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
                             + "del proveedor")); 
                List<Proveedor> lst=proveedor.getAll();   
                for(Proveedor prov: lst){
                    if(prov.getCodigo()==id){
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
                    nombre=JOptionPane.showInputDialog("Ingrese el nombre");
                    correo=JOptionPane.showInputDialog("Ingrese el correo");
                    Proveedor p=new Proveedor(id,nombre,correo);
                    proveedor.save(p);
                    break; 
                case 2:
                    List<Proveedor> lst=proveedor.getAll();
                    String proveedores="Lista de proveedores \n"; 
                    
                    for(Proveedor prove:lst){
                        proveedores=proveedores+"Código:"+prove.getCodigo()+
                                " Nombre: "+prove.getNombre()+
                                " Correo: "+prove.getCorreo()+"\n";
                        
                                
                    }
                    if(lst.isEmpty()){
                        proveedores="No hay ningún proveedor registrado";
                    }
                    JOptionPane.showMessageDialog(null,proveedores);
                    break;
                case 3:
                    try{
                       id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id")); 
                       Optional<Proveedor> opProv=proveedor.get(id);
                       if(opProv.isPresent()==false){
                       JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún proveedor");    
                       break;
                       }else{
                           String proveedorId="Código:"+opProv.get().getCodigo()+
                                " Nombre: "+opProv.get().getNombre()+
                                " Correo: "+opProv.get().getCorreo();
                           JOptionPane.showMessageDialog(null,proveedorId);
                           
                       }
                            
                       
                    }catch(NumberFormatException nfe){
                        break;
                    
                    }
                    break;
                case 4:
                    try{
                      id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id"
                              + " del proveedor a modificar"));    
                      Optional<Proveedor> opProve=proveedor.get(id);
                      if(opProve.isPresent()==false){
                       JOptionPane.showMessageDialog(null,"El id ingresado no corresponde"
                               + " con ningún proveedor");
                      break;   
                      }else{
                          String nom;
                          String email;
                          nom=JOptionPane.showInputDialog("Ingrese el nuevo nombre"
                                  + " del proveedor \n (Si deja el espacio en blanco, se"
                                  + " mantendrá el actual)");
                          email=JOptionPane.showInputDialog("Ingrese el nuevo"
                                  + " correo del proveedor \n(Si deja el espacio en "
                                  + "blanco, se mantendrá el actual)");
                         if(nom.equalsIgnoreCase("")){
                             nom=opProve.get().getNombre();
                         }
                         if(email.equalsIgnoreCase("")){
                             email=opProve.get().getCorreo();
                         }
                         String[] actualiza=new String[]{nom,email};
                         Proveedor provAux=new Proveedor(id,nom,email);
                         proveedor.update(provAux, actualiza);
                         
                      }
                      
                    }catch(NumberFormatException nfe){
                        break;
                    }
                    break;
                    
                case 5:
                    try{
                    id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id"
                            + " del proveedor a borrar \n"
                            + "AVISO: esto también borrará todos los productos "
                            + " asociados a este proveedor."));        
                    Optional<Proveedor> opProveedor=proveedor.get(id);
                    if(opProveedor.isPresent()==false){
                     JOptionPane.showMessageDialog(null,"El id ingresado no corresponde con ningún proveedor");
                    
                    break;  
                    }else{
                        Proveedor provDel=new Proveedor(id,"","");
                        proveedor.delete(provDel);
                        
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
}
