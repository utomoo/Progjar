/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
            String inputLine,output = null; 
            ArrayList<String> outputLine = new ArrayList<String>();
            remotedir rd = new remotedir();
            //menginisiasi hubungan dengan client
            outputLine.add(0,"terhubungan dengan server");
            out.println(outputLine);
            outputLine.remove(0);
            while ((inputLine = in.readLine()) != null){
                int x = 0;
                System.out.println("test");
                outputLine = rd.inputcode(x,inputLine);
                //outputLine.add(x,"Bye.");
                //System.out.println(outputLine);
                x = outputLine.size();
                //for(int i = 0 ; i <= x ; i++){
                //    System.out.println("test1");
                //    output = outputLine.get(i);
                    out.println(outputLine);
                    //outputLine.remove(i);
                    outputLine.clear();
                //}
                if (output.equals("Bye."))
                    break;
            } 
        } catch (Exception e) {
            System.err.println("Somethingwrong");
            System.exit(1);
        }
    }
}
