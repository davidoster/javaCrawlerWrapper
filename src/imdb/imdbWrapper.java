package imdb;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class imdbWrapper {

	 private static final String PATH = "C:/Users/Fabro/Desktop/imdb_dataset/";

	
	public void extractData(int id, String plainHTML) throws IOException{
		
		Document currentDoc = Jsoup.parseBodyFragment(plainHTML);
		List<String> ListForCSV = new LinkedList<String>();
		List<String> filmgenres = new LinkedList<String>();
		
		Elements titleElement = currentDoc.select("h1.header").select("span.itemprop");
		Elements yearElement = currentDoc.select("h1.header").select("span.nobr").select("a");
		Elements directorElement = currentDoc.select("div[itemprop=director]").select("a").select("span");
		Elements filmCoverElement = currentDoc.select("div.image").select("a").select("img");
		Elements ratingsElement = currentDoc.select("div.titlePageSprite.star-box-giga-star");
		Elements genres = currentDoc.select("div[itemprop=genre]").select("a");
		
		if((!titleElement.isEmpty())){
			String title = titleElement.text();
			System.out.println("TITLE: "+title);
			
			String filmCoverUrl = filmCoverElement.attr("src");
			System.out.println("COVER URL: "+filmCoverUrl);
			
			String director = directorElement.text();
			System.out.println("DIRECTOR: "+director);
			
			String year = yearElement.text();
			System.out.println("YEAR: "+year);
			
			String ratings = ratingsElement.text();
			System.out.println("RATINGS: "+ratings);
				
			
			if(!genres.isEmpty()){
				for(Element genre : genres){
					filmgenres.add(genre.text());
					System.out.println("GENRES: "+genre.text());
					
				}
				}
				else filmgenres.add("0");
			
			ListForCSV.add(String.valueOf(id));
			ListForCSV.add(title);
			ListForCSV.add(filmCoverUrl);
			ListForCSV.add(year);
			ListForCSV.add(director);
			ListForCSV.add(ratings);
			
			for(String g : filmgenres){
				ListForCSV.add(g);
				
			}
			this.writeToCSV(ListForCSV);

			
	}
		
		
}
	
	public void writeToCSV(List<String> fill) throws IOException{
		FileWriter fw = new FileWriter(PATH+"imdb.csv",true);
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
