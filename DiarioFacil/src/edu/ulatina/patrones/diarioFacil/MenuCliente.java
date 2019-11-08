/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import static edu.ulatina.patrones.diarioFacil.MenuAdministrador.dao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author josem
 */
public class MenuCliente implements IMenu {
    public static Dao dao;
    
    OrdenCompra  carrito = new CarritoCompra();
    
    String producto="Producto",precio_unitario="Precio_unitario",cantidad="Cantidad",monto="Monto";
    
    List<Properties> lstCarrito;
    
    boolean compraRealizada;
    
    JButton btnActualizar = new JButton("Actualizar");
    
    
    @Override
    public void mostrarMenu() {
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JPanel pnlBack = new JPanel( new GridLayout(3,1));
        JButton btnUltimaOrden = new JButton("Ver última orden Realizada");
        JButton btnCrearOrden = new JButton("Crear Orden");
        JButton btnVerReporte = new JButton("Ver Reporte");

        
        btnUltimaOrden.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/buscarOrden.png"));
        btnCrearOrden.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/orden.png"));
        btnVerReporte.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/Reporte.png"));

        
        pnlBack.add(btnUltimaOrden);
        pnlBack.add(btnCrearOrden);
        pnlBack.add(btnVerReporte);
       
        JComponent[] component = new JComponent[]{pnlBack};
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Cliente");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/cliente.png");
        
        JDialog dialog = opt.createDialog(null, opt.getName());
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(350, 400);
        dialog.setResizable(true);
        //</editor-fold>
        
         //<editor-fold defaultstate="collapsed" desc="Logica">
          btnCrearOrden.addActionListener((ActionEvent e) -> {
              //Abrir menu de ordenes
              dialog.setVisible(false);
              menuClienteCrearCompra();
              dialog.setVisible(true);
          })
                  ;
         //</editor-fold>
         
