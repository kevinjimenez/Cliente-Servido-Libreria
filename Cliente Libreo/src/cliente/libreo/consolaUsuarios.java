/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author kevin
 */
public class consolaUsuarios {    
    Socket cliente;
    BufferedReader in;
    String command="",ayuda;
    public static ArrayList<String[]> libros;
    //public static HashMap<String,Blob> downloader;
    //public ArrayList<String> misLibros;
    HashMap<String, String> misLibros;
    String codBook="";
    String nombreLibro="";
    FileOutputStream output;
    InputStream inStream=null;

    public consolaUsuarios(Socket cliente) {
        this.cliente = cliente;
        in=new BufferedReader(new InputStreamReader(System.in));
        //misLibros=new ArrayList<>();        
        misLibros=new HashMap<>();
        //downloader=new HashMap<>();
    }
    
    public void listaLibrosDisponibles() throws IOException, ClassNotFoundException, InterruptedException, SQLException{
        System.out.println("\nCliente > Ingrese comando");
        command=in.readLine();
        switch(command){
            case "--listar":
                enviarSolicitud.enviarMSJActualizar(cliente, "listaDeLibros");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                Thread.sleep(2000);
                libros=recibirInformacion.recibirLibros(cliente);
                if (libros.isEmpty()) {
                    System.out.println("\t!!! No se encuentra Ningun libro !!!");                    
                }
                System.out.println("------------------------------------------");
                for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {                    
                    String[] next = iterator.next();                    
                    System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);
                }
                System.out.println("------------------------------------------");
                enviarSolicitud.enviarConfirmacion(cliente);            
                listaLibrosDisponibles();
                break;
            case "descargar":
                System.out.println("Cliente> Ingrese el CODIGO del libro a descargar: ");
                System.out.println("====================================================");
                System.out.println("Codigo del Libro:");
                String cod=in.readLine();
                enviarSolicitud.enviarMSJActualizar(cliente, "descarga");
                enviarSolicitud.enviarCodigoibro(cliente, cod);
                System.out.println("Descargando..*****");
                System.out.println("----------------------------------------------------");
                //downloader=recibirInformacion.recibirLibros(cliente); 
                libros=recibirInformacion.recibirLibros(cliente);
                for (Iterator<String[]> iterator = libros.iterator(); iterator.hasNext();) {                    
                    String[] next = iterator.next();                
                    Thread.sleep(2000);
                    System.out.println("Codigo: "+next[1]+"\tNombre libro: "+next[0]);                    
                    codBook=next[1];
                    nombreLibro=next[0];
                    output=new FileOutputStream(nombreLibro+".pdf");
                    misLibros.put(codBook, nombreLibro);
                }
                System.out.println("----------------------------------------------------");
                System.err.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);
                listaLibrosDisponibles();            
                break;
            case "cargar":
                System.out.println("Cliente > Ingrese los siguientes datos obligatorios:");
                System.out.println("====================================================");
                System.out.println("AUTOR:");
                String autor=in.readLine();                                
                System.out.println("PAIS:");
                String pais=in.readLine();
                System.out.println("Nombre Libro:");
                String nameLibro = in.readLine();   
                System.out.println("path del libro:");
                String pathLibro=in.readLine();
                enviarSolicitud.enviarMSJActualizar(cliente, "cargando");
                enviarSolicitud.enviarCaomposLibro(cliente, autor, pais, nameLibro,pathLibro);
                System.out.println("====================================================");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                Thread.sleep(3000);
                System.out.println("\tcompletado.");
                System.out.println("====================================================");
                enviarSolicitud.enviarConfirmacion(cliente);                
                listaLibrosDisponibles(); 
                break;
            case "help":
                enviarSolicitud.enviarMSJActualizar(cliente, "ayuda");
                System.out.println("********************************");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                System.out.println("********************************");                
                enviarSolicitud.enviarConfirmacion(cliente);
                listaLibrosDisponibles();
                break;
            case "close":
                enviarSolicitud.enviarMSJActualizar(cliente, "close");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);
                new login().menu();                
                break;
            case  "exit":
                enviarSolicitud.enviarMSJActualizar(cliente, "salir");
                System.out.println(recibirInformacion.recibirConfirmacionDatos(cliente));
                enviarSolicitud.enviarConfirmacion(cliente);                
                System.exit(0);
                break;
            case "mis descargas":
                
                if (misLibros.isEmpty()) {
                    System.out.println("--------------------------------------------");
                    System.out.println("\t no hay descargas");
                    System.out.println("--------------------------------------------");
                }else{                    
                    System.err.println("\t MIS LIBROS DESCARGADOS");
                    System.out.println("--------------------------------------------");
                    for (Map.Entry<String, String> entry : misLibros.entrySet()) {                        
                        String value = entry.getValue();
                        System.out.println("libro: "+value);
                    }
                    System.out.println("--------------------------------------------");                
                }
                
                listaLibrosDisponibles();
                break;
            default:
                    System.err.println("! COMMANDO INVALIDO !");
                    listaLibrosDisponibles();
        }       
    }
    
    
}
