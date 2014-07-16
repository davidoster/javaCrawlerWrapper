package kayak;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class kayakWrapper {

	 private static final String PATH = "C:/Users/Fabro/Desktop/kayak_dataset/";
	 
	
	public void extractHotelData(int id, String plainHTML) throws IOException{
		List<String> ListForCSV = new LinkedList<String>();
		
		Document currentDoc = Jsoup.parseBodyFragment(plainHTML);
		
		String hotelName = currentDoc.select("h1.hotelName").text();
		String hotelPrice = currentDoc.select("div.hotelPrice.hotelPriceDateless").first().text();
		String webSiteURL = currentDoc.select("span.hotelwebsite").select("a").text();
		
		String currentAddress = "";
		Elements address = currentDoc.select("span.address");
		
		for(Element currentElement : address){
			currentAddress += currentElement.text();
			currentAddress += " ";
		}
		
		
		String hotelPhone = currentDoc.select("span.hotelphone").first().text();
		
		
		String AVGevaluation = this.getAVGwebsitesEvaluation(currentDoc);
		
		
		String imgURL="http://www.kayak.it";
		Elements overviewPhotoElements = currentDoc.select("div.detailsOverviewPhoto").select("img");
		imgURL += overviewPhotoElements.first().attr("src").split("&")[0];
		
		if((!hotelName.isEmpty()) && (!currentAddress.isEmpty()) && (!hotelPhone.isEmpty()) && (!hotelPrice.isEmpty()) &&
				(!webSiteURL.isEmpty()) && (!imgURL.isEmpty())){
		ListForCSV.add(String.valueOf(id));
		ListForCSV.add(hotelName);
		ListForCSV.add(currentAddress);
		ListForCSV.add(hotelPhone);
		ListForCSV.add(AVGevaluation);
		ListForCSV.add(hotelPrice);
		ListForCSV.add(webSiteURL);
		ListForCSV.add(imgURL);
		
		this.writeToCSV(ListForCSV);
		}
		
		
		
}
	
	public void writeToCSV(List<String> fill) throws IOException{
		FileWriter fw = new FileWriter(PATH+"kayak.csv",true);
		PrintWriter out = new PrintWriter(fw);
		for(String value : fill){
			out.print(value);
			out.print(";");
		}
		out.println("");
		out.flush();
		out.close();
		
	}
	
	public String getAVGwebsitesEvaluation(Document currentDoc){
		
		int total = 0;
		int currentSum = 0;
		
		Elements oneStar = currentDoc.select("div.starsprite.short.star1");
		Elements twoStar = currentDoc.select("div.starsprite.short.star2");
		Elements threeStar = currentDoc.select("div.starsprite.short.star3");
		Elements fourStar = currentDoc.select("div.starsprite.short.star4");
		Elements fiveStar = currentDoc.select("div.starsprite.short.star5");
		
		for(Element currentElem: oneStar){
			if(currentElem.parent().hasClass("stars")){
				total++;
				currentSum += 1;
				}
		}
		
		for(Element currentElem: twoStar){
			if(currentElem.parent().hasClass("stars")){
				total++;
				currentSum += 2;
				}
		}
		
		for(Element currentElem: threeStar){
			if(currentElem.parent().hasClass("stars")){
				total++;
				currentSum += 3;
				}
		}
		
		for(Element currentElem: fourStar){
			if(currentElem.parent().hasClass("stars")){
				total++;
				currentSum += 4;
				}
		}
		
		for(Element currentElem: fiveStar){
			if(currentElem.parent().hasClass("stars")){
				total++;
				currentSum += 5;
				}
		}
		
		
		double rating = currentSum*1.0/total;
		
		return String.format("%.2f",rating);
	}
	
	
		
}