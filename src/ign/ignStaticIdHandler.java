package ign;
import java.io.FileWriter;
import java.io.IOException;

public class ignStaticIdHandler {
	
   private static ignStaticIdHandler instance = null;
   private int id = 0;
   
   
   protected ignStaticIdHandler() {
      // Exists only to defeat instantiation.
   }
   public synchronized static ignStaticIdHandler getInstance() {
      if(instance == null) {
         instance = new ignStaticIdHandler();
      }
      return instance;
   }
   
   public synchronized int generateId(){
	   id++;
	   return id;
   }
   
   public synchronized int getId(){
	   return id;
   }
   
   public synchronized void writeToLog(int id, String url, String path) throws IOException{
	   String filePath = path+"id2url.txt";
	   String newline = System.getProperty("line.separator");
	   FileWriter writer  = new FileWriter(filePath,true);
	   writer.append(String.format("%05d", id)+" "+url);
	   writer.append(newline);
	   writer.flush();
	   writer.close();
   }
}
