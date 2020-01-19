package ru.tweekyone.graduateQualificationWork.drugsBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
    private String currentURL;
    private String drugBasePath;
    private boolean hasNewURL;
    private Properties properties;
    
    public DrugBaseDownload(){
        properties = new Properties();
        drugBasePath = "data\\lp.zip";
        try(FileInputStream fis = new FileInputStream("src\\ru\\tweekyone\\graduateQualificationWork\\resources\\database.properties")){
            properties.load(fis);
            URL = properties.getProperty("DrugURL");
        } catch (IOException e){
            e.printStackTrace();
        }
        getCurrentURL();
        checkDrugBase();
    }
    
    private void getCurrentURL(){
        try{
            Document doc = Jsoup.connect("https://grls.rosminzdrav.ru/pricelims.aspx").timeout(5000).get();
            Elements elems = doc.select("a");
            for (Element e : elems){
                if((e.attr("abs:href")).contains("GetLimPrice")){
                    String[] URLparts = e.attr("abs:href").split("&"); 
                    currentURL = URLparts[0];
                }
            }
        } catch (SocketTimeoutException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    private void checkDrugBase(){
        checkURL();
        if (hasNewURL){
            File file = new File(drugBasePath);
            try(ReadableByteChannel rbc = Channels.newChannel(new URL(currentURL).openStream());
                FileOutputStream fos = new FileOutputStream(file)){
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
           } catch(FileNotFoundException e){
               e.printStackTrace();
           } catch(MalformedURLException e){
               e.printStackTrace();
           } catch (IOException e){
               e.printStackTrace();
           }
        }
    }
    
    private void checkURL(){
        if (URL == null || !currentURL.equals(URL)){
            hasNewURL = true;
            try(FileOutputStream fos = new FileOutputStream("src\\ru\\tweekyone\\graduateQualificationWork\\resources\\database.properties")){
                properties.setProperty("DrugURL", currentURL);
                properties.store(fos, "New URL for Dug Database!");
            } catch (IOException e){
                e.printStackTrace();  
            }
        } else{
            hasNewURL = false;
        }
    }
        
    public String getDrugBasePath(){
        return drugBasePath;
    }
}
