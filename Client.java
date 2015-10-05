
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lenovo
 */
public class Client {
    public static void main (String[] args){
        try (
                Socket clientSocket = new Socket("localhost", 9090);
                PrintWriter out = 
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            ) {
                BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
                String fromServer;
                String fromUser;
                int len;

                while ((fromServer = in.readLine()) != null) {
                    /*while(true){
                        System.out.println("test3");
                        System.out.println("Server: " + fromServer);
                        System.out.println("test4");
                        if((fromServer = in.readLine()) == null || (fromServer = in.readLine()).equals("Bye.")){
                            System.out.println("test5");
                            break;
                        }
                        System.out.println("test6");
                    }*/
                    System.out.println("Server: " + fromServer);
                    if (fromServer.equals("[Bye.]")){
                        break;
                    }
                    //System.out.println("Server: " + fromServer);
                    System.out.println("test2");
                    
                    fromUser = stdIn.readLine();
                    if (fromUser != null) {
                        System.out.println("Client: " + fromUser);
                        out.println(fromUser);
                    }
                }
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to server");
                System.exit(1);
            }
    }
}
