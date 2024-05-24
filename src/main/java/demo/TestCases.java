package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;


import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    WebDriver driver;
    String url= "https://www.youtube.com/";
    public TestCases()
    {  WebDriverManager.chromedriver().timeout(30).setup();
    ChromeOptions options = new ChromeOptions();
    LoggingPreferences logs = new LoggingPreferences();

    
    logs.enable(LogType.BROWSER, Level.ALL);
    logs.enable(LogType.DRIVER, Level.ALL);
    options.setCapability("goog:loggingPrefs", logs);

    options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
    System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

    driver = new ChromeDriver(options);

    
    driver.manage().window().maximize();
    ((WebDriver) driver).manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
    	boolean status= false;
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.google.com");
        navigateToHomePage();
      status = verifyHomePage();
      System.out.println("Test Case 01 Status: " + (status ? "Passed" : "Failed"));
      SelectAbout();
      FlimTab();
      musicTab();
      NewsTab ();
     
        System.out.println("end Test case: testCase02");
    }
    public void navigateToHomePage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
        
    }

    public boolean verifyHomePage() {
        return driver.getCurrentUrl().equals(url);
    }
public  void SelectAbout() {
	
	WebElement aboutButton= driver.findElement(By.xpath("//a[contains(text(),'About')]"));
	JavascriptExecutor js = (JavascriptExecutor)driver;
    
	
    js.executeScript("arguments[0].scrollIntoView();", aboutButton);
    aboutButton.click();
    
    String aboutText= driver.findElement(By.xpath("//*[@id='content']/section/p[1]")).getText();
    System.out.println("About Youtube :" +aboutText);
}
public void FlimTab() {
	navigateToHomePage();
	WebElement movieButton= driver.findElement(By.xpath("//a[@title='Movies']"));
	JavascriptExecutor js = (JavascriptExecutor)driver;
    
	
    js.executeScript("arguments[0].scrollIntoView();", movieButton);
    movieButton.click();
    
   
    WebElement scrollRightButton = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-section-list-renderer/div[2]/ytd-item-section-renderer[2]/div[3]/ytd-shelf-renderer/div[1]/div[2]/yt-horizontal-list-renderer/div[3]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div"));
    scrollRightButton.click();
    scrollRightButton.click();
    scrollRightButton.click();

    
    WebElement movie = driver.findElement(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]"));
    String maturityRating = driver.findElement(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]")).getText();
    if(maturityRating.contains("A")) {
    	System.out.println("A is marked");
    }

    
    WebElement movieGenre = movie.findElement(By.xpath(".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
    String genre = movieGenre.getText();
   if(genre.equalsIgnoreCase("Comedy") || genre.equalsIgnoreCase("Animation")) {
	   System.out.println("Comedy cato");
   }
    
}
    

public void musicTab() {
	navigateToHomePage();
	  WebElement musicTab = driver.findElement(By.xpath("//a[@title='Music']"));
      musicTab.click();

      
      WebElement scrollRightButton = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-section-list-renderer/div[2]/ytd-item-section-renderer[1]/div[3]/ytd-shelf-renderer/div[1]/div[2]/yt-horizontal-list-renderer/div[3]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div"));
      scrollRightButton.click();
      scrollRightButton.click();
      scrollRightButton.click();

      
      String playlistName = driver.findElement(By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3")).getText();
     
      System.out.println("Playlist Name: " + playlistName);

      String trackCount = ((WebElement) driver.findElement(By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/p"))).getText();
      String  trackValue= trackCount.replaceAll("\\D+", "");

      
      int numValue = Integer.parseInt(trackValue);
      
   if(numValue <= 50) {
	   System.out.println("musicTrack");
   }
}

public void NewsTab () {
	navigateToHomePage();
	 WebElement newsTab = driver.findElement(By.xpath("//a[@title='News']"));
     newsTab.click();

   

     for (int i = 1; i <= 3; i++) {
         String postTitle = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-rich-grid-renderer/div[6]/ytd-rich-section-renderer[2]/div/ytd-rich-shelf-renderer/div[1]/div[2]/ytd-rich-item-renderer["+i+"]/div/ytd-post-renderer/div[1]/div[1]/div[2]/a/span")).getText();
         String postBody = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-rich-grid-renderer/div[6]/ytd-rich-section-renderer[2]/div/ytd-rich-shelf-renderer/div[1]/div[2]/ytd-rich-item-renderer[\"+i+\"]/div/ytd-post-renderer/div[1]/div[2]/div[1]/yt-formatted-string/span")).getText();

         System.out.println("Post " + i + " Title: " + postTitle);
          System.out.println("Post " + i + " Body: " + postBody);
     }

     int totalLikes = 0;
     for (int i = 1; i <= 3; i++) {
    	 String likeButton = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-rich-grid-renderer/div[6]/ytd-rich-section-renderer[2]/div/ytd-rich-shelf-renderer/div[1]/div[2]/ytd-rich-item-renderer["+i+"]/div/ytd-post-renderer/div[1]/div[3]/ytd-comment-action-buttons-renderer/div[1]/span[2]")).getText();
      
         int likes = likeButton.isEmpty() ? 0 : Integer.parseInt(likeButton.replaceAll("[^0-9]", ""));
         totalLikes += likes;
     }

     System.out.println("Total Likes on 3 Posts: " + totalLikes);
}

			 public void YoutubeViewSum () {
			        try (FileInputStream fis = new FileInputStream("C://Users//TALHA ABDUL KHADER//eclipse-workspace//selenium-starter-2-main//qa_codeathon.xlsx");
			             Workbook workbook = new XSSFWorkbook(fis)) {

			            Sheet sheet = workbook.getSheetAt(0);
			            Iterator<Row> iterator = sheet.iterator();

			            while (iterator.hasNext()) {
			                Row currentRow = iterator.next();
			                String searchTerm = currentRow.getCell(0).getStringCellValue();
			                searchAndScroll(searchTerm);
			            }
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
			    private void searchAndScroll(String searchTerm){
			        WebElement searchBox = driver.findElement(By.name("search_query"));
			        searchBox.sendKeys(searchTerm);
			        searchBox.submit();
			        long totalViews = 0;
			        while (totalViews < 100_000_000) { 
			            scrollDown();
			            
			            List<WebElement> videoViewsElements = driver.findElements(By.xpath("//div[@id='metadata-line']//span[contains(text(),'views')]"));
			            for (WebElement element : videoViewsElements) {
			                String viewsText = element.getText();
			                long views = extractViews(viewsText);
			                totalViews += views;
			            }
			        
			        }
			    }

			    private void scrollDown() {
			        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			        
			    }

			    private long extractViews(String viewsText) {
			       
			        return 0; 
			    }



}
