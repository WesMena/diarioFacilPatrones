/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

/**
 *
 * @author josem
 */
public abstract class Menu {
    
    protected IMenu menu;
    
    public void DesplegarMenu(){
    menu.mostrarMenu();
    }
}
