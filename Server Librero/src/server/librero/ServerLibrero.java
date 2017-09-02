/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.librero;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author kevin
 */
public class ServerLibrero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServerSocket serve = new ServerSocket(5000);
        System.out.println("Servidor> Servidor iniciado");    
        System.out.println("Servidor> En espera de cliente...");         
        while (true) {            
            Socket cliente = serve.accept();            
            Thread hilo = new Thread(new MyThread(cliente));
            hilo.start();
        }
    }
    
}
