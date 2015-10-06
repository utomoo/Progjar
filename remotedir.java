package server;  
 
import java.io.File; 
import java.util.ArrayList; 
import java.lang.Object; 
 import java.nio.file.Files; 
 import java.nio.file.Paths; 
 import org.omg.IOP.Encoding; 
  
 public class remotedir { 
     public ArrayList<String> inputcode(int x,String theInput){ 
         ArrayList<String> theOutput = new ArrayList<String>(); 
         String dir = "C:/"; 
         //(String[] theOutput = new String[]) = null; 
         if (theInput.equalsIgnoreCase("ls")){ 
             String str; 
             File file = new File(dir); 
          
             File[] files = file.listFiles(); 
             
             for (int i = 0 ; i < files.length ; i++){ 
                  
                 str = files[i].toString(); 
                 theOutput.add(i,str); 
                 System.out.println(files[i]); 
             }
         }
             else if(theInput.equalsIgnoreCase("mkdir")){ 
                 File file2 = new File(dir +"Directory1"); 
                 if (!file2.exists()) { 
 		            if (file2.mkdir()) { 
                         theOutput.add(0,"Directory is created!"); 
 			System.out.println("Directory is created!"); 
		}  
                     else { 
                         theOutput.add(0,"Failed to create directory!"); 
 			System.out.println("Failed to create directory!"); 
 		}  
 
                 } 
         }
             else if (theInput.equalsIgnoreCase("cd")){
            System.out.println("Currently working directory:");
            System.out.println(System.getProperty(dir));
            System.out.println(new File(".").getAbsolutePath());
            System.out.println("Changing working directory");
            System.setProperty(dir, "C:/");
            System.out.println("Currently working directory: c");
            System.out.println(System.getProperty(dir));
            System.out.println(new File(".").getAbsolutePath());}
        
        else if (theInput.equalsIgnoreCase("cd temp")){
            System.out.println("Currently working directory:");
            System.out.println(System.getProperty(dir));
            System.out.println(new File(".").getAbsolutePath());
            System.out.println("Changing working directory");
            System.setProperty(dir, "C:/temp");
            System.out.println("Currently working directory:");
            theOutput.add(0,"Currently working directory: to temp");
            System.out.println(System.getProperty(dir));
            System.out.println(new File(".").getAbsolutePath());}
         else{ 
            theOutput.add(0,"Bye."); 
         } 
         return theOutput; 
     } 
         
  } 
