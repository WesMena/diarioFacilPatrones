/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author josem
 */
public class MenuCliente implements IMenu {
    public static Dao dao;
    
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
        
        //Arranque
        dialog.setVisible(true);
    }
    
}
