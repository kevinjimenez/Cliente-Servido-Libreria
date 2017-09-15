/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.libreo;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author kevin
 */
public class ClienteLibreo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SQLException {
        // TODO code application logic here
        new login().menu();
        
        
    }
    
}
