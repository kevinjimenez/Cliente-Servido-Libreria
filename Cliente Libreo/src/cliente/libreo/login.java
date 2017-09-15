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
import java.sql.SQLException;

/**
 *
 * @author kevin
 */
public class login {
    Socket cliente;
    String [] datosLogin;
    BufferedReader in;
    int opcion;
    String str;

    public login() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    
    
    public void menu() throws IOException, ClassNotFoundException, InterruptedException, SQLException{
        System.out.println("Cliente> Inicio");
        System.out.println("1. Registrate");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.err.println("================");
        System.out.println("Ingrese una Opcion:");
        str=in.readLine();        
        if (!Validacion.isNumeric(str)) {
            System.err.println("Escoja una opcion numerica");
            menu();
        }
        switch(Integer.parseInt(str)){
            case 1:
                cliente = new Socket("localhost",5000);   
                enviarSolicitud.enviarMSJActualizar(cliente, "RegistrarCliente");
                if (recibirInformacion.recibirConfirmacionDatos(cliente).equals("registrado")) {                
                    new resgistrar(cliente).resgistrando();                
                }
                break;
            case 2:
                cliente=new Socket("localhost",5000);
                String usr,pass;                            
                System.out.println("\nCliente> Inicio"); 
                System.out.println("usuario: ");
                usr=in.readLine();
                System.out.println("contrasena:");
                pass=in.readLine();            
                new Ingresar(cliente, usr, pass).ingresandoUser();
                break;
            case 3:
                System.out.println("ADIOS");
                System.exit(0);
                break;
            default:
                System.err.println("! OPCION INCORRECTA !");
                new login().menu();
        }
       
    }
    
    
    
}
