/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
class remotedir {
    private static final int waiting = 0;
    private static final int dl = 1;
    private static final int up = 2;
    String dir = "C:/Progjar/";
    String str;
    public String ls() throws IOException{
        String theOutput=null;
        File file = new File(dir);
        File fileLS = new File(dir + "fileprog.txt");
        FileWriter fw = new FileWriter(fileLS.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
          
        File[] files = file.listFiles(); 
        
	// Jika file tidak ada maka dibuat
	if (!fileLS.exists()) {
            fileLS.createNewFile();
	}
        for (int i = 0 ; i < files.length ; i++){ 
                  
            str = files[i].toString()+"\n"; 
            bw.write(str);
            System.out.println(files[i]);
        }
        bw.close();
        
        theOutput= fileLS.toString();
        return theOutput;
    }
    
}
