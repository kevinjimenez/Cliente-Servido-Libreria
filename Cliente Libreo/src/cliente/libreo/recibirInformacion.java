/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public class recibirInformacion{
    
    public  static String recibirConfirmacionDatos(Socket cliente) throws IOException, ClassNotFoundException{
        String confirmacion="";
        ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
        confirmacion=""+in.readObject();
        return confirmacion;
    }
    
    public static boolean recibirConfirmacion(Socket cliente) throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        boolean confir = false;
        String info;
        in = new ObjectInputStream(cliente.getInputStream());
        info = in.readObject().toString();        
        info=info.toLowerCase().replaceAll(" ", "");
        if (info.equals("clienteaceptado")) {
            confir=true;
        }
        return confir;
    }
    
    
    public static ArrayList recibirLibros(Socket cliente) throws IOException, ClassNotFoundException{
        ArrayList<String[]> listLibros = new ArrayList();
        ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
        listLibros=(ArrayList) in.readObject();
        return listLibros;
    }
//    public static HashMap recibirArchivos(Socket cliente) throws IOException, ClassNotFoundException{
//        HashMap<String,Blob> archivosDescarga = new HashMap<>();
//        ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
//        archivosDescarga=(HashMap<String, Blob>) in.readObject();
//        return archivosDescarga;
//    }
    
    
            
    
}
