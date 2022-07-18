package trelloproj.test;

import dataProvider.ConfigFileReader;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class NewTest {
	WebDriver driver;
	ConfigFileReader configFileReader;
@Test
 public void login()throws InterruptedException{
		 
		 configFileReader = new ConfigFileReader();
		  driver.findElement(By.linkText("Log in")).click();
		  driver.findElement(By.id("user")).sendKeys(configFileReader.getUserName());
		  Thread.sleep(5000);
		  driver.findElement(By.id("login")).click();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		  driver.findElement(By.id("password")).sendKeys(configFileReader.getPassword());
		  driver.findElement(By.id("login-submit")).click();
		  Thread.sleep(10000);
		  if((driver.findElement(By.xpath("//button[contains(@title,'Amir')]"))).isDisplayed()) {
			  System.out.println("Login Successful");
		  }else {
			  System.out.println("Login Unsuccessful");
		  }
		  
		  //Creation of New Board
		  List<WebElement> listOfElements=driver.findElements(By.xpath("//*[@class='boards-page-board-section-list']/li"));
			for (WebElement webElement : listOfElements) {
			//System.out.println(webElement.getText());
			if(webElement.getText().equals("Create new board")) {
			Thread.sleep(3000);
			webElement.click();
			driver.findElement(By.xpath("//input[@data-test-id='create-board-title-input']")).sendKeys("TestBoard");
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@data-test-id='create-board-submit-button']")).click();
				}
			}
			Thread.sleep(10000);
			if((driver.findElement(By.xpath("//h1[contains(text(),'TestBoard')]"))).isDisplayed()) {
				  System.out.println("Board creation Successful");
			  }else {
				  System.out.println("Board creation Unsuccessful");
			  }
			
			//Adding List A and List B in the Test Board
			 Thread.sleep(3000);
			 driver.findElement(By.xpath("//input[@class='list-name-input']")).sendKeys("List A");
				driver.findElement(By.xpath("//input[@value='Add list']")).click();
				Thread.sleep(10000);
				if((driver.findElement(By.xpath("//textarea[contains(text(),'List A')]"))).isDisplayed()) {
					  System.out.println("List A creation Successful");
				  }else {
					  System.out.println("List A creation Unsuccessful");
				  }
				
				driver.findElement(By.xpath("//input[@class='list-name-input']")).sendKeys("List B");
				driver.findElement(By.xpath("//input[@value='Add list']")).click();
				Thread.sleep(10000);
				if((driver.findElement(By.xpath("//textarea[contains(text(),'List B')]"))).isDisplayed()) {
					  System.out.println("List B creation Successful");
				  }else {
					  System.out.println("List B creation Unsuccessful");
				  }
				
				//Adding new card in List A
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				driver.findElement(By.xpath("//span[@class='js-add-a-card']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//textarea[@placeholder='Enter a title for this card…']")).sendKeys("TestCard");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//div/input[@value='Add card']")).click();
				Thread.sleep(10000);
				if((driver.findElement(By.xpath("//span[contains(text(),'TestCard')]"))).isDisplayed()) {
					  System.out.println("Card creation Successful");
				  }else {
					  System.out.println("Card creation Unsuccessful");
				  }
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@class='icon-lg icon-close dark-hover js-cancel']")).click();
				driver.findElement(By.xpath("//*[@id=\"board\"]/div[1]/div/div[2]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[@class='icon-sm icon-move']")).click();
				Thread.sleep(2000);
				
				//Moving the card from List A to List B
				Select listDrop= new Select(driver.findElement(By.xpath("//select[@class='js-select-list']")));
				listDrop.selectByVisibleText("List B");
				
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@class='nch-button nch-button--primary wide js-submit']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@class='icon-md icon-close dialog-close-button js-close-window']")).click();
				Thread.sleep(2000);

				//Getting the X and Y coordinates of the card.
				WebElement XYelement = driver.findElement(By.xpath("//*[@id=\"board\"]/div[2]/div/div[2]/a"));
				Point point = XYelement.getLocation();
				int xcord = point.getX();
				System.out.println("The X coordinate of the card is "+xcord);
				int ycord =point.getY();
				System.out.println("The Y coordinate of the card is "+ycord);
				Thread.sleep(10000);
				if((driver.findElement(By.xpath("//span[contains(text(),'TestCard')]"))).isDisplayed()) {
					  System.out.println("Card moved");
				  }else {
					  System.out.println("Card not moved");
				  }

}
 

@BeforeTest

//Initiating the chrome driver and starting the browser
  public void beforeTest() throws InterruptedException {
	  configFileReader = new ConfigFileReader();
	  System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
		driver = new ChromeDriver();
	  	driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.navigate().to(configFileReader.getApplicationUrl());
		//driver.get("https://www.trello.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		
  }

@AfterTest

//log out and closing the driver.
  public void afterTest() throws InterruptedException {
	  
		driver.findElement(By.cssSelector("button[aria-label = 'Open member menu']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.findElement(By.cssSelector("button[data-test-id = 'header-member-menu-logout']")).click();
		driver.close();
		
  }

}
