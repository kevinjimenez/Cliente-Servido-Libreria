/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.librero;

import java.net.Socket;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 *
 * @author kevin
 */
public class MyThread implements  Runnable{
    Socket cliente;
    conexionMysql consultaSQL;
    
    int idUsuario;
    int idLibro;  
    int idAutor;
    String autorExiste;
    String codLibro;
    String [] nuevoLibro;
    String nombreUsuario,pass,fechaServicio,tipoServicio;        
    String [] nuevousuario;
    String logearse[],lista[];
    ArrayList<String[]> books;
    HashMap<String,Blob> descargas;
    ArrayList<String[]> download;

    public MyThread(Socket cliente) {
        this.cliente = cliente;
        consultaSQL=new conexionMysql();
    }

    @Override
    public void run() {                
        try {
            String tipo="";
            String confirmacionInformacionRecibida = "";
            tipo=recibirInformacionServer.recibirConfirmacion(cliente);            
            while (true) {                      
                if (tipo.equals("RegistrarCliente")) {                    
                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "registrado");
                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ok");
                    nuevousuario = recibirInformacionServer.recibirInformacionActualizada(cliente);                    
                    consultaSQL.newUsuario(nuevousuario);
                    confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);      //                              
                    confirmacionInformacionRecibida="";
                }
                if (tipo.equals("ingresarCliente")) {
                    logearse = recibirInformacionServer.log(cliente);                    
                    nombreUsuario=consultaSQL.existeUsuario("select nombre from USUARIO where nombre="+"'"+logearse[0]+"'");                                                            
                    pass=consultaSQL.password("select clave from USUARIO where nombre="+"'"+logearse[0]+"'");
                    if (nombreUsuario=="") {                        
                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "noregistraso");
                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ok");                        
                        System.out.println("No existes :v");
                        System.out.println("OK");
                    }else{                                                                                                                                                      
                        if (logearse[1].equals(pass)) {                                                
                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ingresando");
                            enviarInformacionServidor.confirmaCLiente(cliente);                        
                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);
                            System.out.println(confirmacionInformacionRecibida);
                            if (confirmacionInformacionRecibida.equals("OK")) {                            
                                while (true) {    
                                    java.util.Date fecha;
                                    confirmacionInformacionRecibida=recibirInformacionServer.recibirTipoActualizacion(cliente);                                                                                                
                                    System.out.println(confirmacionInformacionRecibida);
                                    switch(confirmacionInformacionRecibida){
                                        case "listaDeLibros":
                                            System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");
                                            books=consultaSQL.todosLosLibros("select * from LIBRO");                                     
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "listado todos los libro.....");
                                            enviarInformacionServidor.enviarInformacion(cliente, books);                                    
                                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);
                                            confirmacionInformacionRecibida="";
                                            break;
                                        case "descarga":
                                            codLibro=recibirInformacionServer.tomandoCodigoLibro(cliente);
                                            System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");                                    
                                            fecha = new Date();                                                                                        
                                            //descargas=consultaSQL.descargando("select NOMBRE_LIBRO,PATH_LIBRO from LIBRO where COD_LIBRO="+"'"+codLibro+"'");                                            
                                            download=consultaSQL.descarga("select * from LIBRO where COD_LIBRO="+"'"+codLibro+"'");
                                            if (download.isEmpty()) {
                                                //enviarInformacionServidor.enviarLibros(cliente, descargas);
                                                enviarInformacionServidor.enviarInformacion(cliente, download);
                                                enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ERROR DE DESCARGA !NO EXISTE!");
                                                confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                          
                                                confirmacionInformacionRecibida="";
                                                continue;
                                            }else{                                                
                                                Thread.sleep(2000);                                                
                                                idUsuario=consultaSQL.getIdUsuario(logearse[0]);
                                                idLibro=consultaSQL.getIdLibro(codLibro);
                                                fechaServicio=fecha.toString();                                    
                                                consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);
                                                //enviarInformacionServidor.enviarLibros(cliente, descargas);  
                                                enviarInformacionServidor.enviarInformacion(cliente, download);
                                                enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "\t** descarga completa ** ");
                                                confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                                confirmacionInformacionRecibida="";}
                                            break;
                                        case "cargando":                                            
                                            nuevoLibro=recibirInformacionServer.libroNUevo(cliente);                                    
                                            System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "\tcargando libro.....");
                                            fecha = new Date();                                            
                                            autorExiste=consultaSQL.getNombreAutor(nuevoLibro[0]);                                             
                                            if (autorExiste=="") {
                                                consultaSQL.newAutor(nuevoLibro[0], nuevoLibro[1]);                                                                        
                                                //int idAutor=0;                                   
                                                idAutor=consultaSQL.getIdAutor(nuevoLibro[0]);                                                                                    
                                                consultaSQL.newLibro(idAutor, nuevoLibro[2], "cod"+(idAutor),nuevoLibro[3]);                                                                                                                                                                                                                                   
                                                idUsuario=consultaSQL.getIdUsuario(logearse[0]);                                    
                                                idLibro=consultaSQL.getIdlibroName(nuevoLibro[2]);
                                                fechaServicio=fecha.toString();
                                                consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);                                             
                                                confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                        
                                                confirmacionInformacionRecibida="";                                                
                                            }
                                            if(autorExiste!=""){
                                                idUsuario=consultaSQL.getIdUsuario(logearse[0]);                                                                                                         
                                                idAutor=consultaSQL.getIdAutor(autorExiste);
                                                System.out.println(idAutor);                                                
                                                consultaSQL.newLibro(idAutor, nuevoLibro[2], "cod"+(idAutor+idUsuario),nuevoLibro[3]);                                                                                                                                   
                                                idLibro=consultaSQL.getIdlibroName(nuevoLibro[2]);                                                 
                                                fechaServicio=fecha.toString();
                                                consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);                                                                                             
                                                confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                        
                                                confirmacionInformacionRecibida="";
                                                
                                            }
                                                                             
                                            break;
                                        case "ayuda":
                                            String listComandos="";
                                            listComandos="--listar:\t lista todos los libros\n"
                                                           +"descargar:  \t descargar un libro\n"
                                                           +"cargar:  \t subir libro\n"
                                                           +"close:  \t cerrar cesion\n"
                                                           +"mis descargas: \t libros descargados\n"
                                                           +"exit:   \t salir";                                                           
                                            System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");                                         
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, listComandos);                                        
                                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                            confirmacionInformacionRecibida="";
                                            break;
                                        case "close":
                                            System.out.println("Cliente [ "+logearse[0]+"]  > petición [" + confirmacionInformacionRecibida +  "]");
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, logearse[0]+"\tcerrando cesion ");
                                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                            confirmacionInformacionRecibida="";                                        
                                            break;
                                        case "salir":
                                            System.out.println("Cliente [ "+logearse[0]+"]  > petición [" + confirmacionInformacionRecibida +  "]"); 
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "\t"+logearse[0]+" dice adios ");                                   
                                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                            confirmacionInformacionRecibida="";                                        
                                            break;                                    
                                        default:
                                            System.out.println(";v");
                                    }                                
                                }
                            }                        
                        }else{
                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "passMalo");
                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ok");
                            System.out.println("khe :v");
                            System.out.println("OK");
                        }
                    }//else user                    
                }
            }
        } catch (Exception e) {
        }
        
    }
    
    
    
}
