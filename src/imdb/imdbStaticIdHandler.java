package imdb;
import java.io.FileWriter;
import java.io.IOException;

public class imdbStaticIdHandler {
	
   private static imdbStaticIdHandler instance = null;
   private int id = 0;
   
   
   protected imdbStaticIdHandler() {
      // Exists only to defeat instantiation.
   }
   public synchronized static imdbStaticIdHandler getInstance() {
      if(instance == null) {
         instance = new imdbStaticIdHandler();
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
   
   /*Write to id2url */
   
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