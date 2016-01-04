package Pokemon;

import chatmessage.AnotherMessage;
import chatmessage.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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
    private JLabel enemHp, myHp, enemName;
    Message objMsg = new Message();
    String username = null;
    String newuser = null;
    int statenewuser=0;
    int stateAwal = 0;
    int ourHP;
    String enemyHP;
    int flag = 0;
    int x;
    int y;
    
    public ThreadRead(Socket socket, javax.swing.JTextArea chatTextArea, String Username, JLabel jLabel2, JLabel jLabel5, JLabel jLabel8) throws IOException {
        this.socket = socket;
        this.ois = new ObjectInputStream(this.socket.getInputStream());
        this.ous = new ObjectOutputStream(this.socket.getOutputStream());
        this.done = false;
        this.chatTextArea = chatTextArea;
        this.username = Username;
        this.enemHp = jLabel5;
        this.enemName = jLabel8;
        this.myHp = jLabel2;
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
        //System.out.println("masuk123");
        try {
            while(!this.done) {
                try {
                    Object incoming = this.ois.readObject();
                    if(incoming instanceof Message) {
                        Message msg = (Message) incoming;
                        String pesan = msg.getMessage();
                        String temp3 ="1000";
                        String pengirim = msg.getSender();
                        String[] split = pesan.split(" ");
                        if(pesan.equals("newuser")){
                            if(pengirim.equals(this.username)){
                            } else {
                                System.out.println("masukNewUser");
                                newuser = msg.getSender();
                                System.out.println(statenewuser);
                                objMsg.setMessage("hello");
                                objMsg.setSender(this.username);
                                send(objMsg);
                                newuser = pengirim;
                                enemyHP = temp3;
                                //enemName.setText(pengirim);
                                //enemHp.setText(temp3);
                                statenewuser = 1;
                                System.out.println(statenewuser);
                            }
                        }
                        if(stateAwal == 0){
                            System.out.println("masuk hello");
                            if(pesan.equals("hello")){
                                System.out.println("masuk hello");
                                if(pengirim.equals(this.username)){
                                } else {
                                    newuser = msg.getSender();
                                    statenewuser = 1;
                                    System.out.println(statenewuser);
                                    newuser = pengirim;
                                    enemyHP = temp3;
                                    //enemName.setText(pengirim);
                                    //enemHp.setText(temp3);
                                    flag=1;
                                }
                            }
                            /*if(!pesan.equals("hello")){
                                stateAwal = 1;
                            }*/
                        }
                        if(split[0].equals("Serang:")){
                            if(pengirim.equals(this.username)){
                            } else {
                                flag=0;
                                if(split[2].equals("buff:")){
                                    enemHp.setText(split[5]);
                                }
                                else{
                                    System.out.println("masukSerang");
                                    String darah=myHp.getText();
                                    int i=Integer.parseInt(darah);
                                    int damage=Integer.parseInt(split[1]);
                                    i=i-damage;
                                    if(i<=0)
                                    {
                                        Message objMsg = new Message();
                                        objMsg.setMessage("Saya Kalah");
                                        objMsg.setSender(username);
                                        try {
                                            send(objMsg);
                                        } catch (IOException ex) {
                                            Logger.getLogger(PokemonGUI.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        System.out.println("Anda Kalah,Coba lagi!");
                                        myHp.setText("0");
                                        System.exit(0);
                                    }
                                    else{
                                        darah=String.valueOf(i);
                                        myHp.setText(darah);
                                    }
                                }
                            }
                        }
                        if(split[0].equals("x:")){
                            if(pengirim.equals(this.username)){
                            } else {
                                int i=Integer.parseInt(split[1]);
                                x = i;
                                i=Integer.parseInt(split[3]);
                                y = i;
                            }
                        }
                        System.out.println(msg.getSender() + " : " + msg.getMessage());
                        chatTextArea.setText(chatTextArea.getText().trim()+"\n"+msg.getSender()+" : " + msg.getMessage()); 
                    }
                    else if(incoming instanceof AnotherMessage)
                    {
                        AnotherMessage msg = (AnotherMessage) incoming;
                        System.out.print("Server : " + msg.getPesan());
                        chatTextArea.setText(chatTextArea.getText().trim()+"\n"+"Server : "+msg.getPesan());
                        objMsg.setMessage("newuser");
                        objMsg.setSender(this.username);
                        send(objMsg);
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