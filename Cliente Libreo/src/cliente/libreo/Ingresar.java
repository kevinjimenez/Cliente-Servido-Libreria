/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

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
    
    public void ingresandoUser() throws IOException, ClassNotFoundException, InterruptedException, SQLException{
        enviarSolicitud.enviarMSJActualizar(cliente, "ingresarCliente");
        enviarSolicitud.enviarLogin(cliente, userName, userPassword);
        String tipo=recibirInformacion.recibirConfirmacionDatos(cliente);         
            if (tipo.equals("ingresando")) {                                                                       
                if (recibirInformacion.recibirConfirmacion(cliente)) {                    
                    enviarSolicitud.enviarConfirmacion(cliente);
                    System.out.println("** BIENVENIDO **");
                    new consolaUsuarios(cliente).listaLibrosDisponibles();                    
                }
            }
            if (tipo.equals("noregistraso")) {
                System.err.println("no esta registrado \t ** REGISTRATE **");
                new login().menu();
            }
            if (tipo.equals("passMalo")) {
                System.err.println("--> contrasena no coincide intenta ingresar otra vez <--");
                new login().menu();
        }
    }
    
            
    
}
