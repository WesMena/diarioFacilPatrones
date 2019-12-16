/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nvidi
 */
public class CareTaker {
        private List<Memento> mementos = null;
    
    public CareTaker(){
        this.mementos = new ArrayList<>();
    }
    
    public void addMemento(Memento e){
        this.mementos.add(e);
    }
    
    public Memento getMemento(int index){
        return this.mementos.get(index);
    }
    
    
}
