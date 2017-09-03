/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.librero;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author kevin
 */
public class MyThread implements  Runnable{
    Socket cliente;
    conexionMysql consultaSQL;
    int idUsuario;
    int idLibro;  
    String codLibro;
    String [] nuevoLibro;
    String pass,fechaServicio,tipoServicio;        
    String [] nuevousuario;
    String logearse[],lista[];
    ArrayList<String[]> books;
    ArrayList<String[]> descargas;

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
                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ingresando");
                    logearse = recibirInformacionServer.log(cliente);                    
                    pass=consultaSQL.password("select clave from USUARIO where nombre="+"'"+logearse[0]+"'");
                    if (logearse[1].equals(pass)) {                                                
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
                                        descargas=consultaSQL.descargando("select * from LIBRO where COD_LIBRO="+"'"+codLibro+"'");                                    
                                        
                                        if (descargas.isEmpty()) {
                                            enviarInformacionServidor.enviarInformacion(cliente, descargas);
                                            enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ERROR DE DESCARGA !NO EXISTE!");
                                            confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                          
                                            confirmacionInformacionRecibida="";
                                            continue;
                                        }
                                        Thread.sleep(2000);
                                        idUsuario=consultaSQL.getIdUsuario(logearse[0]);
                                        idLibro=consultaSQL.getIdLibro(codLibro);
                                        fechaServicio=fecha.toString();                                    
                                        consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);
                                        enviarInformacionServidor.enviarInformacion(cliente, descargas);  
                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "descarga completa");
                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                        confirmacionInformacionRecibida="";
                                        break;
                                    case "cargando":
                                        nuevoLibro=recibirInformacionServer.libroNUevo(cliente);                                    
                                        System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");
                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "cargando libro.....");
                                        fecha = new Date();                                                                                                          
                                        consultaSQL.newAutor(nuevoLibro[0], nuevoLibro[1]);                                                                        
                                        int idAutor=0;                                   
                                        idAutor=consultaSQL.getIdAutor(nuevoLibro[0]);                                    
                                        consultaSQL.newLibro(idAutor, nuevoLibro[2], "cod"+idAutor);                                                                        
                                        idUsuario=consultaSQL.getIdUsuario(logearse[0]);                                    
                                        idLibro=consultaSQL.getIdlibroName(nuevoLibro[2]);                                                                                                           
                                        fechaServicio=fecha.toString();
                                        consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);     
                                        
                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                        
                                        confirmacionInformacionRecibida="";                                   
                                        break;
                                    case "ayuda":
                                        String listComandos="";
                                        listComandos="--listar:\t lista todos los libros\n"
                                                           +"descargar:  \t descargar un libro\n"
                                                           +"cargar:  \t updata libro\n";
                                        System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");                                         
                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, listComandos);                                        
                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                        confirmacionInformacionRecibida="";
                                        break;
                                    case "close":
                                        System.out.println("Cliente [ "+logearse[0]+"]  > petición [" + confirmacionInformacionRecibida +  "]");
                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, logearse[0]+" cierra cesion ");
                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                        confirmacionInformacionRecibida="";                                        
                                        break;
                                    case "salir":
                                        System.out.println("Cliente [ "+logearse[0]+"]  > petición [" + confirmacionInformacionRecibida +  "]"); 
                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, logearse[0]+" dice adios ");                                   
                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
                                        confirmacionInformacionRecibida="";                                        
                                        break;                                    
                                    default:
                                        System.out.println(";v");
                                }
//                                if (confirmacionInformacionRecibida.equals("listaDeLibros")) {                                                                       
//                                    System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");
//                                    books=consultaSQL.todosLosLibros("select * from LIBRO");                                     
//                                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "listado todos los libro.....");
//                                    enviarInformacionServidor.enviarInformacion(cliente, books);                                    
//                                    confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);
//                                    confirmacionInformacionRecibida="";                                    
//                                }
//                                if (confirmacionInformacionRecibida.equals("--exit")) {
//                                   System.out.println("Cliente [ "+logearse[0]+"]  > petición [" + confirmacionInformacionRecibida +  "]"); 
//                                   enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "cliente dice adios "+logearse[0]);                                   
//                                   confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
//                                   confirmacionInformacionRecibida="";
//                                   System.exit(0);
//                                }
//                                if (confirmacionInformacionRecibida.equals("--help")) {
//                                    String listComandos="--listar:\t lista todos los libros\n"
//                                                       +"descargar:  \t descargar un libro\n"
//                                                       +"cargar:  \t updata libro\n";
//                                    System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]"); 
//                                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, listComandos);
//                                    confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
//                                    confirmacionInformacionRecibida="";
//                                }                                                                
//                                if (confirmacionInformacionRecibida.equals("descarga")) {                                                        
//                                    codLibro=recibirInformacionServer.tomandoCodigoLibro(cliente);
//                                    System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");                                    
//                                    java.util.Date fecha = new Date();        
//                                    System.out.println("khe");
//                                    descargas=consultaSQL.descargando("select * from LIBRO where COD_LIBRO="+"'"+codLibro+"'");                                    
//                                    
//                                    if (descargas.isEmpty()) {
//                                        enviarInformacionServidor.enviarInformacion(cliente, descargas);
//                                        enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "ERROR DE DESCARGA !NO EXISTE!");
//                                        confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                          
//                                        confirmacionInformacionRecibida="";
//                                        continue;
//                                    }
//                                    Thread.sleep(2000);
//                                    idUsuario=consultaSQL.getIdUsuario(logearse[0]);
//                                    idLibro=consultaSQL.getIdLibro(codLibro);
//                                    fechaServicio=fecha.toString();                                    
//                                    consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, confirmacionInformacionRecibida);
//                                    enviarInformacionServidor.enviarInformacion(cliente, descargas);  
//                                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "descargar completa");
//                                    confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);                                    
//                                    confirmacionInformacionRecibida="";
//                                    
//                                }                                                                                                   
//                                if (confirmacionInformacionRecibida.equals("cargando")) {
//                                    nuevoLibro=recibirInformacionServer.libroNUevo(cliente);                                    
//                                    System.out.println("Cliente [ "+logearse[0]+" ] > petición [" + confirmacionInformacionRecibida +  "]");
//                                    enviarInformacionServidor.confirmacionClienteActualizacion(cliente, "cargando libro.....");
//                                    java.util.Date fecha = new Date();                                                                                                          
//                                    consultaSQL.newAutor(nuevoLibro[0], nuevoLibro[1]);                                                                        
//                                    int idAutor=0;                                   
//                                    idAutor=consultaSQL.getIdAutor(nuevoLibro[0]);                                    
//                                    consultaSQL.newLibro(idAutor, nuevoLibro[2], "cod"+idAutor);                                                                        
//                                    idUsuario=consultaSQL.getIdUsuario(logearse[0]);                                    
//                                    idLibro=consultaSQL.getIdlibroName(nuevoLibro[2]);                                                                                                           
//                                    fechaServicio=fecha.toString();
//                                    consultaSQL.newServicio(idUsuario, idLibro, fechaServicio, nuevoLibro[1]);                                    
//                                    confirmacionInformacionRecibida=recibirInformacionServer.recibirConfirmacion(cliente);
//                                    confirmacionInformacionRecibida="";                                   
//                                }
                                
                            }
                        }                        
                    }else{
                        
                    }
                    
                }
            }
        } catch (Exception e) {
        }
        
    }
    
    
    
}
