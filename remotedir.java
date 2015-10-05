/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.util.ArrayList;
import java.lang.Object;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.omg.IOP.Encoding;
/**
 *
 * @author lenovo
 */
public class remotedir {
    public ArrayList<String> inputcode(int x,String theInput){
        ArrayList<String> theOutput = new ArrayList<String>();
        //(String[] theOutput = new String[]) = null;
        if (theInput.equalsIgnoreCase("ls")){
            String str;
            File file = new File("C:/Users/lenovo/Desktop/");
        
            File[] files = file.listFiles();
            
            for (int i = 0 ; i < files.length ; i++){
                
                str = files[i].toString();
                theOutput.add(i,str);
                System.out.println(files[i]);
            }
        }
        else{
            theOutput.add(0,"Bye.");
        }
        return theOutput;
        //return null;
    }
}
