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
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author kevin
 */
public class consolaUsuarios {    
    Socket cliente;
    BufferedReader in;
    String command="",ayuda;
    public static ArrayList<String[]> libros;

    public consolaUsuarios(Socket cliente) {
        this.cliente = cliente;
        in=new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void listaLibrosDisponibles() throws IOException, ClassNotFoundException, InterruptedException{
        System.out.println("Cliente > commando");
        command=in.readLine();
        switch(command){
            case "--listar":
                enviarSolicitud.enviarMSJActualizar(cliente, "listaDeLibros");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                Thread.sleep(2000);
                libros=recibirInformacion.recibirLibros(cliente);
                if (libros.isEmpty()) {
                    System.out.println("No se encuentra Ningun libro !!!");                    
                }
                for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {
                    String[] next = iterator.next();
                    System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);
                }            
                enviarSolicitud.enviarConfirmacion(cliente);            
                listaLibrosDisponibles();
                break;
            case "descargar":
                System.out.println("Cliente> codigo del libro a descargar");
                String cod=in.readLine();
                enviarSolicitud.enviarMSJActualizar(cliente, "descarga");
                enviarSolicitud.enviarCodigoibro(cliente, cod);
                System.out.println("Descargando..*****");
                libros=recibirInformacion.recibirLibros(cliente);                                               
                for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {
                    String[] next = iterator.next();                
                    Thread.sleep(3000);
                    System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);
                }
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);
                listaLibrosDisponibles();            
                break;
            case "cargar":
                System.out.println("Cliente > ingrese los siguientes datos obligatorios:");
                System.out.println("AUTOR:");
                String autor=in.readLine();                                
                System.out.println("PAIS:");
                String pais=in.readLine();
                System.out.println("Nombre Libro:");
                String nameLibro = in.readLine();                
                enviarSolicitud.enviarMSJActualizar(cliente, "cargando");
                enviarSolicitud.enviarCaomposLibro(cliente, autor, pais, nameLibro);
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                Thread.sleep(3000);
                System.out.println("completado");
                enviarSolicitud.enviarConfirmacion(cliente);
                listaLibrosDisponibles(); 
                break;
            case "help":
                enviarSolicitud.enviarMSJActualizar(cliente, "ayuda");                
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);
                listaLibrosDisponibles();
                break;
            case "exit":
                enviarSolicitud.enviarMSJActualizar(cliente, "salir");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);
                System.exit(0);                
                break;
            default:
                    System.err.println("! COMMANDO INVALIDO !");
                    listaLibrosDisponibles();
        }
//        if (command.equals("--listar")) {
//            enviarSolicitud.enviarMSJActualizar(cliente, "listaDeLibros");
//            System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
//            Thread.sleep(3000);
//            libros=recibirInformacion.recibirLibros(cliente);
//            for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {
//                String[] next = iterator.next();
//                System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);
//            }            
//            enviarSolicitud.enviarConfirmacion(cliente);            
//            listaLibrosDisponibles();
//        }
//        if (command.equals("descargar")) {
//            System.out.println("Cliente> codigo del libro a descargar");
//            String cod=in.readLine();
//            enviarSolicitud.enviarMSJActualizar(cliente, "descarga"+" "+cod);
//            libros=recibirInformacion.recibirLibros(cliente);            
//            System.out.println("Descargando.....");            
//            for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {
//                String[] next = iterator.next();                
//                Thread.sleep(1000);
//                System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);
//            }
//            System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
//            enviarSolicitud.enviarConfirmacion(cliente);
//            listaLibrosDisponibles();            
//        }
//        
//        if (command.equals("cargar")) {
//            System.out.println("Cliente> ingrese los siguientes datos:");
//            System.out.println("AUTOR:");
//            String autor=in.readLine();
//            System.out.println("PAIS:");
//            String pais=in.readLine();
//            System.out.println("Nombre Libro:");
//            String nameLibro = in.readLine();
//            enviarSolicitud.enviarMSJActualizar(cliente, "cargando"+" "+autor
//                                                                   +" "+pais
//                                                                   +" "+nameLibro);
//            System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
//            Thread.sleep(3000);
//            System.out.println("compleato");
//            enviarSolicitud.enviarConfirmacion(cliente);
//            listaLibrosDisponibles(); 
//            
//        }
//        if (command.equals("--ayuda")) {
//            String listComandos="--listar:\t lista todos los libros\n"
//                              + "descargar:\t descargar un libro\n"
//                              + "cargar:\t updata libro\n";
//            System.out.println(listComandos);
//            listaLibrosDisponibles();
//        }
        
        
        
        
    }
    
    
}
