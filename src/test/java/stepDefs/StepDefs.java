package stepDefs;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StepDefs {
	
	static WebDriver driver;
	static WebDriverWait wait;
	static String BefDel;
	
	@BeforeAll
	public static void ChromeBrowser() throws IOException, InterruptedException
		{
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver=new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
			driver.get("https://www.demoblaze.com/");
		}
	
	@Given("User is on Launch Page")
	public void user_is_on_launch_page() throws InterruptedException {
		driver.findElement(By.xpath ( "//li/a[@id='login2']")).click();
	}
	@When("User Login")
	public void user_login() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='loginusername']")).sendKeys("ninja");
		driver.findElement(By.xpath("//input[@id='loginpassword']")).sendKeys("hatori");
		driver.findElement(By.xpath("//div/button[contains(text(),'Log in')]")).click();
	}
	@Then("should display Home Page")
	public void should_display_home_page() throws InterruptedException {
		Thread.sleep(3000);
		 WebElement who = driver.findElement(By.xpath("//li/a[@id='nameofuser']"));
		 Thread.sleep(3000);
		 Assert.assertEquals(who.getText(), "Welcome ninja");
	}
	
	@Given("User is oh Home Page")
	public void user_is_oh_home_page() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='Home ']")).click();
	    
	}
	@When("User Selects the {string} and {string}")
	public void user_selects_the_and(String Category, String Product) throws InterruptedException {
		Thread.sleep(3000);
		String currentCategory = "//a[text()='"+Category+"']";
		driver.findElement(By.xpath(currentCategory)).click();
		Thread.sleep(2000);
		String currentItem = "//a[text()='"+Product+"']";
		driver.findElement(By.xpath(currentItem)).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement btn = driver.findElement(By.xpath("//a[text()='Add to cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(btn));
		btn.click();
	}
	@Then("Should Add items to the Cart")
	public void should_add_items_to_the_cart() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	@Given("User is on the Cart Page")
	public void user_is_on_the_cart_page() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='Home ']")).click();
		driver.findElement(By.xpath("//li/a[@id='cartur']")).click();
	}

	
	@When("User deletes an Item")
	public void user_deletes_an_item() throws InterruptedException {
		Thread.sleep(3000);
	    BefDel = driver.findElement(By.xpath("//div/h3")).getText();
	    driver.findElement(By.xpath("//td/a")).click();
	}
	@Then("the Item is Deleted")
	public void the_item_is_deleted() throws InterruptedException {
		Thread.sleep(3000);
	    String AftDel = driver.findElement(By.xpath("//div/h3")).getText();
	    if(BefDel.equals(AftDel)) {
			System.out.println("Product not Deleted");
		}
		else {
			System.out.println("Product Deleted");
		}
	}
	
	@When("User clicks PlaceOrder")
	public void user_clicks_place_order() throws InterruptedException {
		Thread.sleep(4000);
		driver.findElement(By.cssSelector("button[data-toggle='modal']")).click(); 
	    Thread.sleep (3000);
	    driver.findElement(By.cssSelector("#name")).sendKeys("shinchan");
		driver.findElement(By.cssSelector("#country")).sendKeys("japan"); 
		driver.findElement(By.cssSelector("#city")).sendKeys("kasukabe");
	    driver.findElement(By.cssSelector("#card")).sendKeys("no card");
		driver.findElement(By.cssSelector("#month")).sendKeys("this month"); 
		driver.findElement(By.cssSelector("#year")).sendKeys("final year"); 
		Thread.sleep (3000);
		driver.findElement(By.cssSelector("button[onclick='purchaseOrder()']")).click(); 
	}
	@Then("Order is Purchased")
	public void order_is_purchased() {
		WebElement postPurchase = driver.findElement(By.xpath("//h2[contains(text(),'Thank you')]"));
	    Assert.assertEquals(postPurchase.getText(), "Thank you for your purchase!");
	    driver.findElement(By.cssSelector("button[tabindex='1']")).click();
	}
	
	
	@AfterAll
	public static void close() 
	{
	   driver.close();
	}

}
