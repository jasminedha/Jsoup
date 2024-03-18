/*
 * Problem 2 Sell My Pet Food
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TargetedAd {

  public static void main(String[] args)
  {
    
    // create social media text file
    String initialUrl = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_othr_d_show_all_btm?ie=UTF8&reviewerType=all_reviews";
    initialUrl = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_getr_d_paging_btm_next_1?ie=UTF8&reviewerType=all_reviews&pageNumber=1";
    String urlBase = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_getr_d_paging_btm_next_";
    urlBase = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C?pageNumber=";
    scrapeData amznScraper = new scrapeData(20,urlBase,initialUrl,"socialMediaInfo.txt");
    amznScraper.scrape();

    DataCollector myDataCollector = new DataCollector();
    myDataCollector.setData("socialMediaInfo.txt", "targetWords.txt");
    String users = "";
    Map<String,Boolean> usersMap = new HashMap<String,Boolean>();

    String post;

    while (!(post = myDataCollector.getNextPost()).equals("NONE")){
      String[] parts = post.split("\\|",5);
      String username = parts[0];
      String title = parts[1].toLowerCase();
      String text = parts[2].toLowerCase();
      String reviewdate = parts[3].toLowerCase();
      String verification = parts[4].toLowerCase();
      //System.out.println(post);
      System.out.println("username is:" + username);
      //System.out.println("post is:" + postData);
      
      String targetWord;
      while(!(targetWord = myDataCollector.getNextTargetWord()).equals("NONE")){
        targetWord.toLowerCase();
        if (text.indexOf(targetWord)!=-1){
          if(!usersMap.containsKey(username)){
          users =  users +  " " + username;
          usersMap.put(username,true);
          }
        }
    }  
  }
  System.out.println(users);
  users = users.trim();
  //System.out.println(users);
  myDataCollector.prepareAdvertisement("Advertisement.txt", users, "Buy batteries for your car!");

}
}
