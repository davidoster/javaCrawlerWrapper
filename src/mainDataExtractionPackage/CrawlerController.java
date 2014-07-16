package mainDataExtractionPackage;


import ign.ignCrawler;
import imdb.imdbCrawler;
import kayak.kayakCrawler;
import lastFm.lastFmCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerController {
	
	
    public static void main(String[] args) throws Exception {
    	/*
         * crawlStorageFolder is a folder where intermediate crawl data is
         * stored.
         */
        String crawlStorageFolder = "/home/Fabro/Desktop/tmp";
      
        /*
         * numberOfCrawlers shows the number of concurrent threads that should
         * be initiated for crawling.
         */
        int numberOfCrawlers = Integer.parseInt("4");
        
        /**************************** LASTFM CRAWLER CONFIG ******************************/
        CrawlConfig lastfmconfig = new CrawlConfig();
    
        lastfmconfig.setCrawlStorageFolder(crawlStorageFolder);
        lastfmconfig.setPolitenessDelay(500);
        lastfmconfig.setMaxDepthOfCrawling(-1);
        lastfmconfig.setMaxPagesToFetch(10000); 
        lastfmconfig.setResumableCrawling(false);
        PageFetcher lastfmpageFetcher = new PageFetcher(lastfmconfig);
        RobotstxtConfig lastfmrobotstxtConfig = new RobotstxtConfig();
        lastfmrobotstxtConfig.setEnabled(false);
        RobotstxtServer lastfmrobotstxtServer = new RobotstxtServer(lastfmrobotstxtConfig, lastfmpageFetcher);

        /*Lastfm dataset controller */    
        CrawlController lastfmcontroller = new CrawlController(lastfmconfig, lastfmpageFetcher, lastfmrobotstxtServer);
        
        lastfmcontroller.addSeed("http://www.lastfm.it/");
        lastfmcontroller.addSeed("http://www.lastfm.it/music");
        
        //lastfmcontroller.start(lastFmCrawler.class, numberOfCrawlers);    
     
        /***************************** KAYAK CRAWLER CONFIG **********************************/
        CrawlConfig kayakconfig = new CrawlConfig();

        kayakconfig.setCrawlStorageFolder(crawlStorageFolder);
        kayakconfig.setPolitenessDelay(500);
        kayakconfig.setMaxDepthOfCrawling(-1);
        kayakconfig.setMaxPagesToFetch(10000);
        kayakconfig.setResumableCrawling(false);
        PageFetcher kayakpageFetcher = new PageFetcher(kayakconfig);
        RobotstxtConfig kayakrobotstxtConfig = new RobotstxtConfig();
        kayakrobotstxtConfig.setEnabled(false);
        RobotstxtServer kayakrobotstxtServer = new RobotstxtServer(kayakrobotstxtConfig, kayakpageFetcher);

        /*kayak dataset controller */    
        CrawlController kayakcontroller = new CrawlController(kayakconfig, kayakpageFetcher, kayakrobotstxtServer);
        
        kayakcontroller.addSeed("http://www.kayak.it/");
        kayakcontroller.addSeed("http://www.kayak.it/hotels");
        
        //kayakcontroller.start(kayakCrawler.class, numberOfCrawlers);    
        
        /******************************* IMDB CRAWLER CONFIG ********************************/
        CrawlConfig imdbConfig = new CrawlConfig();

        imdbConfig.setCrawlStorageFolder(crawlStorageFolder);
        imdbConfig.setPolitenessDelay(500);
        imdbConfig.setMaxDepthOfCrawling(-1);
        imdbConfig.setMaxPagesToFetch(10000);
        imdbConfig.setResumableCrawling(false);
        PageFetcher imdbPageFetcher = new PageFetcher(imdbConfig);
        RobotstxtConfig imdbrobotstxtConfig = new RobotstxtConfig();
        imdbrobotstxtConfig.setEnabled(false);
        RobotstxtServer allmovierobotstxtServer = new RobotstxtServer(imdbrobotstxtConfig, imdbPageFetcher);
     
        /*imdb dataset controller */    
        CrawlController imdbcontroller = new CrawlController(imdbConfig, imdbPageFetcher, allmovierobotstxtServer);
        
        /*Seed imdb*/
        for(int i = 1; i < 10000; i=i+100){
       	 imdbcontroller.addSeed("http://www.imdb.com/search/title?at=0&count=100&sort=moviemeter,asc&start="+i+"&title_type=feature,tv_series");
       }
        
        imdbcontroller.start(imdbCrawler.class, numberOfCrawlers);
        
        /******************************* IGN CRAWLER CONFIG ********************************/
        CrawlConfig ignconfig = new CrawlConfig();

        ignconfig.setCrawlStorageFolder(crawlStorageFolder);
        ignconfig.setPolitenessDelay(500);
        ignconfig.setMaxDepthOfCrawling(-1);
        ignconfig.setMaxPagesToFetch(10000);
        ignconfig.setResumableCrawling(false);
        PageFetcher ignpageFetcher = new PageFetcher(ignconfig);
        RobotstxtConfig ignrobotstxtConfig = new RobotstxtConfig();
        ignrobotstxtConfig.setEnabled(false);
        RobotstxtServer ignrobotstxtServer = new RobotstxtServer(ignrobotstxtConfig, ignpageFetcher);
     
        /*ign dataset controller */    
        CrawlController igncontroller = new CrawlController(ignconfig, ignpageFetcher, ignrobotstxtServer);
        
        igncontroller.addSeed("http://www.ign.com");
        igncontroller.addSeed("http://www.ign.com/games");
        
        //igncontroller.start(ignCrawler.class, numberOfCrawlers);
       }
}
