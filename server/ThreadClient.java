/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_download;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
class ThreadClient  implements Runnable{
    private Socket socket;
    private static final int waiting = 0;
    private static final int dl = 1;
    private static final int up = 2;
    
    private int state = waiting;
    
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
            remotedir rd = new remotedir();
            String namafile;
            System.out.println("Klien baru datang ....");
            String inputLine,outputLine = null;
            outputLine="terhubungan dengan server, silahkan ketik dl untuk download dan up untuk upload";
            out.println(outputLine);
            
            while(true) {
                inputLine = in.readLine();
                System.out.println("system test1");
                System.out.println(inputLine);
                if(inputLine.equals("dl") && state==waiting){
                    System.out.println("ls test");
                    state=dl;
                    namafile = rd.ls();
                    System.out.println(namafile);
                    sendFile(namafile);
                }
                else if(inputLine.equals("up") && state==waiting){
                    System.out.println("up test");
                    state = up;
                    receiveFile();
                }
                else if(inputLine.equals("dl")){
                    inputLine = "C:/Progjar/"+inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        sendFile(inputLine);
                    }
                }
                else if(inputLine.equals("up")){
                    System.out.println("up test2");
                    receiveFile();
                }
                System.out.println("system test2");
                //System.out.println("user: "+inputLine);
                //output = "hello";
                //out.println(output);
            }
            
            //out.close();
            //in.close();
            //socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receiveFile() {
        try {
            int bytesRead;

            DataInputStream clientData = new DataInputStream(socket.getInputStream());

            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("dari_client" + fileName));
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            clientData.close();

            System.out.println("File "+fileName+" received from client.");
        } catch (IOException ex) {
            System.err.println("Client error. Connection closed.");
        }
    }

    private void sendFile(String namafile) {
        try {
            //handle file read
            System.out.println(namafile);
            File myFile = new File(namafile);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            //handle file send over socket
            OutputStream os = socket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+namafile+" sent to client.");
        } catch (Exception e) {
            System.err.println("File tidak ada!");
        } 
    }
}
