/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author kevin
 */
public class Ingresar {
    Socket cliente;
    String userName;
    String userPassword;

    public Ingresar(Socket cliente, String userName, String userPassword) {
        this.cliente = cliente;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    
    public void ingresandoUser() throws IOException, ClassNotFoundException, InterruptedException{
        enviarSolicitud.enviarMSJActualizar(cliente, "ingresarCliente");
                if (recibirInformacion.recibirConfirmacionDatos(cliente).equals("ingresando")) {                                    
                    enviarSolicitud.enviarLogin(cliente, userName, userPassword);                
                    if (recibirInformacion.recibirConfirmacion(cliente)) {                    
                        enviarSolicitud.enviarConfirmacion(cliente);
                        System.out.println("** BIENVENIDO **");
                        new consolaUsuarios(cliente).listaLibrosDisponibles();                    
                    }
                }
    }
    
            
    
}
