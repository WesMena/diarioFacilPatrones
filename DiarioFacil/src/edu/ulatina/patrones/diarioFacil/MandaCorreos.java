/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.patrones.diarioFacil;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
import java.util.Optional;
/**
 *
 * @author wesli
 */
public class MandaCorreos {
   void enviarCorreo(int proveedor, int idProducto, int cantidad){
       try {
           DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
           LocalDateTime horaActual=LocalDateTime.now();
           String fechaHora=horaActual.format(dtf);
           ProveedorDao prove=new ProveedorDao();
           ProductoDao produ=new ProductoDao();
           Optional<Producto> opProdu=produ.get(idProducto);
           Optional<Proveedor>opProve=prove.get(proveedor);
           //Proveedor proveedor,int idProducto,int cantidad
           Properties propiedades=new Properties();
           propiedades.setProperty("mail.smtp.host","smtp.gmail.com");
           propiedades.setProperty("mail.smtp.starttls.enable", "true");
           propiedades.setProperty("mail.smtp.port", "587");
           propiedades.setProperty("mail.smtp.auth", "true");
           Session sesion=Session.getDefaultInstance(propiedades);
           
           String cuenta="ventasestructurasulatina@gmail.com";
           String contra="wvjjk611";
           String destinatario="tiendaproyectoulatina@gmail.com";
           
           //La contraseña de este también es wvjjk611
           
           String asunto="Pedido "+fechaHora;
           
          
           String mensaje="<font size=\"4\">Estimado "+opProve.get().getNombre()+", <br>"+
                   "Se le solicita enviar "+cantidad+" unidades del producto "
                   +opProdu.get().getNombreProd()+" a nuestra tienda. <br>"+
                   "<br> Muchas gracias.</font>";
           
           
           
           MimeMessage mail=new MimeMessage(sesion);
           
           mail.setFrom(new InternetAddress(cuenta));
           mail.addRecipient(Message.RecipientType.TO,new InternetAddress(destinatario));
           mail.setSubject(asunto);
           mail.setContent(mensaje,"text/html");
           Transport transporte=sesion.getTransport("smtp");
           transporte.connect(cuenta,contra);
           transporte.sendMessage(mail,mail.getRecipients(Message.RecipientType.TO));
           transporte.close();
                   
                   
                   } catch (MessagingException ex) {
           Logger.getLogger(MandaCorreos.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
   }

  
}
