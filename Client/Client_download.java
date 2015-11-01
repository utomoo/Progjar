/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Dimas Rahman Oetomo
 */
public class Client_download {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        try(Socket clientSocket = new Socket("localhost", 9090);
                PrintWriter out = 
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                ){
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));  
            String fromServer;
            String fromServer1;
            String fromUser;
            int len;
            
            //fromServer = in.readLine();
            //System.out.println("Server: " +fromServer);
            
            while((fromServer = in.readLine()) != null){
                //while((fromServer = in.readLine()) != null){
                    System.out.println("Test1");
                    //fromServer = in.readLine();
                    //System.out.println("Test3");
                    //fromServer1 = in.readLine();
                    
                    System.out.println("Server: " +fromServer);
                    //System.out.println("Server: " +fromServer1);
                  
                //}
                
                    fromUser = stdIn.readLine();
                    if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println("Result:"+fromUser);
                    }
                    System.out.println("Test2");
            }
        }
       catch(UnknownHostException e){
           System.out.println("Server tidak ditemukan");
       }
    }
        
    
}
    

