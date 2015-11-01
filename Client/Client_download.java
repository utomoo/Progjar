/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_download;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class Client_download {
    private static final int waiting = 0;
    private static final int dl = 1;
    private static final int up = 2;
    private static String fileName;
    private static Socket clientSocket;
    private static int state = waiting;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        clientSocket = new Socket("localhost", 9090);
        try( 
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
            
            while(true){
                    System.out.println("Test1");
                    if(state == waiting){
                        fromServer = in.readLine();
                        System.out.println("Server: " +fromServer);
                        
                        fromUser = stdIn.readLine();
                        if (fromUser != null) {
                            System.out.println("Client: " + fromUser);
                            out.println(fromUser);
                        }
                        if(fromUser.equals("dl")){
                            state = dl;
                        }
                        else if(fromUser.equals("up")){
                            state = up;
                        }
                    }
                    else if(state == dl){
                        receiveFile(fileName);
                        System.err.print("Enter file name: ");
                        fileName = stdIn.readLine();
                        out.println(fileName);
                        receiveFile(fileName);
                    }
                    else if(state == up){
                        System.err.print("Enter file name: ");
                        fromUser = stdIn.readLine();
                        sendFile(fromUser);
                    }
                   
                    System.out.println("Test2");
            }
        }
       catch(UnknownHostException e){
           System.out.println("Server tidak ditemukan");
       }
    }

    private static void receiveFile(String fileName) {
        try {
            int bytesRead;
            InputStream in = clientSocket.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("dari_server" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            in.close();

            System.out.println("File "+fileName+" received from Server.");
        } catch (IOException ex) {
            System.err.println("tidak dapat download");
        }
    }
        
    public static void sendFile(String fileName) {
        try {
            File myFile = new File(fileName);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = clientSocket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+fileName+" sent to Server.");
        } catch (Exception e) {
            System.err.println("File tidak ada!");
        }
    }
}
    

