package ign;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ignWrapper {

	 private static final String PATH = "C:/Users/Fabro/Desktop/ign_dataset/";

	public void extractGamesData(int id, String plainHTML) throws IOException{
		
		List<String> youMayLike = new LinkedList<String>();
		Document currentDoc = Jsoup.parseBodyFragment(plainHTML);
		List<String> ListForCSV = new LinkedList<String>();
		
		String nameElement = currentDoc.getElementsByClass("contentTitle").select("a").text();
		String image = currentDoc.getElementsByClass("highlight-boxArt").attr("src");
		String genre = currentDoc.select("a[href~=(?i)(/games/editors-choice?)]").text();
		String platform = currentDoc.getElementsByClass("contentPlatformsText").select("a[href~=(?i)(http://www.ign.com/games/)]").text();
		String publisher = currentDoc.getElementsByClass("gameInfo-list").select("a[href~=(?i)(http://games.ign.com/objects/)]").text().split(" ")[0];
		Elements gameRelated = currentDoc.getElementsByClass("gamesYouMayLike-name").select("a[href~=(?i)(http://www.ign.com/games/)]");
		
		if(!gameRelated.isEmpty()){
			for (Element gameRela : gameRelated){
			youMayLike.add(gameRela.text());
			}
		}
			else youMayLike.add("no game related");
		System.out.println("nome videogioco " + nameElement + "immagine " + image + "genere " +genre+ "piattaforma " +platform+ "pubblisher " + publisher );
			
			ListForCSV.add(String.valueOf(id));
			ListForCSV.add(nameElement);
			ListForCSV.add(image);
			ListForCSV.add(genre);
			ListForCSV.add(platform);
			ListForCSV.add(publisher);
			
			for(String game : youMayLike){
				ListForCSV.add(game);
				

				}
			
			this.writeToCSV(ListForCSV);
}
		
	public void writeToCSV(List<String> fill) throws IOException{
		FileWriter fw = new FileWriter(PATH+"ign.csv",true);
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