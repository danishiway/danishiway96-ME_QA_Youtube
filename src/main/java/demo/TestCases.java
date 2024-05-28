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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

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
    driver.get(url);
    ((WebDriver) driver).manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
    	 System.out.println("Start Test case: testCase01");
    	    driver.get(url);

    	    boolean status = verifyHomePage();
    	    assert status : "Test Case 01 Failed";
    	    verifyAbout();
    	    System.out.println("Test Case 01 Status: Passed");
      
      System.out.println("end Test of cases");
    }
    
    public  void testCase02(){
     
      System.out.println("Start Test case: testCase02");
      navigateToHomePage(); 
      clickTab("Movies");
      clickScrollRightButtonMultipleTimes(3,"/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse[2]/ytd-two-column-browse-results-renderer/div[1]/ytd-section-list-renderer/div[2]/ytd-item-section-renderer[2]/div[3]/ytd-shelf-renderer/div[1]/div[2]/yt-horizontal-list-renderer/div[3]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div");
      WebElement movie = findMovie("//*[@id='items']/ytd-grid-movie-renderer[16]");
      String maturityRating = getMaturityRating(movie);
      assert maturityRating.contains("A") : "Maturity rating does not contain 'A'";
      String genre = getGenre(movie);
      assert genre.equalsIgnoreCase("Comedy") || genre.equalsIgnoreCase("Animation") : "Genre is not Comedy or Animation";
      System.out.println("end Test of cases");
    }
    public  void testCase03(){
    	 System.out.println("Start Test case: testCase03");
    	navigateToHomePage(); 
    	clickTab("Music");
        clickScrollRightButtonMultipleTimes(3, "//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button");
        String playlistName = getPlaylistName("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3");
        System.out.println("Playlist Name: " + playlistName);
        int numValue = getTrackCount("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/p");
        Assert.assertTrue(numValue <= 50, "numValue should be less than or equal to 50");
        System.out.println("End Test of cases");
    }
    public  void testCase04(){
    	navigateToHomePage();
      
      System.out.println("Start Test case: testCase04");
      clickTab("News");
      getNewsDetails();
      
      System.out.println("end Test of cases");
    }
    
    public void navigateToHomePage() {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
        
    }

    public boolean verifyHomePage() {
        return driver.getCurrentUrl().equals(url);
    }
public  void verifyAbout() {
	
	WebElement aboutButton= driver.findElement(By.xpath("//a[contains(text(),'About')]"));
	JavascriptExecutor js = (JavascriptExecutor)driver;
    
	
    js.executeScript("arguments[0].scrollIntoView();", aboutButton);
    aboutButton.click();
    
    String aboutText= driver.findElement(By.xpath("//*[@id='content']/section/p[1]")).getText();
    System.out.println("About Youtube :" +aboutText);
}
private WebElement findMovie(String path) {
    return driver.findElement(By.xpath(path));
}

private String getMaturityRating(WebElement movie) {
    return movie.findElement(By.xpath("./ytd-badge-supported-renderer/div[2]")).getText();
}


private String getGenre(WebElement movie) {
    return movie.findElement(By.xpath(".//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']")).getText();
}


    

// Method to click a tab by its title
public void clickTab(String title) {
    WebElement tab = driver.findElement(By.xpath("//a[@title='" + title + "']"));
    tab.click();
}

// Method to click the scroll right button multiple times
public void clickScrollRightButtonMultipleTimes(int times, String path) {
    WebElement scrollRightButton =  driver.findElement(By.xpath(path));
    for (int i = 0; i < times; i++) {
        scrollRightButton.click();
    }
}

// Method to get playlist name
public String getPlaylistName(String path) {
    WebElement playlist =  driver.findElement(By.xpath(path));
    return playlist.getText();
}

// Method to get track count
public int getTrackCount(String path) {
    WebElement trackCountElement;
    String trackCount = "0";
    
    try {
        trackCountElement = driver.findElement(By.xpath(path));
        trackCount = trackCountElement.getText().replaceAll("\\D+", "");
    } catch (Exception e) {
        // Handle exception, e.g., log it or throw a custom exception
        e.printStackTrace();
    }
    
    return Integer.parseInt(trackCount);
}

public void getNewsDetails () {
	
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



}
