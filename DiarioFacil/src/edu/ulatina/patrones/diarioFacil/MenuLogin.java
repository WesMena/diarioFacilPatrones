/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.layout.Border;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Nvidi
 */
public class MenuLogin {
    
    public static Dao userDao;
    
    public void loginUI(){
        
        //<editor-fold defaultstate="collapsed" desc="Definicion de controles">
        JTextField txtUserNam = new JTextField();
        JButton btnLogCliente = new JButton("Cliente");
        JPanel pnlPasswordAndSee = new JPanel();
        JButton btnLogAdmin = new JButton("Admin   ");
        JButton btnRegister = new JButton("Registrarse");
        JPanel panlBack = new JPanel();
        JPanel pnlButtons = new JPanel();
        JPanel pnlUserName = new JPanel();
        JPanel pnlPassword = new JPanel();
        JLabel lblUserName = new JLabel("Nombre de usuario : "); 
        JLabel lblPassword = new JLabel("Contrasenia : "); 
        JPasswordField txtPassword = new JPasswordField("0000000");
        
        JButton btnSee = new JButton();
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Nombre de usuario">
        pnlUserName.setSize(50,50);
        pnlUserName.setLayout(new BorderLayout());
        pnlUserName.add(lblUserName,BorderLayout.BEFORE_FIRST_LINE);
        pnlUserName.add(txtUserNam,BorderLayout.AFTER_LAST_LINE);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Contrasenia">
        btnSee.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        pnlPasswordAndSee.setLayout(new BorderLayout());
        pnlPasswordAndSee.add(txtPassword,BorderLayout.CENTER);
        pnlPasswordAndSee.add(btnSee,BorderLayout.EAST);
        pnlPassword.setSize(50,50);
        pnlPassword.setLayout(new BorderLayout());
        pnlPassword.add(lblPassword,BorderLayout.BEFORE_FIRST_LINE);
        pnlPassword.add(pnlPasswordAndSee,BorderLayout.AFTER_LAST_LINE);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Login UI">
        pnlButtons.setSize(50,50);
        btnLogAdmin.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-administrador-8.png"));
        btnLogCliente.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-facturacion-8.png"));
        btnRegister.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-entrar-8.png"));
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(btnLogCliente,BorderLayout.EAST);
        pnlButtons.add(btnLogAdmin,BorderLayout.WEST);
        pnlButtons.add(btnRegister,BorderLayout.CENTER);

        
        panlBack.setLayout(new BorderLayout());
        panlBack.setSize(460, 200);
        panlBack.add(pnlUserName,BorderLayout.BEFORE_FIRST_LINE);
        panlBack.add(pnlPassword,BorderLayout.CENTER);
        panlBack.add(pnlButtons,BorderLayout.AFTER_LAST_LINE);
        JComponent[] component = new JComponent[]{panlBack};
       
        
        
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Login");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-login-24.png");
        opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        JDialog dialog = opt.createDialog(null, "DiarioFacil-Login");
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 200);
        dialog.setResizable(true);
        JButton btCerrar = opt.getRootPane().getDefaultButton(); 
        btCerrar.setLabel("Salir de Aplicación");
        
        btCerrar.addActionListener((ActionEvent e) -> {
            
            Conexion close = Conexion.getInstance();
            try{
            close.desconectar();
            }catch(NullPointerException a){
            } finally {
            System.exit(0);
            }
            
            
        });
        
        //<editor-fold defaultstate="collapsed" desc="Loguearse como cliente">
        btnLogCliente.addActionListener((ActionEvent e) -> {
            if(!txtPassword.getText().isEmpty() && !txtUserNam.getText().isEmpty()){
                userDao = new ClienteDao();
                if(((ClienteDao)userDao).login(txtUserNam.getText(),txtPassword.getText())){
                    //Login correcto
                    JOptionPane.showMessageDialog(null, "Login correcto", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png"));
                    dialog.setVisible(false);
                    //Cliente logueado
                    Constantes.USUARIOLOGUEADO = ((ClienteDao)userDao).getByPassAndUser(txtUserNam.getText(), txtPassword.getText());
                    //Abrir el menu de cliente
                    
                    Menu cliente = new ClienteLogin();
                    dialog.setVisible(false);
                    cliente.DesplegarMenu();
                    dialog.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Las credenciales son incorrectas", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los datos solicitados son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
            }
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Loguearse como administrador">
        btnLogAdmin.addActionListener((ActionEvent e) -> {
            userDao =  new AdminDao();
            if(!txtPassword.getText().isEmpty() && !txtUserNam.getText().isEmpty()){
                if(((AdminDao)userDao).login(txtUserNam.getText(), txtPassword.getText())){
                    JOptionPane.showMessageDialog(null, "Login correcto", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-ok-24.png"));
                    //Admin Logueado
                    
                    
                    //Abrir menu de admin
                    Menu admin = new AdminLogin();
                    dialog.setVisible(false);
                    admin.DesplegarMenu();
                    dialog.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Las credenciales son incorrectas", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los datos solicitados son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
            }
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Registrarse">
         btnRegister.addActionListener((ActionEvent e) -> {
             dialog.setVisible(false);
             this.registroUI();
             dialog.setVisible(true);
         });
         
         //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Ver contra">
        btnSee.addActionListener((ActionEvent e)->{
            if(txtPassword.getEchoChar()==0){
                txtPassword.setEchoChar('\u25CF');
            }else{
                txtPassword.setEchoChar((char)0);
            }
        });
        
        //</editor-fold>
         
        dialog.setVisible(true);
        //</editor-fold>
        
        
    }
    
    public void registroUI(){
        //<editor-fold defaultstate="collapsed" desc="Definicion y aspecto de controles">
         JPanel pnlBack = new JPanel();
         JPanel pnlUserName = new JPanel();
         JPanel pnlEmail = new JPanel();
         JPanel pnlPassword = new JPanel();
         JPanel pnlPasswordConfirm = new JPanel();
         JPanel pnlButtons = new JPanel();
         JLabel password = new JLabel("Contraseña : ");
         JLabel passwordConf = new JLabel("Confirma Contraseña : ");
         JLabel userName = new JLabel("Nombre de usuario : ");
         JLabel email =    new JLabel("Email : ");
         JTextField txtUserName = new JTextField();
         JTextField txtEmail = new JTextField();
         JPasswordField txtPass = new JPasswordField();
         JPasswordField txtPassConfirm = new JPasswordField();
         JButton btnRegistrar = new JButton( new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-login-24.png"));
         JPanel pnlPasswordAndSee = new JPanel();
         JPanel pnlPasswordAndSeeConfirm = new JPanel();
         JButton btnSee = new JButton(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
         JButton btnSeeConfirm = new JButton(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
         
        //<editor-fold defaultstate="collapsed" desc="Nombre de usuario">
         pnlUserName.setSize(50,50);
         pnlUserName.setLayout(new BorderLayout());
         pnlUserName.add(userName,BorderLayout.LINE_START);
         pnlUserName.add(txtUserName,BorderLayout.AFTER_LAST_LINE);
         
         //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Password">
         pnlPasswordAndSee.setLayout(new BorderLayout());
         pnlPasswordAndSee.add(txtPass,BorderLayout.CENTER);
         pnlPasswordAndSee.add(btnSee,BorderLayout.EAST);
         pnlPassword.setSize(50,50);
         pnlPassword.setLayout(new BorderLayout());
         pnlPassword.add(password,BorderLayout.LINE_START);
         pnlPassword.add(pnlPasswordAndSee,BorderLayout.AFTER_LAST_LINE);
         
         //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Password confirmacion">
         pnlPasswordAndSeeConfirm.setLayout(new BorderLayout());
         pnlPasswordAndSeeConfirm.add(txtPassConfirm,BorderLayout.CENTER);
         pnlPasswordAndSeeConfirm.add(btnSeeConfirm,BorderLayout.EAST);
         pnlPasswordConfirm.setSize(50,50);
         pnlPasswordConfirm.setLayout(new BorderLayout());
         pnlPasswordConfirm.add(passwordConf,BorderLayout.LINE_START);
         pnlPasswordConfirm.add(pnlPasswordAndSeeConfirm,BorderLayout.AFTER_LAST_LINE);
         //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Email">
         pnlEmail.setSize(50,50);
         pnlEmail.setLayout(new BorderLayout());
         pnlEmail.add(email,BorderLayout.LINE_START);
         pnlEmail.add(txtEmail,BorderLayout.AFTER_LAST_LINE);
         //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Buttons">
         btnRegistrar.setText("Registrarse");
         pnlButtons.setSize(50,50);
         pnlButtons.setLayout(new BorderLayout());
         pnlButtons.add(btnRegistrar,BorderLayout.CENTER);
        //</editor-fold>
         
        //<editor-fold defaultstate="collapsed" desc="Displat UI y Logica">
        pnlBack.setLayout(new GridLayout(5, 1));
        pnlBack.setSize(600, 600);
        pnlBack.add(pnlUserName );
        pnlBack.add(pnlEmail);
        pnlBack.add(pnlPassword);
        pnlBack.add(pnlPasswordConfirm);
        pnlBack.add(pnlButtons,BorderLayout.AFTER_LAST_LINE);
        JComponent[] component = new JComponent[]{pnlBack};
       
        
        
        
        JOptionPane opt = new JOptionPane();
        opt.setMessage(component);
        opt.setName("DiarioFacil-Registro");
        opt.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.getImage("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-login-24.png");
        opt.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-visible-16.png"));
        JDialog dialog = opt.createDialog(null, "DiarioFacil-Registro");
        dialog.setIconImage(icon);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(450, 290);
        dialog.setResizable(true);
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Registrarse">
        btnRegistrar.addActionListener((ActionEvent e) -> {
            if(!txtEmail.getText().isEmpty() && !txtPass.getText().isEmpty() && !txtPassConfirm.getText().isEmpty() && !txtUserName.getText().isEmpty()){
                if(!txtPass.getText().equals(txtPassConfirm.getText())){
                   JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                }else{
                    //Registrar al nuevo cliente
                    userDao = new  ClienteDao();
                    userDao.save(new Cliente(txtEmail.getText(),txtPass.getText(),txtUserName.getText(),false,false));
                    JOptionPane.showMessageDialog(null, "Su usuario ha sido creado exitosamente", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-verificado-24.png"));
                    dialog.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los datos solicitados son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
            }
        });
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Ver contra">
        btnSee.addActionListener((ActionEvent e)->{
            if(txtPass.getEchoChar()==0){
                txtPass.setEchoChar('\u25CF');
            }else{
                txtPass.setEchoChar((char)0);
            }
        });
        
        btnSeeConfirm.addActionListener((ActionEvent e)->{
            if(txtPassConfirm.getEchoChar()==0){
                txtPassConfirm.setEchoChar('\u25CF');
            }else{
                txtPassConfirm.setEchoChar((char)0);
            }
        });
        
        //</editor-fold>
        
        
        dialog.setVisible(true);
        //</editor-fold>
    }
    
}
