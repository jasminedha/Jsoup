
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class scrapeData {

    private int numPages;
    private String fileName;
    private String urlBase;
    private String initialUrl;

    public scrapeData(int numPages,String url, String initialUrl,String fileName)
    {
        this.numPages = numPages;
        this.urlBase = url;
        this.fileName = fileName;
        this.initialUrl = initialUrl;
    }

    public void scrape()
  {
    //String url = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_othr_d_show_all_btm?ie=UTF8&reviewerType=all_reviews";
   
      
try {
       // open file for writing here
       File file = new File(fileName);
       FileWriter outputfile = new FileWriter(file); 
       CSVWriter writer = new CSVWriter(outputfile,'|',
       CSVWriter.NO_QUOTE_CHARACTER,
       CSVWriter.DEFAULT_ESCAPE_CHARACTER,
       CSVWriter.DEFAULT_LINE_END); 
       int pageNumber = 1;

        while (pageNumber < numPages) {
            String url;
            if (pageNumber == 1){

                url = initialUrl;
            }
           /*  else if(pageNumber==2)
            {
                url = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_arp_d_paging_btm_next_2?reviewerType=all_reviews&pageNumber=2";
                url = "https://www.amazon.com/AmazonBasics-Performance-Alkaline-Batteries-Count/product-reviews/B00MNV8E0C/ref=cm_cr_getr_d_paging_btm_next_2?ie=UTF8&reviewerType=all_reviews&pageNumber=2";
                //pageNumber++;
                //continue;
            } */
            else{
                url =  urlBase + pageNumber ;//+ "ie=UTF8&reviewerType=all_reviews&pageNumber=" + pageNumber;

            }
            pageNumber ++;
            Document doc = Jsoup.connect(url).get();
            Elements reviewElements = doc.select(".review");
            if (reviewElements == null || reviewElements.isEmpty()) {
                System.out.println("breaking at page num" + pageNumber);
                break;
            }

            for (Element reviewElement : reviewElements) {

                Element titleElement = reviewElement.select(".review-title").first();
                if (titleElement == null) {
                    continue;
                }
                String title = titleElement.text();

                Element textElement = reviewElement.select(".review-text").first();
                if (textElement == null) {
                    continue;
                }
                String text = textElement.text();

                Element userElement = reviewElement.select(".a-profile-name").first();
                if (userElement == null) {
                    System.out.println("Missing username");
                    continue;
                }
                String userName = userElement.text();   

                Element isVerifiedElement = reviewElement.select(".review-data").first();
                if (isVerifiedElement == null) {
                    System.out.println("Missing Verified");
                    continue;
                }
                String isVerified = isVerifiedElement.text();

                Element reviewDateElement = reviewElement.select(".review-date").first();
                if (reviewDateElement == null) {
                    //LOG.error("Text element is null");
                    System.out.println("Missing review Date");
                    continue;
                }
                String reviewDate = reviewDateElement.text();

                // write to file here
                String[] data = {userName, title, text, reviewDate, isVerified }; 
                  writer.writeNext(data); 
            }

            
        }
        writer.close();
    }
    catch(IOException ex)
    {
        System.out.println(ex.getMessage());;
    }
    // close the file
   
       
    }


}