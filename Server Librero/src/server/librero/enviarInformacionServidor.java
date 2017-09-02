/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.librero;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author kevin
 */
public class enviarInformacionServidor {
    public static void confirmacionClienteActualizacion(Socket cliente, String msj) throws IOException{
        ObjectOutputStream out=null;
        out = new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(msj);
        //System.out.println((String)msj);
    }
    
    public static void confirmaCLiente(Socket cliente) throws IOException{
        ObjectOutputStream out = null;
        out=new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject("cliente Aceptado");        
        
    }
    
    public static void enviarInformacion(Socket cliente,ArrayList<String[]> libro) throws IOException{
        ObjectOutputStream out=null;
        out=new ObjectOutputStream(cliente.getOutputStream());
        out.writeObject(libro);
    }
    
    
    
}
