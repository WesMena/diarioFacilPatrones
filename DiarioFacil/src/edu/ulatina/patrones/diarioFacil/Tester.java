/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.sql.SQLException;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Tester {

  
    
   public static void main(String[] args) throws SQLException {  
       Cliente cli=new Cliente();
       cli.setId(1);
       boolean freq;
     //  MenuProducto menu=new MenuProducto();
     //  menu.menu();
       /*EsFrecuente frecuente=new EsFrecuente();
       freq=frecuente.clienteFrecuente(cli);
       System.out.println(freq?"Frecuente":"no frecuente");
       */
       
       
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       MenuLogin log = new MenuLogin();
       log.loginUI();

       
   }
   
}
    
    

