/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;

import java.util.Date;

/**
 *
 * @author Nvidi
 */
public class Orden {
    
    private  Date    fechaOrden;
    private  double  total;
    private  boolean isDel;

    public Orden(Date fechaOrden, double total, boolean isDel) {
        this.fechaOrden = fechaOrden;
        this.total = total;
        this.isDel = isDel;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }
    
    
    
}
