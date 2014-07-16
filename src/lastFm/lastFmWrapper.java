package lastFm;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class lastFmWrapper {

	 private static final String PATH = "C:/Users/Fabro/Desktop/lastfm_dataset/";

	
	public void extractSongData(int id, String plainHTML) throws IOException{
		
		List<String> albumToBuy = new LinkedList<String>();
		Document currentDoc = Jsoup.parseBodyFragment(plainHTML);
		List<String> ListForCSV = new LinkedList<String>();
		Elements albums = currentDoc.getElementsByClass("other-release");
		
		Elements titleElement = currentDoc.select("meta[property=og:title]");
		Elements albumCoverElement = currentDoc.select("meta[property=og:image]");
		Elements scrobblingElement = currentDoc.select("meta[itemprop=interactionCount]");
		String spotifyDataUriElement = currentDoc.getElementsByClass("spotify-inline-play-button").attr("data-uri").split(":")[2];
		if((!titleElement.isEmpty()) &&(!albumCoverElement.isEmpty()) && (!scrobblingElement.isEmpty())){
			String title = titleElement.first().attr("content");
			
			String[] compositeTitle = title.split("–");
			String artist = compositeTitle[0];
			String song = compositeTitle[1];
			System.out.println("CANTANTE: "+artist+ "CANZONE: "+song);
		
		
		
		
			String albumCoverUrl = albumCoverElement.first().attr("content");
			System.out.println("COVER URL: "+albumCoverUrl);
		
			String userPlays = scrobblingElement.first().attr("content").split(":")[1];
			System.out.println("USER PLAYS: "+userPlays);
			System.out.println("SPOTIFY: "+spotifyDataUriElement);
			if(!albums.isEmpty()){
			for(Element album : albums){
				
				albumToBuy.add("http://www.lastfm.it/"+album.attr("href"));
				System.out.println("ALBUM I: "+album.attr("href"));
				
			}
			}
			else albumToBuy.add("0");
			ListForCSV.add(String.valueOf(id));
			ListForCSV.add(artist);
			ListForCSV.add(song);
			ListForCSV.add(albumCoverUrl);
			ListForCSV.add(userPlays);
			ListForCSV.add(spotifyDataUriElement);
			for(String x : albumToBuy){
			ListForCSV.add(x);
			
			}
			this.writeToCSV(ListForCSV);
	}
		
		
}
	
	public void writeToCSV(List<String> fill) throws IOException{
		FileWriter fw = new FileWriter(PATH+"lastFm.csv",true);
		PrintWriter out = new PrintWriter(fw);
		for(String value : fill){
			out.print(value);
			out.print(";");
		}
		out.println("");
		out.flush();
		out.close();
		
	}
}