/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 *
 * @author kevin
 */
public class resgistrar {
    Socket cliente;
    String apodo,clave;

    public resgistrar(Socket cliente) {
        this.cliente = cliente;
    }
    
    public void resgistrando() throws IOException, ClassNotFoundException, InterruptedException{
        String[] datos= new String[2];
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Nombre:");
        apodo = in.readLine();
        System.out.println("password:");
        clave = in.readLine();        
        datos[0]=apodo;
        datos[1]=clave;
        System.out.println(datos[0]+ "," + datos[1]);
        if (recibirInformacion.recibirConfirmacionDatos(cliente).equals("ok")) {
            enviarSolicitud.enviarInformacion(cliente, datos);            
            System.err.println("! Usuario registrado Correctamente !");
            new login().menu();            
        }
    }
    
    
}
