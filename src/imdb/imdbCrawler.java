package imdb;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import mainDataExtractionPackage.pagesDownloader;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class imdbCrawler extends WebCrawler{
	private static final String PATH = "C:/Users/Fabro/Desktop/imdb_dataset/";
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

/**
* You should implement this function to specify whether the given url
* should be crawled or not (based on your crawling logic).
*/
@Override
public boolean shouldVisit(WebURL url) {
    String href = url.getURL().toLowerCase();
    return !FILTERS.matcher(href).matches() && href.startsWith("http://www.imdb.com/title/") && href.matches(".*(\\/)$");
    
}

/**
* This function is called when a page is fetched and ready to be processed
* by your program.
*/
@Override
public void visit(Page page) {
    int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();

    System.out.println("Docid: " + docid);
    System.out.println("URL: " + url);
    System.out.println("Domain: '" + domain + "'");
    System.out.println("Sub-domain: '" + subDomain + "'");
    System.out.println("Path: '" + path + "'");
    System.out.println("Parent page: " + parentUrl);
    System.out.println("Anchor text: " + anchor);
    
    /*Ask for the istance of (singleton) wrapper */
    
    if (page.getParseData() instanceof HtmlParseData) {
    		imdbWrapper wrapper = new imdbWrapper();
    		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            List<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
            
            /*Ask the wrapper to extract data */
            if(page.getWebURL().getURL().matches(".*(\\/)$")){
            try {
				wrapper.extractData(imdbStaticIdHandler.getInstance().getId()+1, html);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
    }

    Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
            System.out.println("Response headers:");
            for (Header header : responseHeaders) {
                    System.out.println("\t" + header.getName() + ": " + header.getValue());
            }
    }
    pagesDownloader downloader = new pagesDownloader();
    
    try {
   	
    	/* Dpwnload page and write to log */
    	if(page.getWebURL().getURL().matches(".*(\\/)$")){
		downloader.downloadContent(page, imdbStaticIdHandler.getInstance().generateId(),PATH);
		imdbStaticIdHandler.getInstance().writeToLog(imdbStaticIdHandler.getInstance().getId(), page.getWebURL().getURL(), PATH);
    	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    System.out.println("=============");
}
}
