/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package server.librero;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author kevin
 */
public class conexionMysql {

    Connection conexion;
    String url="jdbc:mysql://localhost:3306/libreria?useSSL=false";
    String usr="root";
    String password="1234";
    public conexionMysql(){
        try {
            conexion = DriverManager.getConnection(url,usr,password);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    public void newUsuario(String[] datos){
        try {
            Statement st;
            PreparedStatement ps = conexion.prepareStatement("insert into USUARIO(NOMBRE,CLAVE) values (?,?)");
            ps.setString(1, datos[0]);
            ps.setString(2, datos[1]);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void newAutor(String name,String pais) throws SQLException{
        Statement st;
        PreparedStatement ps = conexion.prepareStatement("insert into AUTOR(NOMBRE_AUTOR,PAIS) values (?,?)");
        ps.setString(1, name);
        ps.setString(2, pais);
        ps.execute();
        ps.close();
    }    
    
    public int getIdAutor(String name) throws SQLException{
        int id=0;
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs=st.executeQuery("select ID_AUTOR from AUTOR where NOMBRE_AUTOR="+"'"+name+"'");
        while (rs.next()) {            
            id+=rs.getInt(1);
        }        
        return id;
    }
    
    public void newLibro(int idAUtor,String nameLibro,String codLIbro,String pathLibro) throws SQLException, FileNotFoundException, IOException{
        FileInputStream archivoLibro = null;
        File file = new File(pathLibro);
        archivoLibro = new FileInputStream(file);        
        PreparedStatement ps = conexion.prepareStatement("insert into LIBRO(ID_AUTOR,NOMBRE_LIBRO,COD_LIBRO,PATH_LIBRO) values (?,?,?,?)");
        ps.setInt(1, idAUtor);
        ps.setString(2, nameLibro);
        ps.setString(3, codLIbro);
        ps.setBinaryStream(4, archivoLibro);
        ps.execute();
        ps.close();
        archivoLibro.close();
    }
    
    public String password(String consulta) throws SQLException{
        String pass="";
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs = st.executeQuery(consulta);
        while (rs.next()) {            
            pass+=rs.getString(1);
        }
        return pass;
    }
    
    public String existeUsuario(String consulta) throws SQLException{
        String user="";
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs = st.executeQuery(consulta);
        while (rs.next()) {            
            user+=rs.getString(1);
        }
        return user;
    }
    
    public ArrayList todosLosLibros(String consulta) throws SQLException{
        ArrayList<String[]> libritos=new ArrayList<>();
        Statement st;
        ResultSet rs;
        st=conexion.createStatement();
        rs=st.executeQuery(consulta);
        while (rs.next()) {          
            String books[]=new String[2];
            books[0]=rs.getString(3);
            books[1]=rs.getString(4);
            libritos.add(books);            
        }
        return libritos;
    }
    
    /*public HashMap descargando(String consulta) throws SQLException{
        HashMap<String,Blob> tusDescargas = new HashMap<>();
        Blob archivo=null;
        String nombre="";
        Statement st;
        ResultSet rs;
        st=conexion.createStatement();
        rs=st.executeQuery(consulta);
        while (rs.next()) {          
            archivo = rs.getBlob("PATH_LIBRO");
            nombre = rs.getObject("NOMBRE_LIBRO").toString();
            tusDescargas.put(nombre, archivo);
            System.out.println(nombre+" "+archivo);
        }
        return tusDescargas;
    }*/
    public ArrayList descarga(String consulta) throws SQLException{
        ArrayList<String[]> lista = new ArrayList<>();
        String[] libritos;
        Statement st;
        ResultSet rs;
        st=conexion.createStatement();
        rs=st.executeQuery(consulta);
        while (rs.next()) { 
            libritos=new String[2];
            libritos[0]=rs.getString(3);
            libritos[1]=rs.getString(4);
            lista.add(libritos);
        }
        return lista;
    }
    
    public void newServicio(int idUsuario,int idLibro,String fechaServicio,String tipoServicio) throws SQLException{
        Statement st;
        PreparedStatement ps = conexion.prepareStatement("insert into SERVICIOS(ID_USUARIO,ID_LIBRO,FECHA_SERVICIO,TIPO_SERVICIO) values (?,?,?,?)");
        ps.setInt(1, idUsuario);
        ps.setInt(2, idLibro);
        ps.setString(3, fechaServicio);
        ps.setString(4, tipoServicio);
        ps.execute();
        ps.close();
    }
    public String getNombreAutor(String nombreAutor) throws SQLException{
        String autor="";
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs=st.executeQuery("select NOMBRE_AUTOR from AUTOR where NOMBRE_AUTOR="+"'"+nombreAutor+"'");
        while (rs.next()) {            
            autor+=rs.getString(1);
        }
        return autor;
    }
    
    public int getIdUsuario(String User) throws SQLException{
        int id=0;
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs=st.executeQuery("select ID_USUARIO from USUARIO where NOMBRE="+"'"+User+"'");
        while (rs.next()) {            
            id+=rs.getInt(1);
        }
        
        return id;
    }
    
    public int getIdLibro(String libro) throws SQLException{
        int id=0;
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs=st.executeQuery("select ID_LIBRO from LIBRO where COD_LIBRO="+"'"+libro+"'");
        while (rs.next()) {            
            id+=rs.getInt(1);
        }
        
        return id;
    }
    
    public int getIdlibroName(String name) throws SQLException{
        int id=0;
        Statement st;
        ResultSet rs;
        st = conexion.createStatement();
        rs=st.executeQuery("select ID_LIBRO from LIBRO where    NOMBRE_LIBRO="+"'"+name+"'");
        while (rs.next()) {            
            id+=rs.getInt(1);
        }
        
        return id;
    } 
    
    
   
    
}


