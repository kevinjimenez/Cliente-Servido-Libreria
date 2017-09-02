/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author kevin
 */
public class enviarSolicitud {
    public static void enviarMSJActualizar(Socket cliente, String msj) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(msj);        
    }
    public static void enviarCodigoibro(Socket cliente,String codLibro) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(codLibro); 
    }
    public static void enviarCaomposLibro(Socket cliente, String Autor,String Pais,String Libro) throws IOException{
        String anadirLibro[]=new String[3];
        anadirLibro[0]=Autor;
        anadirLibro[1]=Pais;
        anadirLibro[2]=Libro;
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(anadirLibro);
        
    }
    
    public static void  enviarInformacion(Socket cliente,String[] datos) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(datos);        
    }
    
    public static void enviarLogin(Socket cliente,String usuario,String pass) throws IOException{
        String[] log=new String[2];
        log[0]=usuario;
        log[1]=pass;
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(log);
    }
    
    public static void enviarConfirmacion(Socket cliente) throws IOException{
        String confirmacion ="OK";
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(confirmacion);
    }
    
    
    
}
