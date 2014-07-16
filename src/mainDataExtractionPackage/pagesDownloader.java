package mainDataExtractionPackage;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;






import org.apache.tika.io.IOUtils;

import edu.uci.ics.crawler4j.crawler.Page;


public class pagesDownloader {

	
	public void downloadContent(Page page, int i, String path) throws IOException{
		 try {

			 FileOutputStream output = new FileOutputStream(new File(path +String.format("%05d", i)+".html"));
			 IOUtils.write(page.getContentData(), output);
			 
			 }
			    catch (MalformedURLException e) {
			  
			 e.printStackTrace();
			   } catch (IOException e) {
			  
			 e.printStackTrace();
			  }
			 }
		
}


