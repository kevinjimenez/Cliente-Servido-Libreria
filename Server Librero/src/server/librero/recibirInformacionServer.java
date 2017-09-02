/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.librero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author kevin
 */
public class recibirInformacionServer {
    public static String recibirConfirmacion(Socket cliente) throws IOException, ClassNotFoundException{
        String confirmacion="";
        ObjectInputStream in;
        in=new ObjectInputStream(cliente.getInputStream());
        confirmacion=""+in.readObject();
        System.out.println("Cliente> petición Realizada ["+ confirmacion +"]");
        return confirmacion;
    }
    
    public static String[] recibirInformacionActualizada(Socket cliente) throws IOException, ClassNotFoundException{
        String[] informacionActualizada = null;
        ObjectInputStream in;
        in  = new ObjectInputStream(cliente.getInputStream());
        informacionActualizada=(String[]) in.readObject();
        return informacionActualizada;
    }
    
    public static String[] log (Socket cliente) throws IOException, ClassNotFoundException{
        String login[]=null;
        ObjectInputStream in;
        in =new ObjectInputStream(cliente.getInputStream());
        login=(String[]) in.readObject();
        return login;
                
    }
    
    public static String[] libroNUevo (Socket cliente) throws IOException, ClassNotFoundException{
        String libros[]=null;
        ObjectInputStream in;
        in =new ObjectInputStream(cliente.getInputStream());
        libros=(String[]) in.readObject();
        return libros;
                
    }
    
    public static String tomandoCodigoLibro(Socket cliente) throws IOException, ClassNotFoundException{
        String cod="";
        ObjectInputStream in;
        in = new ObjectInputStream(cliente.getInputStream());
        cod=""+in.readObject();
        return cod;
    }
    
    public static String recibirTipoActualizacion(Socket cliente) throws IOException, ClassNotFoundException{
        String condrimacion="";
        ObjectInputStream in;
        in = new ObjectInputStream(cliente.getInputStream());
        condrimacion=""+in.readObject();
        return condrimacion;
    }
            
}
