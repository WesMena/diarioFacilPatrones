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
public class ClienteLogin extends Menu{
 
    public ClienteLogin(){
    
        menu = new MenuCliente();
        Observador obs = new Observador((MenuCliente)menu);
    }
    
}
