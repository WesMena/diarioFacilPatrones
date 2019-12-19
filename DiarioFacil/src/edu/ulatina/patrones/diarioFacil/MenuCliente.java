/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class MenuCliente extends Observado implements IMenu {
    public static Dao dao;
    
    OrdenCompra  carrito= new CarritoCompra();
    
    String producto="Producto",precio_unitario="Precio_unitario",cantidad="Cantidad",monto="Monto";
    
    List<Properties> lstCarritoSearch;
    
    boolean compraRealizada;
    
   
    
    
    @Override
    public void mostrarMenu() {
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JPanel pnlBack = new JPanel( new GridLayout(4,1));
        JButton btnUltimaOrden = new JButton("Ver última orden Realizada");
        JButton btnCrearOrden = new JButton("Crear Orden");
        JButton btnVerReporte = new JButton("Ver Reporte");
        JButton btnCambiarPass = new JButton("Cambiar Contraseña");


        
        btnUltimaOrden.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/buscarOrden.png"));
        btnCrearOrden.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/orden.png"));
        btnVerReporte.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/Reporte.png"));
        btnCambiarPass.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/passcambio.png"));
        
        pnlBack.add(btnUltimaOrden);
        pnlBack.add(btnCrearOrden);
        pnlBack.add(btnVerReporte);
        pnlBack.add(btnCambiarPass);
        pnlBack.add(btnCambiarPass);
       
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
        JButton btCerrar = opt.getRootPane().getDefaultButton(); 
        btCerrar.setLabel("Cerrar");
        //</editor-fold>
        
         //<editor-fold defaultstate="collapsed" desc="Logica">
          
          btnUltimaOrden.addActionListener((ActionEvent e) -> {
              //Abrir Ver Ultima Orden
              dialog.setVisible(false);
              Optional op = Optional.empty();
              clienteVerUltimaOrden(Constantes.USUARIOLOGUEADO.getId(),op);
              dialog.setVisible(true);
          });
         
         
         
         btnCrearOrden.addActionListener((ActionEvent e) -> {
              //Abrir menu de ordenes
              dialog.setVisible(false);
              menuClienteCrearCompra();
              dialog.setVisible(true);
          })
                  ;
          
          btnCambiarPass.addActionListener((ActionEvent e) -> {
              //Abrir Cambio de Contraseña
              dialog.setVisible(false);
             menuClientePass();
              dialog.setVisible(true);
          })
                  ;
          
        btnVerReporte.addActionListener((ActionEvent e)->{
            dialog.setVisible(false);
            ReporteCliente reporte=new ReporteCliente();
            reporte.verHistorial(Constantes.USUARIOLOGUEADO.getId());
            dialog.setVisible(true);
        });
         //</editor-fold>
         
        //Arranque
        dialog.setVisible(true);
    }
    
    public void menuClienteCrearCompra(){
        
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            //Paneles
            List<Properties> inventario = new ArrayList<>();
            this.lstCarritoSearch = inventario;
            JPanel pnlBack,pnlSearchBar,pnlActionbar;
            JTable tblProductos = new JTable();
            JLabel lblSearch = new JLabel("Buscar : ");
            JTextField txtBuscar = new JTextField();
            JButton btnActualizar = new JButton("Actualizar");
            JButton btnAdd = new JButton("Agregar al carrito");
            JButton btnSeeSc = new JButton("Ver mi carrito");
            JButton btnCombos  = new JButton("Combos");
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
            
            dao  = new ProductoDao();
            for(Properties p : inventario){
                if(Constantes.USUARIOLOGUEADO.isIsPref() && ((ProductoDao)dao).isPromoDisp(Integer.parseInt(p.getProperty("IDProducto"))) ){
                    model.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),String.valueOf(Double.parseDouble(p.getProperty("Precio"))-(Double.parseDouble(p.getProperty("Precio"))*0.10)),p.getProperty("Categoria")}); 
                }else{
                    model.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),p.getProperty("Precio"),p.getProperty("Categoria")}); 
                }
                    
               
            }
               
             
            tblProductos.setModel(model);
            TableColumnModel tcm = tblProductos.getColumnModel();
            tcm.removeColumn( tcm.getColumn(0) );
            
            //Iconos para los botones
            btnActualizar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-update-16.png"));
            btnAdd.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-add-16.png"));
            btnSeeSc.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-shopping-cart-16.png"));
            btnCombos.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-package-16.png"));
            
            
            //Panel de busqueda
            pnlSearchBar  = new JPanel(new BorderLayout());
            pnlSearchBar.add(lblSearch,BorderLayout.WEST);
            pnlSearchBar.add(txtBuscar,BorderLayout.CENTER);
            pnlSearchBar.add(btnActualizar,BorderLayout.EAST);
            
            //Panel de acciones
            pnlActionbar = new JPanel(new GridLayout(1,3));
            pnlActionbar.add(btnAdd);
            pnlActionbar.add(btnSeeSc);
            pnlActionbar.add(btnCombos);
            
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
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
        //<editor-fold defaultstate="collapsed" desc="Salir">
            btCerrar.setText("Salir");
            
            btCerrar.addActionListener((ActionEvent e) -> {
                dialog.dispose();
            });
            //</editor-fold>
            
            
        //<editor-fold defaultstate="collapsed" desc="Filtrar">
        txtBuscar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if(Character.isDigit(e.getKeyChar()))
                        e.consume();   
                }

            @Override
            public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                //Modelo base
            DefaultTableModel modelIni = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                        return false;
                    }
                };
                List<Properties> inventario = new ArrayList<>();
                List<Properties> inventarioFiltered =  inventario;
                //Cargando Columnas al modelo
                inventarioFiltered.clear();
                dao = new ClienteDao();
                inventario = ((ClienteDao)dao).getInventarioCliente();
                for (String s : inventario.get(0).stringPropertyNames()) {
                    modelIni.addColumn(s);
                }

                inventario.stream().filter( (Properties x ) -> x.getProperty("Producto").contains(txtBuscar.getText())).forEach( z ->{
                    inventarioFiltered.add(z);
                });
                
                
                dao  = new ProductoDao();
                for(Properties p : inventarioFiltered){
                    if(Constantes.USUARIOLOGUEADO.isIsPref() && ((ProductoDao)dao).isPromoDisp(Integer.parseInt(p.getProperty("IDProducto"))) ){
                        modelIni.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),String.valueOf(Double.parseDouble(p.getProperty("Precio"))-(Double.parseDouble(p.getProperty("Precio"))*0.10)),p.getProperty("Categoria")}); 
                    }else{
                        modelIni.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),p.getProperty("Precio"),p.getProperty("Categoria")}); 
                    }


                }
               
             
                tblProductos.setModel(modelIni);
                TableColumnModel tcm = tblProductos.getColumnModel();
                tcm.removeColumn( tcm.getColumn(3) );
                
            }
        });
        
        //</editor-fold>
        btnCombos.addActionListener((ActionEvent e) -> {
                dao = new ComboDao();
                if(!((ComboDao)dao).getAll().isEmpty()){
                    dialog.setVisible(false);
                    menuClienteVerCombos();
                    dialog.setVisible(true);   
                }
            });
        
            btnAdd.addActionListener((ActionEvent e) -> {
                if(tblProductos.getRowCount()>0){
                    if(tblProductos.getSelectedRow()!=-1){
                        
                        //Desplegar un JSpinner para pedirle al cliente la cantidad del producto
                        SpinnerNumberModel model1 = new SpinnerNumberModel(1.0, 1.0, 100.0, 1.0);
                        JSpinner spin  = new JSpinner(model1);
                        //Deshabilitando la escritura en el spinner
                        ((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setEditable(false);
                        JLabel cantidadIn= new JLabel("Cantidad :");
                        JButton btnAceptar = new JButton();
                        btnAceptar.setText("Aceptar");
                        
                        //Coleccion de controles
                        JComponent[]   componentes  = new JComponent[]{
                            cantidadIn,
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
                    btnActualizar.doClick();
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            //</editor-fold>
           
            
            btnSeeSc.addActionListener((ActionEvent e) -> {
                dao = new ClienteDao();
                if((((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id).size()>0)){
                    compraRealizada  = false;
                    dialog.setVisible(false);
                    this.menuClienteVerCarrito(carrito.costo(),lstCarritoSearch);
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
                dao = new ProductoDao();
                for(Properties p : inventarioInterno){
                    if(Constantes.USUARIOLOGUEADO.isIsPref() && ((ProductoDao)dao).isPromoDisp(Integer.parseInt(p.getProperty("IDProducto"))) ){
                        internalModel.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),String.valueOf(Double.parseDouble(p.getProperty("Precio"))-(Double.parseDouble(p.getProperty("Precio"))*0.10)),p.getProperty("Categoria")}); 
                    }else{
                        internalModel.addRow(new Object[]{p.getProperty("Disponibles"),p.getProperty("Producto"),p.getProperty("Marca"),p.getProperty("IDProducto"),p.getProperty("Precio"),p.getProperty("Categoria")}); 
                    }  
                }
            
                tblProductos.setModel(internalModel);
                TableColumnModel tcminternal = tblProductos.getColumnModel();
                tcminternal.removeColumn( tcminternal.getColumn(3) );
                
            });
            
            
            this.carrito = new CarritoCompra();
            
            dao  = new ClienteDao();
            for(Properties prop : ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.getId())){
                dao = new ProductoDao();
                if(Integer.parseInt(prop.getProperty("isCombo"))==0){
                    if(Constantes.USUARIOLOGUEADO.isIsPref() && ((ProductoDao)dao).isPromoDisp(Integer.parseInt(prop.getProperty("ProductoID"))) ){
                        //Esta en promo la wea
                        this.carrito = new Item(carrito,Integer.parseInt(prop.getProperty(cantidad)),prop.getProperty(producto),Double.parseDouble(prop.getProperty(precio_unitario)) - Double.parseDouble(prop.getProperty(precio_unitario))*0.10);
                    }else{
                        this.carrito = new Item(carrito,Integer.parseInt(prop.getProperty(cantidad)),prop.getProperty(producto),Double.parseDouble(prop.getProperty(precio_unitario)));
                    }
                }   
            }
            dao = new ClienteDao();
            this.carrito = new Item(carrito,1,"",((ClienteDao)dao).costo_combos(((ClienteDao)dao).Orden_actual(Constantes.USUARIOLOGUEADO.Id)));
            
            
            System.out.println("Costo de carrito : "+carrito.costo());

        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
        
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    
    public void menuClienteVerCarrito(double subTotal,List<Properties> carrito){
        
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
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
            JPanel pnlTblMods;
            JPanel pnlBtnMod;
            JButton btnDelete = new JButton("Eliminar");
            JButton btnUpdate = new JButton("Modificar");
            JButton btnCombo = new JButton("Ver contenido");
            //Modelo base
            DefaultTableModel model = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            
            btnActualizar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-update-16.png"));
            btnUpdate.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/expediente.png"));
            btnDelete.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/borrar.png"));
            
            carrito = ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id);
            
            //Cargando modelo 
            for(String col: carrito.get(0).stringPropertyNames())
                model.addColumn(col);


            
            carrito.stream().forEach((Properties prop)->{
                Integer idProd = -1;
                idProd = Integer.parseInt(prop.getProperty("ProductoID"));
                dao = new ProductoDao();
                if(!((ProductoDao)dao).isPromoDisp(idProd) && !Constantes.USUARIOLOGUEADO.isIsPref()){
                    dao = new ClienteDao();
                    model.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                }else{
                    dao = new ClienteDao();
                    Double precioOr = Double.parseDouble(prop.getProperty(precio_unitario));
                    precioOr = precioOr - (precioOr*0.10);
                    model.addRow(new Object[]{String.format("%,.2f", precioOr),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),Double.parseDouble(prop.getProperty(cantidad))*precioOr, ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                }
            });
            

            
            tblProductos.setModel(model);
            TableColumnModel tcminternal = tblProductos.getColumnModel();
            tcminternal.removeColumn( tcminternal.getColumn(2));
            tcminternal.removeColumn( tcminternal.getColumn(2) );
           
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
            
            //Panel de los botones
            pnlBtnMod = new JPanel(new BorderLayout());
            pnlBtnMod.add(btnDelete,BorderLayout.WEST);
            pnlBtnMod.add(btnUpdate,BorderLayout.CENTER);
            pnlBtnMod.add(btnCombo,BorderLayout.EAST);
            
            
            //Panel de la tabla y los botones de modificacion
            pnlTblMods  = new JPanel(new BorderLayout());
            pnlTblMods.add(new JScrollPane(tblProductos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
            pnlTblMods.add(pnlBtnMod,BorderLayout.SOUTH);
            
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlSearchBar,BorderLayout.NORTH);
            pnlBack.add(pnlTblMods,BorderLayout.CENTER);
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
            
            //<editor-fold defaultstate="collapsed" desc="Comprar">
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
                        lstCarritoSearch.clear();
                        this.carrito = new CarritoCompra();
                        JOptionPane.showMessageDialog(null, "La compra se ha relizado con exito", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                        this.compraRealizada = true;
                        notificarObservadores();
                        Optional op = Optional.empty();
                        clienteVerUltimaOrden(Constantes.USUARIOLOGUEADO.getId(),op);
                   }else if(!returned.errors().equals("")){
                       //Enviarle un mensaje  de compra no  realizada debido a que x peticiones exceden stock
                       
                   }else if(returned.getLooped()==0){
                       //Algo salio mal 
                   }
               }
            });
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Modificar">
            btnUpdate.addActionListener((ActionEvent e) -> {
                if(tblProductos.getSelectedRow()!=-1){
                    Double isCombo = 0.0;
                    isCombo = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 3).toString());
                    //Llamar spinner que actualice la cantidad del producto
                    //Desplegar un JSpinner para pedirle al cliente la cantidad del producto
                    JPanel pnlUpdatde  = new JPanel(new BorderLayout());
                    SpinnerNumberModel model1 = new SpinnerNumberModel(1.0, 1.0, 100.0, 1.0);
                    JSpinner spin  = new JSpinner(model1);
                    //Deshabilitando la escritura en el spinner
                    ((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setEditable(false);
                    String prod = tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 1).toString();
                    Double actualValue  = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 2).toString());
                    spin.setValue(actualValue.intValue());
                    
                    JLabel cantidadCarrito= new JLabel("Cantidad :");
                    JButton btnAceptar = new JButton();
                    btnAceptar.setText("Aceptar");

                    //Coleccion de controles
                    JComponent[]   componentes  = new JComponent[]{
                        cantidadCarrito,
                        spin
                    } ;
                    Double idProductoIn = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 2).toString());
                    Double precioProducto = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 0).toString());
                    Double viejaCantidad = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 4).toString());
                    Double precioCombo  = ((ClienteDao)dao).precioCombo_byID(isCombo.intValue());

                    
                    if(isCombo.intValue()==0){
                        //Modificacion de un producto
                        int result = JOptionPane.showConfirmDialog(null, componentes,prod,JOptionPane.YES_NO_OPTION);
                        if(result == JOptionPane.YES_OPTION){
                            //llamar procedimiento de actualizacion
                            int diff;
                            dao = new ClienteDao();
                            ((ClienteDao)dao).update_or_delete_Item(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.Id,(int)Math.round(Double.parseDouble(spin.getValue().toString())));
                            //Mensaje de exito
                            JOptionPane.showMessageDialog(null, "Su modificacion se ha realizado exitosamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                            //Actualizar table y el fucking decorador del carrito
                               List<Properties> carritoIn = ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id);
                               DefaultTableModel modelUpdate = new DefaultTableModel(){
                                @Override
                                    public boolean isCellEditable(int row,int column){
                                        return false;
                                    }
                                };
                                //Cargando modelo 
                                for(String col: carritoIn.get(0).stringPropertyNames())
                                    modelUpdate.addColumn(col);



                                carritoIn.stream().forEach((Properties prop)->{
                                    Integer idProd = -1;
                                    idProd = Integer.parseInt(prop.getProperty("ProductoID"));
                                    dao = new ProductoDao();
                                    if(!((ProductoDao)dao).isPromoDisp(idProd) && !Constantes.USUARIOLOGUEADO.isIsPref()){
                                        dao = new ClienteDao();
                                        modelUpdate.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                    }else{
                                        dao = new ClienteDao();
                                        Double precioOr = Double.parseDouble(prop.getProperty(precio_unitario));
                                        precioOr = precioOr - (precioOr*0.10);
                                        modelUpdate.addRow(new Object[]{precioOr.toString(),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                    }
                                });



                                tblProductos.setModel(modelUpdate);
                                TableColumnModel tcminternalUpdate = tblProductos.getColumnModel();
                                tcminternal.removeColumn( tcminternalUpdate.getColumn(2));
                                tcminternal.removeColumn( tcminternalUpdate.getColumn(2) );
                                
                                //Decorando
                                diff = viejaCantidad.intValue() - (int)Math.round(Double.parseDouble(spin.getValue().toString()));
                                if(diff>0){
                                    this.carrito = new Item(this.carrito,diff,"",(-1*precioProducto));
                                }else if(diff<0){
                                    this.carrito = new Item(this.carrito,(diff*-1),"",precioProducto);
                                }else{
                                    this.carrito = new Item(this.carrito,viejaCantidad.intValue(),"",(-1*precioProducto));
                                }
                                
                               txtSubTotal.setText(String.valueOf(this.carrito.costo()));
                               txtTotal.setText(String.valueOf(this.carrito.costo()+(this.carrito.costo()*0.13)));
                        }
                    }else{
                        //Modificacion de un combo
                        int result = JOptionPane.showConfirmDialog(null, componentes,prod,JOptionPane.YES_NO_OPTION);
                            dao = new ClienteDao();
                            viejaCantidad = Double.parseDouble(String.valueOf(((ClienteDao)dao).get_cantidad_Combo(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.getId(), isCombo.intValue()))) ;
                        if(result == JOptionPane.YES_OPTION){
                            ((ClienteDao)dao).update_or_delete_Combo(idProductoIn.intValue(),Constantes.USUARIOLOGUEADO.getId(), isCombo.intValue(),(int)Math.round(Double.parseDouble(spin.getValue().toString())));
                            //Mensaje de exito
                            JOptionPane.showMessageDialog(null, "Su modificacion se ha realizado exitosamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                            //Actualizar table y el fucking decorador del carrito
                               List<Properties> carritoIn = ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id);
                               DefaultTableModel modelUpdate = new DefaultTableModel(){
                                @Override
                                    public boolean isCellEditable(int row,int column){
                                        return false;
                                    }
                                };
                                //Cargando modelo 
                                for(String col: carritoIn.get(0).stringPropertyNames())
                                    modelUpdate.addColumn(col);



                                carritoIn.stream().forEach((Properties prop)->{
                                    Integer idProd = -1;
                                    idProd = Integer.parseInt(prop.getProperty("ProductoID"));
                                    dao = new ProductoDao();
                                    if(!((ProductoDao)dao).isPromoDisp(idProd) && !Constantes.USUARIOLOGUEADO.isIsPref()){
                                        dao = new ClienteDao();
                                        modelUpdate.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                    }else{
                                        dao = new ClienteDao();
                                        Double precioOr = Double.parseDouble(prop.getProperty(precio_unitario));
                                        precioOr = precioOr - (precioOr*0.10);
                                        modelUpdate.addRow(new Object[]{precioOr.toString(),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                    }
                                });



                                tblProductos.setModel(modelUpdate);
                                TableColumnModel tcminternalUpdate = tblProductos.getColumnModel();
                                tcminternal.removeColumn( tcminternalUpdate.getColumn(2));
                                tcminternal.removeColumn( tcminternalUpdate.getColumn(2) );
                                
                                int diff;
                                //Decorando
                                diff = viejaCantidad.intValue() - (int)Math.round(Double.parseDouble(spin.getValue().toString()));
                                if(diff>0){
                                    this.carrito = new Item(this.carrito,diff,"",(-1*precioCombo));
                                }else if(diff<0){
                                    this.carrito = new Item(this.carrito,(diff*-1),"",precioCombo);
                                }else{
                                    this.carrito = new Item(this.carrito,viejaCantidad.intValue(),"",(-1*precioCombo));
                                }
                                
                               txtSubTotal.setText(String.valueOf(this.carrito.costo()));
                               txtTotal.setText(String.valueOf(this.carrito.costo()+(this.carrito.costo()*0.13)));
                                
                        }
                    }

                   
                }
            });
           //</editor-fold>
           
            //<editor-fold defaultstate="collapsed" desc="Eliminar">
            btnDelete.addActionListener((ActionEvent e) -> {
                if(tblProductos.getSelectedRow()!=-1){
                    Double idProductoIn = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 2).toString()); 
                    Double idCombo  = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 3).toString());
                    Double Del = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 4).toString());
                    Double viejaCantidad = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 4).toString());
                    int diff;
                    if(idCombo==0){
                        //Eliminar producto
                       int result = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este producto del carrito?", "Sys", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,new ImageIcon(""));
                       if(result == JOptionPane.YES_OPTION){
                           //Eliminar item del carrito
                           dao = new ClienteDao();
                           ((ClienteDao)dao).update_or_delete_Item(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.Id, Del.intValue());
                           JOptionPane.showMessageDialog(null, "Se ha eliminado el item del carrito exitosamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                            
                            //Decorando
                            Double precioProducto = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 0).toString());
                            this.carrito = new Item(this.carrito,viejaCantidad.intValue(),"",(-1*precioProducto));
                            
                            txtSubTotal.setText(String.valueOf(this.carrito.costo()));
                            txtTotal.setText(String.valueOf(this.carrito.costo()+(this.carrito.costo()*0.13)));                           
                            
                            //Actualizar la tabla
                            //Actualizar table y el fucking decorador del carrito
                            dao = new ClienteDao();
                            List<Properties> carritoIn = ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id);
                            DefaultTableModel modelUpdate = new DefaultTableModel(){
                               @Override
                                public boolean isCellEditable(int row,int column){
                                    return false;
                                }
                            };
                            //Cargando modelo 
                            for(String col: carritoIn.get(0).stringPropertyNames())
                                modelUpdate.addColumn(col);



                            carritoIn.stream().forEach((Properties prop)->{
                                Integer idProd = -1;
                                idProd = Integer.parseInt(prop.getProperty("ProductoID"));
                                dao = new ProductoDao();
                                if(!((ProductoDao)dao).isPromoDisp(idProd) && !Constantes.USUARIOLOGUEADO.isIsPref()){
                                    dao = new ClienteDao();
                                    modelUpdate.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                }else{
                                    dao = new ClienteDao();
                                    Double precioOr = Double.parseDouble(prop.getProperty(precio_unitario));
                                    precioOr = precioOr - (precioOr*0.10);
                                    modelUpdate.addRow(new Object[]{String.format("%,.2f", precioOr),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                }
                            });



                            tblProductos.setModel(modelUpdate);
                            TableColumnModel tcminternalUpdate = tblProductos.getColumnModel();
                            tcminternal.removeColumn( tcminternalUpdate.getColumn(2));
                            tcminternal.removeColumn( tcminternalUpdate.getColumn(2) );
                            

                       }
                    }else{
                      //Eliminar combo   
                       int result = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este combo del carrito?", "Sys", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,new ImageIcon(""));
                       if(result == JOptionPane.YES_OPTION){
                           //Eliminar item del carrito
                           dao = new ClienteDao();
                           viejaCantidad = Double.parseDouble(String.valueOf(((ClienteDao)dao).get_cantidad_Combo(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.getId(), idCombo.intValue())));
                           ((ClienteDao)dao).update_or_delete_Combo(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.Id, idCombo.intValue(),viejaCantidad.intValue());
                           JOptionPane.showMessageDialog(null, "Se ha eliminado el item del carrito exitosamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png")); 
                           Double precioCombo = ((ClienteDao)dao).precioCombo_byID(idCombo.intValue());
                            //Decorando
                            Double precioProducto = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 0).toString());
                            
                            this.carrito = new Item(this.carrito,viejaCantidad.intValue(),"",(-1*precioCombo));
                            
                            txtSubTotal.setText(String.valueOf(this.carrito.costo()));
                            txtTotal.setText(String.valueOf(this.carrito.costo()+(this.carrito.costo()*0.13)));                           
                            
                            //Actualizar la tabla
                            //Actualizar table y el fucking decorador del carrito
                            List<Properties> carritoIn = ((ClienteDao)dao).getCarritoCliente(Constantes.USUARIOLOGUEADO.Id);
                            DefaultTableModel modelUpdate = new DefaultTableModel(){
                               @Override
                                public boolean isCellEditable(int row,int column){
                                    return false;
                                }
                            };
                            //Cargando modelo 
                            for(String col: carritoIn.get(0).stringPropertyNames())
                                modelUpdate.addColumn(col);



                            carritoIn.stream().forEach((Properties prop)->{
                                Integer idProd = -1;
                                idProd = Integer.parseInt(prop.getProperty("ProductoID"));
                                dao = new ProductoDao();
                                if(!((ProductoDao)dao).isPromoDisp(idProd) && !Constantes.USUARIOLOGUEADO.isIsPref()){
                                    dao = new ClienteDao();
                                    modelUpdate.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                }else{
                                    dao = new ClienteDao();
                                    Double precioOr = Double.parseDouble(prop.getProperty(precio_unitario));
                                    precioOr = precioOr - (precioOr*0.10);
                                    modelUpdate.addRow(new Object[]{precioOr.toString(),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});   
                                }
                            });



                            tblProductos.setModel(modelUpdate);
                            TableColumnModel tcminternalUpdate = tblProductos.getColumnModel();
                            tcminternal.removeColumn( tcminternalUpdate.getColumn(2));
                            tcminternal.removeColumn( tcminternalUpdate.getColumn(2) );
                        }
                    }
                }
            });
           //</editor-fold>
           
            //<editor-fold defaultstate="collapsed" desc="Salir">
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
            btCerrar.setText("Salir");
            
            btCerrar.addActionListener((ActionEvent e) -> {
                dialog.dispose();
            });
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Ver detalle">
                btnCombo.addActionListener((ActionEvent e) -> {
                    if(tblProductos.getSelectedRow()!=-1){
                        Double idProductoIn = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 2).toString()); 
                        Double idCombo  = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 3).toString());
                        if(idCombo.intValue()!=0){
                            dao = new ClienteDao();
                            String message = ((ClienteDao)dao).get_combo_contenido(idCombo.intValue());
                            String append  = "Cantidad : "+String.valueOf(((ClienteDao)dao).get_cantidad_Combo(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.getId(), idCombo.intValue()));
                            JOptionPane.showMessageDialog(null, message+"\n"+append, "Contenido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-more-details-24.png"));
                        }
                    }
                });
            //</editor-fold>
           
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
         
    }
    
    public void menuClienteVerCombos(){
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            List<Properties> inventario = new ArrayList<>();
            JPanel pnlBack,pnlSearchBar,pnlActionbar;
            JTable tblCombos = new JTable();
            JLabel lblSearch = new JLabel("Buscar : ");
            JTextField txtBuscar = new JTextField();
            JButton btnActualizar = new JButton("Actualizar");
            JButton btnAdd = new JButton("Agregar al carrito");
            JButton btnSeeSc = new JButton("Ver detalle");
            //Modelo base
            DefaultTableModel model = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            
            //Cargando Columnas al modelo
            dao = new ComboDao();
            List<ArmaCombos> lstCombosArm = ((ComboDao)dao).getAll();
            for (Field f : lstCombosArm.get(0).getClass().getDeclaredFields()) {
                model.addColumn(f.getName());
            }
            //Cargando filas al modelo
            for(ArmaCombos arm  : lstCombosArm){
                model.addRow(new Object[]{arm.getId(),arm.getNombre(),arm.getPrecio(),arm.activado,arm.borrado});
            }
            
             
            tblCombos.setModel(model);
            TableColumnModel tcm = tblCombos.getColumnModel();
            tcm.removeColumn( tcm.getColumn(4) );
            tcm.removeColumn( tcm.getColumn(3) );
            tcm.removeColumn( tcm.getColumn(0) );
            
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
            pnlActionbar = new JPanel(new GridLayout(1,3));
            pnlActionbar.add(btnAdd);
            pnlActionbar.add(btnSeeSc);

            
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlSearchBar,BorderLayout.NORTH);
            pnlBack.add(new JScrollPane(tblCombos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
            pnlBack.add(pnlActionbar,BorderLayout.SOUTH);
            
            //Ventana principal
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Combos");
            opt.setVisible(true);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/cliente.png");

            JDialog dialog = opt.createDialog(null, opt.getName());
            dialog.setIconImage(icon);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(600, 400);
            dialog.setResizable(true);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica" >
            //<editor-fold defaultstate="collapsed" desc="Agregar al carrito">
            btnAdd.addActionListener((ActionEvent e) -> {
                if(tblCombos.getSelectedRow()!=-1){
                        int idCombo;
                        //Desplegar un JSpinner para pedirle al cliente la cantidad de combos
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
                        
                        //Double idProdTemp= Double.parseDouble(tblCombos.getModel().getValueAt(tblCombos.getSelectedRow(),3).toString());
                        
                        //idProducto = idProdTemp.intValue();
                        
                        int idUser  = Constantes.USUARIOLOGUEADO.getId();
                        int result = JOptionPane.showConfirmDialog(null, componentes,"Seleccione una cantidad" ,JOptionPane.YES_NO_OPTION);
                        
                        if(result == JOptionPane.YES_OPTION){
                            //Procedo a revisar el stock de eso que desea agregar
                            dao = new ClienteDao();
                            Double idComboIn = Double.parseDouble(tblCombos.getModel().getValueAt(tblCombos.getSelectedRow(),0).toString());
                            ((ClienteDao)dao).insertar_Combo_Carrito(Constantes.USUARIOLOGUEADO.getId(),idComboIn.intValue() , (int)Math.round(Double.parseDouble(spin.getValue().toString())));
                          this.carrito = new Item(this.carrito,(int)Math.round(Double.parseDouble(spin.getValue().toString())),"",((ClienteDao)dao).precioCombo_byID(idComboIn.intValue()));
                        }
                }
                        
            });
            //</editor-fold>
                      
            //<editor-fold defaultstate="collapsed" desc="Ver contenido">
            btnSeeSc.addActionListener((ActionEvent e) -> {
                if(tblCombos.getSelectedRow()!=-1){
                    //Abrir JOptionPane con icono y el contenido 
                    dao = new ClienteDao();
                     Double idComboInCon = Double.parseDouble(tblCombos.getModel().getValueAt(tblCombos.getSelectedRow(),0).toString());
                    String message =  ((ClienteDao)dao).get_combo_contenido(idComboInCon.intValue());
                    JOptionPane.showMessageDialog(null, message,"Contenido",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-more-details-24.png"));
                }
            });
            
            //</editor-fold>
            
            txtBuscar.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if(Character.isDigit(e.getKeyChar()))
                        e.consume();
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //Modelo base
                    DefaultTableModel model = new DefaultTableModel(){
                    @Override
                        public boolean isCellEditable(int row,int column){
                            return false;
                        }
                    };
                    
                    //Cargando Columnas al modelo
                    dao = new ComboDao();
                    List<ArmaCombos> lstCombosArm = ((ComboDao)dao).getAll();
                    List<ArmaCombos> lstCombosArmFiltered = new ArrayList<>();
                    
                    
                    lstCombosArm.stream().filter((ArmaCombos x)-> x.nombre.contains(txtBuscar.getText())).forEach((ArmaCombos z) ->{
                        lstCombosArmFiltered.add(z);
                    });
                    
                    for (Field f : lstCombosArm.get(0).getClass().getDeclaredFields()) {
                        model.addColumn(f.getName());
                    }
                    //Cargando filas al modelo
                    for(ArmaCombos arm  : lstCombosArmFiltered){
                        model.addRow(new Object[]{arm.getId(),arm.getNombre(),arm.getPrecio(),arm.activado,arm.borrado});
                    }


                    tblCombos.setModel(model);
                    TableColumnModel tcm = tblCombos.getColumnModel();
                    tcm.removeColumn( tcm.getColumn(4) );
                    tcm.removeColumn( tcm.getColumn(3) );
                    tcm.removeColumn( tcm.getColumn(0) );
                }
            });
            
            //<editor-fold defaultstate="collapsed" desc="Salir">
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
            btCerrar.setText("Salir");
            
            btCerrar.addActionListener((ActionEvent e) -> {
                dialog.dispose();
            });
            //</editor-fold>
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
    }
    
    
     public  void clienteVerUltimaOrden(int idCliente,Optional op){
        List<Properties> carritoVer = new ArrayList<>();
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            JPanel pnlBack,pnlSearchBar,pnlActionbar,pnlActionContent,pnlSubtotal,pnlTotal;
            JTable tblProductos = new JTable();
            JLabel lblSubTotal  = new JLabel("Subtotal : ");
            JLabel lblTotal = new JLabel("Total : ");
            JTextField txtSubTotal= new JTextField();
            JTextField txtTotal = new JTextField();
            txtSubTotal.setEditable(false);
            txtTotal.setEditable(false);
            JPanel pnlTblMods;
            JPanel pnlBtnMod;
            JButton btnDelete = new JButton("Eliminar");
            JButton btnUpdate = new JButton("Modificar");
            JButton btnCombo = new JButton("Ver contenido");
            //Modelo base
            DefaultTableModel model = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            

            btnUpdate.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/expediente.png"));
            btnDelete.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/borrar.png"));
            
            dao = new ClienteDao();
            carritoVer = ((ClienteDao)dao).getUltimaOrden(Constantes.USUARIOLOGUEADO.Id,op);
            
            //Cargando modelo 
            for(String col: carritoVer.get(0).stringPropertyNames())
                model.addColumn(col);


            
            carritoVer.stream().forEach((Properties prop)->{
                model.addRow(new Object[]{prop.getProperty(precio_unitario),prop.getProperty(producto),prop.getProperty("ProductoID"),prop.getProperty("isComboID"),prop.getProperty(cantidad),prop.getProperty(monto), ((ClienteDao)dao).getComboByID(Integer.parseInt(prop.getProperty("isCombo")))});
            });
            

            
            tblProductos.setModel(model);
            TableColumnModel tcminternal = tblProductos.getColumnModel();
            tcminternal.removeColumn( tcminternal.getColumn(2));
            tcminternal.removeColumn( tcminternal.getColumn(2) );
           

            
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
            
            //Panel de los botones
            pnlBtnMod = new JPanel(new BorderLayout());
            pnlBtnMod.add(btnCombo,BorderLayout.CENTER);
            
            
            //Panel de la tabla y los botones de modificacion
            pnlTblMods  = new JPanel(new BorderLayout());
            pnlTblMods.add(new JScrollPane(tblProductos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
            pnlTblMods.add(pnlBtnMod,BorderLayout.SOUTH);
            
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlTblMods,BorderLayout.CENTER);
            pnlBack.add(pnlActionbar,BorderLayout.SOUTH);
            
            //Ventana principal
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Ordenes");
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
        Double subtotal =0.0;
        dao = new OrdenDao();
        if(op.isPresent())            
            subtotal = ((OrdenDao)dao).geTotalOrden(Integer.parseInt(op.get().toString()));
        else
            subtotal = ((OrdenDao)dao).getTotalUltimaOrden( idCliente); 
        
        txtTotal.setText(String.format( "%,.2f", subtotal + (subtotal * 0.13)));
        txtSubTotal.setText(String.format("%,.2f", subtotal)); 
            
            //<editor-fold defaultstate="collapsed" desc="Salir">
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
            btCerrar.setText("Salir");
            
            btCerrar.addActionListener((ActionEvent e) -> {
                dialog.dispose();
            });
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Ver detalle">
                btnCombo.addActionListener((ActionEvent e) -> {
                    if(tblProductos.getSelectedRow()!=-1){
                        Double idProductoIn = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 2).toString()); 
                        Double idCombo  = Double.parseDouble(tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 3).toString());
                        if(idCombo.intValue()!=0){
                            dao = new ClienteDao();
                            String message = ((ClienteDao)dao).get_combo_contenido(idCombo.intValue());
                            String append  = "Cantidad : "+String.valueOf(((ClienteDao)dao).get_cantidad_Combo(idProductoIn.intValue(), Constantes.USUARIOLOGUEADO.getId(), idCombo.intValue()));
                            JOptionPane.showMessageDialog(null, message+"\n"+append, "Contenido", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-more-details-24.png"));
                        }
                    }
                });
            //</editor-fold>
           
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
         
    }
    public void menuClientePass(){
    //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
    JTextField txtPassword = new JTextField();
    JLabel lblPassword = new JLabel("Contraseña : ");
    JButton btnPassAceptar = new JButton("Guardar");
    JPanel panlBack = new JPanel();
    JPanel pnlButtons = new JPanel();
    JPanel pnlPassword = new JPanel();
    JButton btnResetPass = new JButton("Revertir cambio");
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="UI">
    pnlButtons.setSize(10,25);
    pnlButtons.setLayout(new GridLayout(2,1));
    pnlButtons.add(btnPassAceptar);
    pnlButtons.add(btnResetPass);
    
    
    pnlPassword.setSize(10,25);
    pnlPassword.setLayout(new BorderLayout());
    pnlPassword.add(lblPassword,BorderLayout.BEFORE_FIRST_LINE);
    pnlPassword.add(txtPassword,BorderLayout.AFTER_LAST_LINE);
    
    panlBack.setLayout(new BorderLayout());
    panlBack.setSize(200, 150);
    panlBack.add(pnlPassword,BorderLayout.PAGE_START);
    panlBack.add(pnlButtons,BorderLayout.AFTER_LAST_LINE);
    JComponent[] component = new JComponent[]{panlBack};
    
    JOptionPane opt = new JOptionPane();
    opt.setMessage(component);
    opt.setName("Cambio de Contraseña");
    opt.setVisible(true);
    Toolkit kit = Toolkit.getDefaultToolkit();
    Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/passcambio.png");
    JDialog dialog = opt.createDialog(null, "Cambio de Contraseña");
    dialog.setIconImage(icon);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(200, 155);
    dialog.setResizable(false);
    JButton btCerrar = opt.getRootPane().getDefaultButton(); 
    btCerrar.setLabel("Cerrar");
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Logica" >
    int idUser  = Constantes.USUARIOLOGUEADO.getId();
    btnResetPass.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dao = new ClienteDao();
            if(((ClienteDao)dao).passChanged(Constantes.USUARIOLOGUEADO.Id)){
                int result = -1;
                result = JOptionPane.showConfirmDialog(null, "Desea restaurar su antigua contraseña ? ","Sys", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(result==JOptionPane.YES_OPTION){
                    dao = new ClienteDao();
                    Constantes.MEMENTOS = new ArrayList();
                    ((ClienteDao)dao).login(Constantes.USUARIOLOGUEADO.getNombreUsuario(), Constantes.USUARIOLOGUEADO.getPassword());

                    ((ClienteDao)dao).update(Constantes.USUARIOLOGUEADO, new String[]{});
                    JOptionPane.showMessageDialog(null, "Contraseña Actualizada","Sys",JOptionPane.INFORMATION_MESSAGE);
                     btnResetPass.setEnabled(false);
                }
            }else{
                JOptionPane.showMessageDialog(null, "No se han realizado cambios","Sys",JOptionPane.ERROR_MESSAGE);
                btnResetPass.setEnabled(false);
            }
        }
    });
    btnPassAceptar.addActionListener((ActionEvent e) -> {
      //Guardar Contraseña Nueva
    if(txtPassword.getText().length()==0){
        JOptionPane.showMessageDialog(null, "Por favor ingrese nueva contraseña");
    }else{
        dao = new ClienteDao();
        ((ClienteDao)dao).cambiarPassword(txtPassword.getText(), idUser);
         btnResetPass.setEnabled(true);
        
        ((ClienteDao)dao).login(Constantes.USUARIOLOGUEADO.getNombreUsuario(), Constantes.USUARIOLOGUEADO.getPassword());
        
        JOptionPane.showMessageDialog(null, "Contraseña Actualizada");
    } 
     
    });
    //</editor-fold>
    
    //Arranque
    dialog.setVisible(true);
    }
    
}
