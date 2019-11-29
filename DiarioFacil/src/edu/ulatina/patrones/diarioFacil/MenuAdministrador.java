/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nvidi
 */
public class MenuAdministrador implements IMenu {
    public static Dao dao;
    
    @Override
    public void mostrarMenu(){
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JPanel pnlBack = new JPanel( new GridLayout(7,1));
        JButton btnAdminUser = new JButton("Usuarios");
        JButton btnAdminProveedores = new JButton("Proveedores");
        JButton btnAdminCat = new JButton("Categorias");
        JButton btnAdminProd = new JButton("Productos");        
        JButton btnAdminCombo = new JButton("Combos");
        JButton btnAdminBuscarOrd = new JButton("Buscar Orden");        
        JButton btnAdminReporte = new JButton("Reporte");
        
        btnAdminUser.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-select-users-30.png"));
        btnAdminProveedores.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-trolley-30.png"));
        btnAdminCat.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-categorize-30.png"));
        btnAdminProd.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/Productos.png"));
        btnAdminCombo.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-packaging-30.png"));
        btnAdminBuscarOrd.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/buscarOrden.png"));
        btnAdminReporte.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/Reporte.png"));
        
        pnlBack.add(btnAdminUser);
        pnlBack.add(btnAdminProveedores);
        pnlBack.add(btnAdminCat);
        pnlBack.add(btnAdminProd);        
        pnlBack.add(btnAdminCombo);
        pnlBack.add(btnAdminBuscarOrd);
        pnlBack.add(btnAdminReporte);

        
        JComponent[] component = new JComponent[]{pnlBack};
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Administrador");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-database-administrator-24.png");
        //opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        JDialog dialog = opt.createDialog(null, opt.getName());
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(350, 400);
        dialog.setResizable(true);
        JButton btCerrar = opt.getRootPane().getDefaultButton(); 
        btCerrar.setLabel("Cerrar");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
        btnAdminUser.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);
            //Llamar la ui de administracion de usuarios
            this.menuAdminUserMenu();
            dialog.setVisible(true);
        });
        
        btnAdminProveedores.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);
            //Llamar la ui de administracion de Proveedores
            MenuProveedores proveedores=new MenuProveedores();
            proveedores.menu();
            dialog.setVisible(true);
        });
        
        btnAdminCat.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);
            //Llamar la ui de administracion de Categorias
            MenuCategorias categorias=new MenuCategorias();
            categorias.menu();
            dialog.setVisible(true);
        });
        
        btnAdminProd.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);
            //Llamar la ui de administracion de Producto
            MenuProducto producto=new MenuProducto();
            producto.menu();
            dialog.setVisible(true);
        });
        
        btnAdminCombo.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);
            //Llamar la ui de administracion de Producto
            MenuCombos combos=new MenuCombos();
            combos.menu();
            dialog.setVisible(true);
        });
        
        btnAdminReporte.addActionListener((ActionEvent e)->{
            dialog.setVisible(false);
            ReporteGeneral reporte=new ReporteGeneral();
            reporte.verOrdenes();
            dialog.setVisible(true);
    });
        //</editor-fold>
        //Arranque
        dialog.setVisible(true);
    }
    public void menuAdminUserMenu(){
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JPanel pnlBack = new JPanel( new GridLayout(4, 1));
        JButton btnInsertar = new JButton("Insertar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnDesactivar = new JButton("Desactivar");
        JButton btnVer = new JButton("Ver registro");
        pnlBack.add(btnInsertar);
        pnlBack.add(btnModificar);
        pnlBack.add(btnDesactivar);
        pnlBack.add(btnVer);
        btnInsertar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-save-as-16.png"));
        btnVer.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-google-web-search-16.png"));
        btnModificar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-edit-16.png"));
        JComponent[] component = new JComponent[]{pnlBack};
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Administrador");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-database-administrator-24.png");
        //opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        JDialog dialog = opt.createDialog(null, "DiarioFacil-Login");
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(350, 300);
        dialog.setResizable(true);
        JButton btCerrar = opt.getRootPane().getDefaultButton(); 
        btCerrar.setLabel("Cerrar");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
            //<editor-fold defaultstate="collapsed" desc="Insertar">
            btnInsertar.addActionListener((ActionEvent e) -> {
                dialog.setVisible(false);
                menuAdminUserInsert();
                dialog.setVisible(true);
            });
            btnVer.addActionListener((ActionEvent e) -> {
                dialog.setVisible(false);
                menuAdminUserVer();
                dialog.setVisible(true);
            });
            btnModificar.addActionListener((ActionEvent e) -> {
                dialog.setVisible(false);
                menuAdminUserUpdate();
                dialog.setVisible(true);
            });
            //</editor-fold>
        //</editor-fold>
        //Arranque
        dialog.setVisible(true);
        
    }
    
    public void menuAdminUserVer(){
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            dao = new ClienteDao();
            //PnlSearch 
            JTextField txtSearch = new JTextField();
            JButton btnReload  = new JButton("Actualizar");
            JLabel lblseacrh  = new JLabel("Buscar : ");
            JPanel pnlSearchBar;
            btnReload.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-update-16.png"));
            
            pnlSearchBar = new JPanel(new BorderLayout());
            pnlSearchBar.setSize(750, 20);
            pnlSearchBar.add(lblseacrh,BorderLayout.WEST);
            pnlSearchBar.add(txtSearch,BorderLayout.CENTER);
            pnlSearchBar.add(btnReload,BorderLayout.EAST);
            
            JPanel pnlBack = new JPanel();
            JTable tblUsersClientes = new JTable();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo  = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            modelo.addColumn("ID_usuario");
            modelo.addColumn("Nombre Usuario");
            modelo.addColumn("Email ");
            modelo.addColumn("Contraseña");
            modelo.addColumn("Es cliente preferencial");
            modelo.addColumn("Desactivado");
            
            List<Cliente>  lstCliente = dao.getAll();
            for (Cliente c : lstCliente) {
                modelo.addRow(new Object[]{c.getId(),c.getNombreUsuario(),c.getEmailUsuario(),c.getPassword(),c.isIsPref(),c.isIsActive()});
            }
            tblUsersClientes.setModel(modelo);
            pnlBack = new JPanel(new BorderLayout());
            pnlBack.add(pnlSearchBar,BorderLayout.NORTH);
            pnlBack.add(new JScrollPane(tblUsersClientes,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
            
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Administrador");
            opt.setVisible(true);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-database-administrator-24.png");
            //opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
            JDialog dialog = opt.createDialog(null, "DiarioFacil-Ver Clientes");
            dialog.setIconImage(icon);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(750, 300);
            dialog.setResizable(true);
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
            btCerrar.setLabel("Cerrar");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
            txtSearch.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if(!Character.isDigit(e.getKeyChar()))
                        e.consume();
                    }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyReleased(KeyEvent e) {
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Optional<String> text = Optional.ofNullable(txtSearch.getText());
                    dao = new ClienteDao();
                    List<Cliente> filter = dao.getAll();
                    List<Cliente> filtered = new ArrayList<>();
                    if(!"".equals(text.get())){
                        text.ifPresent((String c) ->{
                            filter.stream().filter((x)-> x.Id==Integer.parseInt(text.get())).forEach((Cliente cl)->{
                            filtered.add(cl);
                            }); 
                        });
                        DefaultTableModel modelFiltered = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row,int column){
                                return false;
                            }   
                        };
                        modelFiltered.addColumn("ID_usuario");
                        modelFiltered.addColumn("Nombre Usuario");
                        modelFiltered.addColumn("Email ");
                        modelFiltered.addColumn("Contraseña");
                        modelFiltered.addColumn("Es cliente preferencial");
                        modelFiltered.addColumn("Desactivado");
                        filtered.forEach((Cliente c) -> {
                            modelFiltered.addRow(new Object[]{c.Id,c.nombreUsuario,c.emailUsuario,c.password,c.isIsPref(),c.isIsActive()});
                        });
                        tblUsersClientes.setModel(modelFiltered);
                    }else{
                        List<Cliente> original = dao.getAll();
                        //Reload original data
                        DefaultTableModel modelFiltered = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row,int column){
                                return false;
                            }   
                        };
                        modelFiltered.addColumn("ID_usuario");
                        modelFiltered.addColumn("Nombre Usuario");
                        modelFiltered.addColumn("Email ");
                        modelFiltered.addColumn("Contraseña");
                        modelFiltered.addColumn("Es cliente preferencial");
                        modelFiltered.addColumn("Desactivado");
                        original.forEach((Cliente c) -> {
                            modelFiltered.addRow(new Object[]{c.Id,c.nombreUsuario,c.emailUsuario,c.password,c.isIsPref(),c.isIsActive()});
                        });
                        tblUsersClientes.setModel(modelFiltered);
                        
                    }
                }
            });
            
            btnReload.addActionListener((ActionEvent e) -> {
                List<Cliente> original = dao.getAll();
                //Reload original data
                DefaultTableModel modelFiltered = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row,int column){
                        return false;
                    }   
                };
                modelFiltered.addColumn("ID_usuario");
                modelFiltered.addColumn("Nombre Usuario");
                modelFiltered.addColumn("Email ");
                modelFiltered.addColumn("Contraseña");
                modelFiltered.addColumn("Es cliente preferencial");
                modelFiltered.addColumn("Desactivado");
                original.forEach((Cliente c) -> {
                    modelFiltered.addRow(new Object[]{c.Id,c.nombreUsuario,c.emailUsuario,c.password,c.isIsPref(),c.isIsActive()});
                });
                tblUsersClientes.setModel(modelFiltered);
            });
           
            
        //</editor-fold>
        //Arranque
        dialog.setVisible(true);
    }
    
    public void menuAdminUserInsert(){
       //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JPanel pnlBack = new JPanel();
        JPanel pnlUserName = new JPanel();
        JPanel pnlEmail = new JPanel();
        JPanel pnlPassword = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JLabel lblUserName = new JLabel("Usuario : ");
        JLabel lblemail = new JLabel("Email : ");
        JLabel lblePass = new JLabel("Contraseña : ");
        JTextField txtUserName = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        btnGuardar.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-save-as-16.png"));
        //pnal userName
        pnlUserName = new JPanel(new BorderLayout());
        pnlUserName.add(lblUserName,BorderLayout.WEST);
        pnlUserName.add(txtUserName,BorderLayout.CENTER);
        
        //pnlEmail
        pnlEmail = new JPanel(new BorderLayout());
        pnlEmail.add(lblemail,BorderLayout.WEST);
        pnlEmail.add(txtEmail,BorderLayout.CENTER);
        
        //pnlPassword
        pnlPassword = new JPanel(new BorderLayout());
        pnlPassword.add(lblePass,BorderLayout.WEST);
        pnlPassword.add(txtPassword,BorderLayout.CENTER);
        
        pnlBack = new JPanel(new GridLayout(4, 1));
        pnlBack.add(pnlUserName);
        pnlBack.add(pnlEmail);
        pnlBack.add(pnlPassword);
        pnlBack.add(btnGuardar);
        
        JComponent[] component = new JComponent[]{pnlBack};
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Administrador");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-database-administrator-24.png");
        //opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        JDialog dialog = opt.createDialog(null, "DiarioFacil-Insertar usuario");
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 200);
        dialog.setResizable(true);
       
      
        //</editor-fold>
       //<editor-fold defaultstate="collapsed" desc="Logica">
        
            btnGuardar.addActionListener((ActionEvent e) -> {
                if(!txtUserName.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtPassword.getText().isEmpty()){
                    dao = new ClienteDao();
                    dao.save(new Cliente(txtEmail.getText(),txtPassword.getText(),txtUserName.getText(),true,false));
                    //Mensaje de exito y limpieza de campos
                    txtUserName.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    JOptionPane.showMessageDialog(null, "Usuario-cliente ingresado con exito", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-verificado-24.png"));
                }else{
                    //Mensaje de error
                      JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                }
            });
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
    }
    public void menuAdminUserUpdate(){
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
            JRadioButton rsIsActivo = new JRadioButton("Eliminado            ");
            JRadioButton rsIsPref = new JRadioButton("Cliente preferencial");
            JPanel pnlBack ;
            JPanel pnlIdMod;
            JPanel pnlnuevoNombre;
            JPanel pnlnuevoEmail;
            JPanel pnlnuevaPassword ;
            JLabel lblIdMod = new JLabel("ID del registro a modificar : ");
            JLabel lblNuevoNombre = new JLabel("Ingrese el nuevo nombre : " );
            JLabel lblNuevoEmail = new JLabel("Ingrese el nuevo email : " );
            JLabel lblNuevaContra = new JLabel("Ingrese la nueva contraseña : " );
            JSpinner spinIdMod ;
            JTextField txtNuevoNombre = new JTextField();
            JTextField txtNuevoEmail = new JTextField();
            JPasswordField txtNuevaContra = new JPasswordField();
            SpinnerNumberModel model1 = new SpinnerNumberModel(1.0, 1.0, 10000000000.0, 1.0);
            spinIdMod = new JSpinner(model1);
            JButton btnEdit = new JButton("Editar");
            JButton btnSee= new JButton(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
            btnEdit.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-edit-16.png"));
            JButton  btnCargarInfodelId = new JButton("Cargar datos del ID");
            btnCargarInfodelId.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-load-cargo-16.png"));
           

           //PnlIdMod
           pnlIdMod  = new JPanel(new BorderLayout());
           pnlIdMod.add(lblIdMod,BorderLayout.WEST);
           pnlIdMod.add(spinIdMod,BorderLayout.CENTER);
           
           //pnlnuevoNombre
           pnlnuevoNombre = new JPanel(new BorderLayout());
           pnlnuevoNombre.add(lblNuevoNombre,BorderLayout.WEST);
           pnlnuevoNombre.add(txtNuevoNombre,BorderLayout.CENTER);
           
           //pnlnuevoEmail
           pnlnuevoEmail = new JPanel(new BorderLayout());
           pnlnuevoEmail.add(lblNuevoEmail,BorderLayout.WEST);
           pnlnuevoEmail.add(txtNuevoEmail,BorderLayout.CENTER);
           
           //pnlnuevoPassword
           pnlnuevaPassword = new JPanel(new BorderLayout());
           pnlnuevaPassword.add(lblNuevaContra,BorderLayout.WEST);
           pnlnuevaPassword.add(txtNuevaContra,BorderLayout.CENTER);
           pnlnuevaPassword.add(btnSee,BorderLayout.EAST);
           
           pnlBack = new JPanel(new GridLayout(8,1));
           pnlBack.add(pnlIdMod);
           pnlBack.add(pnlnuevoNombre);
           pnlBack.add(pnlnuevoEmail);
           pnlBack.add(pnlnuevaPassword);
           pnlBack.add(rsIsActivo);
           pnlBack.add(rsIsPref);
           pnlBack.add(btnCargarInfodelId);
           pnlBack.add(btnEdit);
           
           
            JComponent[] component = new JComponent[]{pnlBack};
        
            JOptionPane opt = new JOptionPane();
            opt.setMessage(component);
            opt.setName("DiarioFacil-Administrador");
            opt.setVisible(true);
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-database-administrator-24.png");
            //opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
            JDialog dialog = opt.createDialog(null, "DiarioFacil-Modificar usuario");
            dialog.setIconImage(icon);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(400, 290);
            dialog.setResizable(true);
            JButton btCerrar = opt.getRootPane().getDefaultButton(); 
            btCerrar.setLabel("Cerrar");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Logica">
            btnSee.addActionListener((ActionEvent e)->{
                if(txtNuevaContra.getEchoChar()==0){
                    txtNuevaContra.setEchoChar('\u25CF');
                }else{
                    txtNuevaContra.setEchoChar((char)0);
                }
            });
            
            btnEdit.addActionListener((ActionEvent e) -> {
                if(!txtNuevaContra.getText().isEmpty() && !txtNuevoEmail.getText().isEmpty() && !txtNuevoNombre.getText().isEmpty() ){
                    Double spinnerClean = Double.parseDouble(spinIdMod.getValue().toString());
                    int id = spinnerClean.intValue();
                    dao = new ClienteDao();
                    Optional<Cliente> cliente = dao.get(id);
                    cliente.ifPresent((Cliente c)->{
                        //Logica de modificado
                        boolean isBorrado=false,isPref=false;
                        if(rsIsActivo.isSelected())
                            isBorrado=true;
                        if(rsIsPref.isSelected())
                            isPref = true;
                        dao.update(new Cliente(id,txtNuevoEmail.getText(),txtNuevaContra.getText(),txtNuevoNombre.getText(),isBorrado,isPref), new String[]{});
                        //Mensaje de exito
                        JOptionPane.showMessageDialog(null, "El usuario ha sido modificado con exito", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-verificado-24.png"));
                    });
                    if(!cliente.isPresent())
                        JOptionPane.showMessageDialog(null, "El ID ingresado no pertenece a ningun usuario", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                }else{
                    //Mensaje de error
                }
            });
            
            btnCargarInfodelId.addActionListener((ActionEvent e) -> {
               if(spinIdMod.getValue()!=null){
                   Double spinnValue = Double.parseDouble(spinIdMod.getValue().toString());
                   int id = spinnValue.intValue();
                   dao = new ClienteDao();
                   Optional<Cliente> cliente = dao.get(id);
                   if(!cliente.isPresent())
                       JOptionPane.showMessageDialog(null, "El ID ingresado no pertenece a ningun usuario", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                   cliente.ifPresent((Cliente c)->{
                       txtNuevoNombre.setText(c.getNombreUsuario());
                       txtNuevoEmail.setText(c.getEmailUsuario());
                       txtNuevaContra.setText(c.getPassword());
                       if(c.isActive)
                           rsIsActivo.setSelected(true);
                       else
                           rsIsActivo.setSelected(false);
                       if(c.isIsPref())
                           rsIsPref.setSelected(true);
                       else
                           rsIsPref.setSelected(false);
                   });
               }
            });
        //</editor-fold>
        
        //Arranque
        dialog.setVisible(true);
    }
    

    

    
}
