package edu.ulatina.patrones.diarioFacil;

import static edu.ulatina.patrones.diarioFacil.MenuCategorias.categoria;
import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

public class MenuProducto{

  
    public static Dao producto;
  public void menu(){
      producto = new ProductoDao();
      int opcion=0;
      int id=0;
      int idProveedor=0;
      int idCat=0;
      int stockMinimo=0;
      int stockActual=0;
      double precio=0;
      String marca=""; 
      String nombre=""; 
      boolean invalido=false;
      StringBuffer s=new StringBuffer();
      s.append("Ingrese una opción:\n");
      s.append("1.Nuevo producto \n");
      s.append("2.Ver productos \n");
      s.append("3.Modificar producto \n");
      s.append("4.Borrar producto \n");
      s.append("5.Manejo de promociones \n");
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
               boolean provExiste=false;
               boolean catExiste=false; 
                  try{
              
                 id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código "
                             + "del producto")); 
                 List<Producto> lst=producto.getAll();
                 for(Producto prod:lst){
                     if(prod.getCodProducto()==id){
                         repetido=true;
                     }
                 }
                 if(repetido==true){
                 JOptionPane.showMessageDialog(null,"El código ingresado ya existe");
               break;    
                }
                  }catch(NumberFormatException nfe){
                     break; 
                  }
                  
                  nombre=JOptionPane.showInputDialog("Ingrese el nombre del producto");
                  marca=JOptionPane.showInputDialog("Ingrese la marca del producto");
                  
                  do{
                      invalido=false;
                 
                     try{
                      provExiste=false;
                  idProveedor=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código "
                             + "del proveedor")); 
                  ProveedorDao proveedores=new ProveedorDao();
                  List<Proveedor> lstProve=proveedores.getAll();
                  for(Proveedor prove:lstProve){
                      if(prove.getCodigo()==idProveedor){
                          provExiste=true;
                      }
                  }
                  
                  if(provExiste==false){
                    JOptionPane.showMessageDialog(null,"El proveedor ingresado no existe");   
                    invalido=true;
                  }
                  }catch(NumberFormatException nfe){
                      invalido=true;
                  }  
                  }while(invalido);
                 
                 
                  
                  do{
                      invalido=false;
                      try{
                         catExiste=false;
                         idCat=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código "
                             + "de la categoría"));
                         CategoriaDao cate=new CategoriaDao();
                        List<Categoria> lstCat=cate.getAll();
                        for(Categoria cat:lstCat){
                            if(cat.getCodCategoria()==idCat){
                                catExiste=true;
                            }
                        }
                         if(catExiste==false){
                             JOptionPane.showMessageDialog(null,"La categoría ingresada no existe");   
                             invalido=true;
                         }
                         
                      }catch(NumberFormatException nfe){
                          invalido=true;
                      }
                  }while(invalido);
                  
                  
                  do{
                      invalido=false;
                      try{
                        precio=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio "
                             + "del producto"));
                      }catch(NumberFormatException nfe){
                          invalido=true;
                      }
                      
                  }while(invalido);
                  
                  do{
                      invalido=false;
                      try{
                          stockMinimo=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock "
                             + "minimo del producto"));
                      }catch(NumberFormatException nfe){
                          invalido=true;
                      }
                  }while(invalido);
                  
                    do{
                      invalido=false;
                      try{
                          stockActual=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock"
                             + " actual del producto"));
                          
                          
                      }catch(NumberFormatException nfe){
                          invalido=true;
                      }
                      
                      if(stockActual<stockMinimo){
                          JOptionPane.showMessageDialog(null,"El stock actual no puede"
                                  + " ser menor al mínimo");
                          invalido=true;
                      }
                  }while(invalido);
                    
                   Producto produ=new Producto(idProveedor,id,nombre,stockMinimo,
                    stockActual,precio,idCat,false,false,marca);
                    producto.save(produ);
                    
                  break;
                  
              case 2:
                  boolean entro=false;
                  Optional<Categoria> categoriaActual;
                 Optional<Proveedor> proveedorActual;
                 String nombreCat;
                 String nombreProve;
                 String promo;
                  List<Producto>lstProd=producto.getAll();
                  String productos="Lista de productos \n";
                  ProveedorDao proveDao=new ProveedorDao();
                  CategoriaDao cateDao=new CategoriaDao();
                  for(Producto produver:lstProd){
                      if(produver.isBorrado()==false){
                          entro=true;
                          if(produver.isPromocion()){
                              promo="Sí";
                          }else{
                              promo="No";
                          }
                          categoriaActual=cateDao.get(produver.getCodCategoria());
                          nombreCat=categoriaActual.get().getNombreCategoria();
                          proveedorActual=proveDao.get(produver.getCodProveedor());
                          nombreProve=proveedorActual.get().getNombre();
                          productos=productos+
                                  "Código: "+produver.getCodProducto()+
                                  " Nombre: "+produver.getNombreProd()+
                                  " Categoría: "+nombreCat+
                                  " Proveedor: "+nombreProve+
                                  " Precio:"+produver.getPrecio()+
                                  " StockMinimo:"+produver.getStockMinimo()+
                                  " StockActual:"+produver.getStockActual()+
                                  " EnPromoción:"+promo+"\n";
                      }
                  }
                  if(entro==false||lstProd.isEmpty()){
                      productos="No hay ningún producto registrado";
                  }
                   JOptionPane.showMessageDialog(null,productos);
                  break;
                  
              case 3:
                  try{
                     id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código"
                              + " del producto a modificar"));  
                     Optional<Producto> opProd=producto.get(id);
                     
                     if(opProd.isPresent()==false || opProd.get().isBorrado()==true){
                         JOptionPane.showMessageDialog(null,"El código ingresado no corresponde"
                               + " con ningún producto");
                         break;
                     }else{
                         String nom;
                         double nuevoPrecio=0;
                         int stockMin=0;
                         boolean incorrecto=false;
                         nom=JOptionPane.showInputDialog("Ingrese el nuevo nombre"
                                  + " del producto \n (Si deja el espacio en blanco, se"
                                  + " mantendrá el actual)");
                         do{
                             incorrecto=false;
                             try{
                             nuevoPrecio=Double.parseDouble(JOptionPane.showInputDialog("Ingrese "
                                     + "el nuevo precio \n (Si escribe un 0 "
                                  + "se mantendrá el actual)"));    
                             }catch(NumberFormatException nfe){
                                 incorrecto=true;
                             }
                             
                             
                         }while(incorrecto);
                         
                         do{
                             incorrecto=false;
                             try{
                                 stockMin=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el "
                                         + " nuevo stock mínimo \n (si escribe un 0"
                                         + " se mantendrá el actual"));
                             }catch(NumberFormatException nfe){
                                 incorrecto=true;
                             }
                         }while(incorrecto);
                         if(nom.equalsIgnoreCase("")){
                             nom=opProd.get().getNombreProd();
                         }
                         
                         if(nuevoPrecio==0){
                             nuevoPrecio=opProd.get().getPrecio();
                         }
                         if(stockMin==0){
                             stockMin=opProd.get().getStockMinimo();
                         }
                         String[]actualiza=new String[]{nom};
                         stockActual=opProd.get().getStockActual();
                         boolean promoModi=opProd.get().isPromocion();
                         Producto prodActualiza=new Producto(id,nom,nuevoPrecio,stockActual,promoModi,stockMin);
                         
                         producto.update(prodActualiza, actualiza);
                         JOptionPane.showMessageDialog(null,"Se han realizado los cambios");
                     }  
                    
                  }catch(NumberFormatException nfe){
                      break;
                  }
                  
                  break;
                  
              case 4:
                  try{
                      id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código"
                            + " del producto a borrar"));    
                      Optional<Producto> opProduct=producto.get(id);
                      if(opProduct.isPresent()==false ||opProduct.get().isBorrado()==true){
                          JOptionPane.showMessageDialog(null,"El código ingresado no corresponde con ningún producto");
                          break;
                      }else{
                          Producto produDel=new Producto(id);
                          producto.delete(produDel);
                          
                      }
                  }catch(NumberFormatException nfe){
                      break;
                  }
              break; 
              
              case 5: 
                  int op=0;
                String estadoPromo;
                boolean incorrecto=false;
                  //Pide id, si existe, abre mini-menu con 2 opciones
                  //Poner en promoción, Quitar de promoción
                  try{
                      id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código"
                            + " del producto al que le quiere cambiar su estado"));    
                      Optional<Producto> opProduct=producto.get(id);
                      if(opProduct.isPresent()==false ||opProduct.get().isBorrado()==true){
                          JOptionPane.showMessageDialog(null,"El código ingresado no corresponde con ningún producto");
                          break;
                      }else{
                          if(opProduct.get().isPromocion()){
                             estadoPromo= "Activado";
                          }else{
                              estadoPromo="Desactivado";
                          }
                              
                          StringBuffer mini=new StringBuffer();
                          mini.append("Digite una opción \n");
                          mini.append("1.Cambiar estado de promoción(Actual:"+estadoPromo+")\n");
                          
                          mini.append("2.Volver");
                          do{
                          
                          do{
                              
                              incorrecto=false;
                              try{
                              op=Integer.parseInt(JOptionPane.showInputDialog(mini));    
                              }catch(NumberFormatException nfe){
                                  incorrecto=true;
                              }
                          }while(incorrecto);    
                              
                          if(op==1){
                              String nom=opProduct.get().getNombreProd();
                              double nuevoPrecio=opProduct.get().getPrecio();
                              stockActual=opProduct.get().getStockActual();
                              boolean promoModi=false;
                              int stockMin=opProduct.get().getStockMinimo();
                              if(opProduct.get().isPromocion()){
                                  promoModi=false;
                                  //Desactiva
                              }else{
                                  promoModi=true;
                                  //Activa
                              }
                              String[]actualiza=new String[]{nom};
                              Producto prodActualiza=new Producto(id,nom,nuevoPrecio,stockActual,promoModi,stockMin);   
                              producto.update(prodActualiza, actualiza);
                              JOptionPane.showMessageDialog(null,"Se han realizado los cambios");
                          }
                          
                          }while(op!=2 && op!=1);
                          
                      }
                  }catch(NumberFormatException nfe){
                      break;
                  }
                  break;
          }
      }while(opcion!=6);
  }
 
    
        
    }
