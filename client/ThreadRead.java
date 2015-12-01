package Pokemon;

import chatmessage.AnotherMessage;
import chatmessage.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lenovo
 */
public class ThreadRead implements Runnable{
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream ous;
    private boolean done;
    private javax.swing.JTextArea chatTextArea;
    
    public ThreadRead(Socket socket,javax.swing.JTextArea chatTextArea) throws IOException {
        this.socket = socket;
        this.ois = new ObjectInputStream(this.socket.getInputStream());
        this.ous = new ObjectOutputStream(this.socket.getOutputStream());
        this.done = false;
        this.chatTextArea = chatTextArea;
    }
    
    public void send(Message message) throws IOException {
        this.ous.writeObject(message);
        this.ous.flush();
        this.ous.reset();
    }

    public void stop() {
        this.done = true;
    }
    
    @Override
    public void run() {
        System.out.println("masuk123");
        try {
            while(!this.done) {
                try {
                    Object incoming = this.ois.readObject();
                    if(incoming instanceof Message) {
                        Message msg = (Message) incoming;
                        System.out.print(msg.getSender() + " : " + msg.getMessage());
                        chatTextArea.setText(chatTextArea.getText().trim()+"\n"+msg.getSender()+" : " + msg.getMessage()); 
                    }
                    else if(incoming instanceof AnotherMessage)
                    {
                        AnotherMessage msg = (AnotherMessage) incoming;
                        System.out.print("Server : " + msg.getPesan());
                        chatTextArea.setText(chatTextArea.getText().trim()+"\n"+"Server : "+msg.getPesan());
                        chatTextArea.setText(chatTextArea.getText().trim()+"\n"+"Username : ");
                    }
                } catch (IOException|ClassNotFoundException ex) {
                    this.done = true;
                    Logger.getLogger(ThreadRead.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            System.out.println("Bye");
            this.ois.close();
            this.ous.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadRead.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }    
}
