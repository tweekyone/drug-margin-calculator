package ru.tweekyone.graduateQualificationWork.drugsBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Пирожок
 */
public class DrugBaseDownload{
    private String URL;
    private Properties properties = new Properties();
    
    public DrugBaseDownload(){
        try{
            FileInputStream fis = new FileInputStream("src\\ru\\tweekyone\\graduateQualificationWork\\resources\\database.properties");
            properties.load(fis);
            URL = properties.getProperty("DrugUrl");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void currentURL(){
        String newURL = null;
        try{
            Document doc = Jsoup.connect("https://grls.rosminzdrav.ru/pricelims.aspx").get();
            Elements elems = doc.select("a");
            for (Element e : elems){
                if((e.attr("abs:href")).contains("GetLimPrice")){
                    String[] URLparts = e.attr("abs:href").split("&"); 
                    newURL = URLparts[0];
                }
            }
            
            String st = new String();
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
}
