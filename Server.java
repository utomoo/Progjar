/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author lenovo
 */
public class Server {
    public static void main(String[] args){
        
        try (
            ServerSocket serverSocket = new ServerSocket(9090);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = 
                new PrintWriter(clientSocket.getOutputStream(), true);    
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            String inputLine, outputLine;
            //menginisiasi hubungan dengan client
            outputLine = "test server";
            out.println(outputLine);
            
            while ((inputLine = in.readLine()) != null){
                outputLine = "bye.";
                out.println(outputLine);
                if (outputLine.equals("bye."))
                    break;
            } 
        } catch (Exception e) {
            System.err.println("Somethingwrong");
            System.exit(1);
        }
    }
}
