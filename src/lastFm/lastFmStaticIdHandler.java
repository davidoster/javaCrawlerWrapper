package lastFm;
import java.io.FileWriter;
import java.io.IOException;

public class lastFmStaticIdHandler {
	
   private static lastFmStaticIdHandler instance = null;
   private int id = 0;
   
   
   protected lastFmStaticIdHandler() {
      // Exists only to defeat instantiation.
   }
   public synchronized static lastFmStaticIdHandler getInstance() {
      if(instance == null) {
         instance = new lastFmStaticIdHandler();
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