        //Arranque
        dialog.setVisible(true);
    }
    
    public void menuClienteCrearCompra(){
        
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            //Paneles
            List<Properties> inventario = new ArrayList<>();
            JPanel pnlBack,pnlSearchBar,pnlActionbar;
            JTable tblProductos = new JTable();
            JLabel lblSearch = new JLabel("Buscar : ");
            JTextField txtBuscar = new JTextField();
            
            JButton btnAdd = new JButton("Agregar al carrito");
            JButton btnSeeSc = new JButton("Ver mi carrito");
            //Modelo base
            DefaultTableModel model = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            
            //Cargando Columnas al modelo
            dao = new ClienteDao();
            inventario = ((ClienteDao)dao).getInventarioCliente();
            for(String s : inventario.get(0).stringPropertyNames())
                model.addColumn(s);
            for(Properties p : inventario)
                model.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),p.getProperty("Precio"),p.getProperty("Categoria")});
             
            tblProductos.setModel(model);
            TableColumnModel tcm = tblProductos.getColumnModel();
            tcm.removeColumn( tcm.getColumn(3) );
            
            //Iconos para los botones
            btnActualizar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-update-16.png"));
            btnAdd.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-add-16.png"));
            btnSeeSc.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-shopping-cart-16.png"));
            
            
            
            //Panel de busqueda
            pnlSearchBar  = new JPanel(new BorderLayout());
            pnlSearchBar.add(lblSearch,BorderLayout.WEST);
            pnlSearchBar.add(txtBuscar,BorderLayout.CENTER);
            pnlSearchBar.add(btnActualizar,BorderLayout.EAST);
            
            //Panel de acciones
            pnlActionbar = new JPanel(new GridLayout(1,2));
            pnlActionbar.add(btnAdd);
            pnlActionbar.add(btnSeeSc);
            
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlSearchBar,BorderLayout.NORTH);
            pnlBack.add(new JScrollPane(tblProductos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
            pnlBack.add(pnlActionbar,BorderLayout.SOUTH);
            
            //Ventana principal
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Productos");
            opt.setVisible(true);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/cliente.png");

            JDialog dialog = opt.createDialog(null, opt.getName());
            dialog.setIconImage(icon);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(600, 400);
            dialog.setResizable(true);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
            btnAdd.addActionListener((ActionEvent e) -> {
                if(tblProductos.getRowCount()>0){
                    if(tblProductos.getSelectedRow()!=-1){
                        
                        //Desplegar un JSpinner para pedirle al cliente la cantidad del producto
                        SpinnerNumberModel model1 = new SpinnerNumberModel(1.0, 1.0, 100.0, 1.0);
                        JSpinner spin  = new JSpinner(model1);
                        //Deshabilitando la escritura en el spinner
                        ((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setEditable(false);
                        JLabel cantidad= new JLabel("Cantidad :");
                        JButton btnAceptar = new JButton();
                        btnAceptar.setText("Aceptar");
                        
                        //Coleccion de controles
                        JComponent[]   componentes  = new JComponent[]{
                            cantidad,
                            spin
                        } ;
                        int idProducto;
                        
                        Double idProdTemp= Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(),3).toString());
                        
                        idProducto = idProdTemp.intValue();
                        
                        int idUser  = Constantes.USUARIOLOGUEADO.getId();
                        int result = JOptionPane.showConfirmDialog(null, componentes,"Seleccione una cantidad" ,JOptionPane.YES_NO_OPTION);
                        
                        if(result == JOptionPane.YES_OPTION){
                            //Revisar stock del producto vs cantidad seleccionada
                            dao = new ClienteDao();
                            if(((ClienteDao)dao).getDiferenciaStock(idProducto, (int)Math.round(Double.parseDouble(spin.getValue().toString())))>=0){
                                //Insertar en la orden el cliente
                                ((ClienteDao)dao).addItemCarrito((int)Math.round(Double.parseDouble(spin.getValue().toString())), idProducto, idUser);
                                
                                //Decorar el carrito
                                carrito = new Item(carrito,(int)Math.round(Double.parseDouble(spin.getValue().toString())),tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(),1).toString(),Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(),4).toString()));
                                int cant =(int)Math.round(Double.parseDouble(spin.getValue().toString()));
                                Double precioUnitario  = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(),4).toString());
                                
                                Properties prop  = new Properties();
                                prop.setProperty(this.producto,tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(),1).toString() );
                                prop.setProperty(this.cantidad,String.valueOf(cant));
                                prop.setProperty(this.precio_unitario,String.valueOf(precioUnitario) );
                                prop.setProperty(this.monto,String.valueOf(precioUnitario*cant));
                                
                                //Revisando si el producto ya ha sido agregado
                                int ItemExists = (int)lstCarrito.stream().filter((Properties p)->p.getProperty(producto).equals(prop.getProperty(producto))).count();
                                
                                if(ItemExists==0){
                                    lstCarrito.add(prop);
                                }else{
                                    lstCarrito.stream().filter((Properties p)->p.getProperty(producto).equals(prop.getProperty(producto))).forEach((Properties p)->{
                                        int oldCantidad  = Integer.parseInt(p.getProperty(this.cantidad));
                                        p.setProperty(this.cantidad, String.valueOf(cant+oldCantidad)); 
                                        p.setProperty(this.monto, String.valueOf((oldCantidad+cant)*Double.parseDouble(p.getProperty(this.precio_unitario))));  
                                    });
                                }
                                
                                //Mensaje de exito
                                JOptionPane.showMessageDialog(null, "Se ha añadido al carrito correctamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png"));
                                
                            }else{
                                //Mensaje avisando sobre la falta de disponibilidad
                                JOptionPane.showMessageDialog(null, "En este momento no contamos con suficientes existencias para satisfacer su solicitud \n "
                                        + "Intente con una cantidad menor", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                            }
                        }
                    }
                }
            });
           
            
            //<editor-fold defaultstate="collapsed" desc="Escuchando cambios en el estado de la ventana">
            dialog.addWindowListener( new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                  if(compraRealizada){
                      btnActualizar.doClick();
                  }
                }

                @Override
                public void windowClosing(WindowEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowIconified(WindowEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowActivated(WindowEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            //</editor-fold>
           
            
            btnSeeSc.addActionListener((ActionEvent e) -> {
                if(lstCarrito.size()>0){
                    compraRealizada  = false;
                    dialog.setVisible(false);
                    this.menuClienteVerCarrito(carrito.costo(),lstCarrito);
                    dialog.setVisible(true);
                        
                }else
                    JOptionPane.showMessageDialog(null, "No tienes items en el carrito","Sys",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                
                
            });
            
            btnActualizar.addActionListener((ActionEvent e) -> {
                
                List<Properties> inventarioInterno;
                DefaultTableModel internalModel = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row,int column){
                        return false;
                    }  
                };
               //Cargando Columnas al modelo
                dao = new ClienteDao();
                inventarioInterno = ((ClienteDao)dao).getInventarioCliente();
                for(String s : inventarioInterno.get(0).stringPropertyNames())
                    internalModel.addColumn(s);
                for(Properties p : inventarioInterno)
                    internalModel.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),p.getProperty("Precio"),p.getProperty("Categoria")});

                tblProductos.setModel(internalModel);
                TableColumnModel tcminternal = tblProductos.getColumnModel();
                tcminternal.removeColumn( tcminternal.getColumn(3) );
                
            });
            
            

            //Cargar en un patron decorador el carrito 
           
            lstCarrito =  new ArrayList<>();
            this.carrito = new CarritoCompra();
            
            for(Properties prop : ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.getId())){
                this.lstCarrito.add(prop);
                this.carrito = new Item(carrito,Integer.parseInt(prop.getProperty(cantidad)),prop.getProperty(producto),Double.parseDouble(prop.getProperty(precio_unitario)));
            }
            
            System.out.println("Costo de carrito : "+carrito.costo());

        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
        
    }
    
    public void menuClienteVerCarrito(double subTotal,List<Properties> carrito){
        
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            List<Properties> inventario = new ArrayList<>();
            JPanel pnlBack,pnlSearchBar,pnlActionbar,pnlActionContent,pnlSubtotal,pnlTotal;
            JTable tblProductos = new JTable();
            JLabel lblSearch = new JLabel("Buscar : ");
            JLabel lblSubTotal  = new JLabel("Subtotal : ");
            JLabel lblTotal = new JLabel("Total : ");
            JTextField txtSubTotal= new JTextField();
            JTextField txtTotal = new JTextField();
            JTextField txtBuscar = new JTextField();
            JButton btnActualizar = new JButton("Actualizar");
            JButton btnBuy = new JButton("Comprar");
            txtSubTotal.setEditable(false);
            txtTotal.setEditable(false);
            //Modelo base
            DefaultTableModel model = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            
            btnActualizar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-update-16.png"));
            
            //Cargando modelo 
            for(String col: carrito.get(0).stringPropertyNames())
                model.addColumn(col);
            
            carrito.stream().forEach((Properties prop)->{
                model.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty(cantidad),prop.getProperty(monto)});
            });
            
            tblProductos.setModel(model);
            
            //Panel de busqueda
            pnlSearchBar  = new JPanel(new BorderLayout());
            pnlSearchBar.add(lblSearch,BorderLayout.WEST);
            pnlSearchBar.add(txtBuscar,BorderLayout.CENTER);
            pnlSearchBar.add(btnActualizar,BorderLayout.EAST);
            
            //Panel de subtotal
            pnlSubtotal = new JPanel(new BorderLayout());
            pnlSubtotal.add(lblSubTotal,BorderLayout.WEST);
            pnlSubtotal.add(txtSubTotal,BorderLayout.CENTER);
            
            //Panel de total 
            pnlTotal = new JPanel(new BorderLayout());
            pnlTotal.add(lblTotal,BorderLayout.WEST);
            pnlTotal.add(txtTotal,BorderLayout.CENTER);
            
            //Juntando el total y el subtotal en una  panel
            pnlActionContent = new JPanel(new  GridLayout(1,2,2,1));
            pnlActionContent.add(pnlSubtotal);
            pnlActionContent.add(pnlTotal);

            
            //Panel de resumen
            pnlActionbar = new JPanel(new BorderLayout(5,5));
            pnlActionbar.add(pnlActionContent,BorderLayout.NORTH);
            pnlActionbar.add(btnBuy,BorderLayout.SOUTH);
            
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlSearchBar,BorderLayout.NORTH);
            pnlBack.add(new JScrollPane(tblProductos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
            pnlBack.add(pnlActionbar,BorderLayout.SOUTH);
            
            //Ventana principal
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Productos");
            opt.setVisible(true);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/cliente.png");

            JDialog dialog = opt.createDialog(null, opt.getName());
            dialog.setIconImage(icon);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(600, 400);
            dialog.setResizable(true);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
            //subtotal y total
            txtSubTotal.setText(String.valueOf(subTotal));
            txtTotal.setText(String.valueOf(subTotal+(subTotal * 0.13 )));
            
            
            btnBuy.addActionListener((ActionEvent e) -> {
             //Comprar uwu   
               if(tblProductos.getModel().getRowCount()>0){
                   ClienteDao.StockManager returned = ((ClienteDao)dao).comprar(Constantes.USUARIOLOGUEADO.Id);
                   if(returned.errors().equals("") && returned.getLooped()>0){
                       //Avisar de que la compra fue realizada con exito y actualizar
                       //Descargando modelo
                        if (model.getRowCount() > 0) {
                            for (int i = model.getRowCount() - 1; i > -1; i--) {
                                    model.removeRow(i);
                            }
                        }
                        
                        tblProductos.setModel(model);
                        txtSubTotal.setText("0.0");
                        txtTotal.setText("0.0");
                        lstCarrito.clear();
                        this.carrito = new CarritoCompra();
                        JOptionPane.showMessageDialog(null, "La compra se ha relizado con exito", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                        this.compraRealizada = true;
                   }else if(!returned.errors().equals("")){
                       //Enviarle un mensaje  de compra no  realizada debido a que x peticiones exceden stock
                       
                   }else if(returned.getLooped()==0){
                       //Algo salio mal 
                   }
               }
            });
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
         
    }
    
}
