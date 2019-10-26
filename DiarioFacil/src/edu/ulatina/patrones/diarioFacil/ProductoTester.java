


package edu.ulatina.patrones.diarioFacil;

import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProductoTester  {
     
    
    
    public static void main(String[] args) {
        Scanner scanner=new Scanner(in);
       ProductoDao productodao = new ProductoDao();
        List<Producto> lstProductos = new ArrayList<>();
        
        
       
        lstProductos = productodao.getAll();
        
        for (int i = 0; i < lstProductos.size(); i++) {
            System.out.println(lstProductos.get(i).getId()+" "+lstProductos.get(i).getNombreProd()+" "+lstProductos.get(i).getPrecio());
        }
            //System.out.println(productodao.get(2).get().getId()+productodao.get(2).get().getNombreProd());   
    
            /*Producto newProducto = new Producto();
            System.out.println("Ingrese el numbre del producto");
            newProducto.setNombreProd(scanner.next());
            System.out.println("Ingrese el precio del producto");
            newProducto.setPrecio(scanner.nextDouble());
            productodao.save(newProducto);*/
            
            /*Producto deleteProducto= new Producto();
            System.out.println("Ingrese el id del producto a eliminar");
            deleteProducto.setId(scanner.nextInt());
            productodao.delete(deleteProducto);*/
            
            Producto updateProducto= new Producto();
            System.out.println("Ingrese el id del producto a actualizar");
            updateProducto.setId(scanner.nextInt());
            System.out.println("Ingrese el nombre del producto a actualizar");
            updateProducto.setNombreProd(scanner.next());
            System.out.println("Ingrese el precio del producto a actualizar");
            updateProducto.setPrecio(scanner.nextDouble());
            productodao.update(updateProducto, args);
            
            lstProductos = productodao.getAll();
        
        for (int i = 0; i < lstProductos.size(); i++) {
            System.out.println(lstProductos.get(i).getId()+" "+lstProductos.get(i).getNombreProd()+" "+lstProductos.get(i).getPrecio());
        }
    }
    
    
}
