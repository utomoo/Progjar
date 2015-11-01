/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_download;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimas Rahman Oetomo
 */
public class Server_download {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ServerSocket servsock = new ServerSocket(9090);
            System.out.println("Server mau berjalan ....");
            
            while(true) {
                Socket socket;
                socket = servsock.accept();
                ThreadClient tc = new ThreadClient(socket);
                Thread t = new Thread(tc);
                t.start();
            }
            
            //servsock.close();
        } catch (IOException ex) {
            Logger.getLogger(Server_download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
