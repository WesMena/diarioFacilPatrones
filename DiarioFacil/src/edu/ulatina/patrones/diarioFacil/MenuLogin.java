/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
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
        JButton btnLogAdmin = new JButton("Admin   ");
        JPanel panlBack = new JPanel();
        JPanel pnlButtons = new JPanel();
        JPanel pnlUserName = new JPanel();
        JPanel pnlPassword = new JPanel();
        JLabel lblUserName = new JLabel("Nombre de usuario : "); 
        JLabel lblPassword = new JLabel("Contrasenia : "); 
        JPasswordField txtPassword = new JPasswordField("0000000");
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Nombre de usuario">
        pnlUserName.setSize(50,50);
        pnlUserName.setLayout(new BorderLayout());
        pnlUserName.add(lblUserName,BorderLayout.BEFORE_FIRST_LINE);
        pnlUserName.add(txtUserNam,BorderLayout.AFTER_LAST_LINE);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Contrasenia">
        pnlPassword.setSize(50,50);
        pnlPassword.setLayout(new BorderLayout());
        pnlPassword.add(lblPassword,BorderLayout.BEFORE_FIRST_LINE);
        pnlPassword.add(txtPassword,BorderLayout.AFTER_LAST_LINE);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Login">
        pnlButtons.setSize(50,50);
        btnLogAdmin.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-administrador-8.png"));
        btnLogCliente.setIcon(new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-facturacion-8.png"));
        pnlButtons.setLayout(new BorderLayout());
        pnlButtons.add(btnLogCliente,BorderLayout.EAST);
        pnlButtons.add(btnLogAdmin,BorderLayout.WEST);

        
        panlBack.setLayout(new BorderLayout());
        panlBack.setSize(400, 200);
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
        dialog.setResizable(true);
        
        btnLogCliente.addActionListener((ActionEvent e) -> {
            if(!txtPassword.getText().isEmpty() && !txtUserNam.getText().isEmpty()){
                userDao = new ClienteDao();
                if(((ClienteDao)userDao).login(txtUserNam.getText(),txtPassword.getText())){
                    //Login correcto
                    JOptionPane.showMessageDialog(null, "Login correcto", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                    dialog.setVisible(false);
                    //Abrir el menu de cliente
                    dialog.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Las credenciales son incorrectas", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los datos solicitados son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));
            }
        });
        
        btnLogAdmin.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userDao =  new AdminDao();
                if(!txtPassword.getText().isEmpty() && !txtUserNam.getText().isEmpty()){
                    if(((AdminDao)userDao).login(txtUserNam.getText(), txtPassword.getText())){
                        JOptionPane.showMessageDialog(null, "Login correcto", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png"));      
                        //Abrir menu de admin
                        dialog.dispose();
                    }else{
                       JOptionPane.showMessageDialog(null, "Las credenciales son incorrectas", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                    } 
                }else{
                   JOptionPane.showMessageDialog(null, "Todos los datos solicitados son obligatorios", "Sys", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/edu/ulatina/patrones/diarioFacil/imagenes/icons8-error-24.png")); 
                }
            }
        });
        
        
        
        dialog.setVisible(true);
        //</editor-fold>
        
        
    }
}
