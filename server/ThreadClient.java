/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
class ThreadClient  implements Runnable{
    private Socket socket;
    
    public ThreadClient(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try (
            PrintWriter out = 
                new PrintWriter(socket.getOutputStream(), true);    
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                ){
            System.out.println("Klien baru datang ....");
            String inputLine,output = null;
            output="terhubungan dengan server";
            out.println(output);
            
            while((inputLine = in.readLine()) != "END") {
                System.out.println("user: "+inputLine);
                output = "hello";
                out.println(output);
            }
            
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
