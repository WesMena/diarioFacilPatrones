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

public class MenuProducto  extends Conexion{

    List<Producto> lstProductos = new ArrayList();
    ProductoDao productoDao = new ProductoDao();
    String[] args;

    public void menu() throws SQLException {
        String normalUsage = "";
        categoria = new CategoriaDao();
        int opcionAux = 0;
        int opcion = 0;
        int id = 0;
        boolean invalido;
        String nombre;

        StringBuffer s = new StringBuffer();
        s.append("Ingrese una opción:\n");
        s.append("1.Ver todos los productos \n");
        s.append("2.Buscar producto por id \n");
        s.append("3.Agregar producto \n");
        s.append("4.Actualizar producto \n");
        s.append("5.eliminar producto \n");
        s.append("6.Volver");
        do {
            do {
                invalido = false;
                try {
                    opcion = Integer.parseInt(JOptionPane.showInputDialog(s));
                } catch (NumberFormatException nfe) {
                    invalido = true;
                }

            } while (invalido == true);

            switch (opcion) {
                case 1:

                    //Aqui me traigo la lista nuevamente para ver el cambio de modificar
                    lstProductos = productoDao.getAll();
                    //Recorro la lista
                    for (int i = 0; i < lstProductos.size(); i++) {
                        normalUsage = lstProductos.get(i).getId() + " " + lstProductos.get(i).getNombreProd() + " " + lstProductos.get(i).getPrecio() + "\n" + normalUsage;
                    }
                    JOptionPane.showMessageDialog(null, normalUsage);
                    normalUsage = "";
                    break;
                case 2:
                    opcionAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del producto a buscar."));
                    JOptionPane.showMessageDialog(null, productoDao.get(opcionAux).get().getId() + " " + productoDao.get(opcionAux).get().getNombreProd());
                    break;
                case 3:
                    //Aqui creo un nuevo producto en la base de datos

                        String auxBool="";
                        Producto newProducto = new Producto();
                        newProducto.setNombreProd(JOptionPane.showInputDialog("Ingrese el nombre del nuevo producto."));
                        newProducto.setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del nuevo producto.")));
                        newProducto.setIdCategoria(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id de la categoria.")));
                        newProducto.setIdProveedor(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id de la proveedor.")));
                        auxBool=JOptionPane.showInputDialog("Indique si el producto esta en promocion(S/N)");
                        if(auxBool.equals("S")||auxBool.equals("s")){
                            newProducto.setPromocion("True");
                        }else{
                            newProducto.setPromocion("False");
                        }
                        
                        String nombreProducto;
                        lstProductos = productoDao.getAll();
                        for (Producto p : lstProductos) {
                            nombreProducto = p.getNombreProd();
                            
                            if (nombreProducto.equals(newProducto.getNombreProd())) {
                                System.out.println("El producto ya existe, inserte uno diferente");
                            }else{
                                System.out.println(newProducto.getPromocion());
                                productoDao.save(newProducto);
                            }

                        }

                     
                        break;
                    
            
            case 4:
                    //Aqui eligo el id del producto a cambiar para despues mandar sql statement y cambiarle los cambios asignados en producto dao

                    Producto updateProducto = new Producto();
                    updateProducto.setId(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del producto a buscar.")));
                    updateProducto.setNombreProd(JOptionPane.showInputDialog("Ingrese el nombre del producto a actualizar."));
                    updateProducto.setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto a actualizar.")));
                    updateProducto.setStockActual(parseInt(JOptionPane.showInputDialog("Ingrese el numero de stock actual")));

                    productoDao.update(updateProducto, args);

                    break;
                case 5:
                    //Esta elimina un producto mediante el id del producto como unica referencial al objeto que deseo eliminar.
                    Producto deleteProducto = new Producto();
                    deleteProducto.setId(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del producto a eliminar.")));
                    productoDao.delete(deleteProducto);
                    break;
                case 6:
                    break;
            }
        } while (opcion != 6);
        }

    }
