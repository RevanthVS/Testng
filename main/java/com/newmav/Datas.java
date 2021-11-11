package com.newmav;

import org.testng.annotations.DataProvider;


	
	import java.io.File;
	import java.io.IOException;
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	import java.util.Set;
	import java.util.concurrent.TimeUnit;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;
	import org.testng.asserts.SoftAssert;

	import io.github.bonigarcia.wdm.WebDriverManager;

	
		public class Datas {
			@DataProvider(name = "mobilename")
			public  Object[][] getdata() {
				
			
			return new Object [][] {{"iphone"}};
			}

			static WebDriver driver;
			static long startTime;
			
		@BeforeClass
		public static void launch() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		}
		@BeforeMethod
		public void beforeMethod() {
			System.out.println("Before Method");
			startTime = System.currentTimeMillis();
		}
		
		@Test(priority =-1,dataProvider = "mobile name")
		public void search(String value) {
			try {
				
			WebElement button = driver.findElement(By.xpath("//button[text()='X']"));
			button.isDisplayed();
			button.click();
			} catch (Exception e ) {
				System.out.println("pop up is not displayed");
			}
			
			WebElement search = driver.findElement(By.name("q"));
			search.sendKeys(value,Keys.ENTER);
		
			WebElement mobileName = driver.findElement(By.xpath("(//div[contains(text(),'"+value+"')]) [2]"));
			mobileName.click();
			
		}
		@Test(priority =1)
		public void windowHandling() {
			String parenturl = driver.getWindowHandle();
			Set<String> childurl= driver.getWindowHandles();
			for(String child:childurl ) {
				if( !parenturl.equals(child)) {
					driver.switchTo().window(child); 
					}
					}
			}
		@Test(priority =2)
		public void valueValidation(String value ) {
			String[] s = value.split("");
			WebElement mobilesName2 = driver.findElement(By.xpath("//span[contains(text(),'"+s[0]+"')]"));
			String name = mobilesName2.getText();
			System.out.println(name);
			Assert.assertTrue(mobilesName2.isDisplayed());
			SoftAssert s1 = new SoftAssert();
			s1.assertEquals("",name);
			System.out.println("value validation got executed successfully");
	  }
		@Test(priority =3,enabled = false)
	    public void dropdown() {
	    	JavascriptExecutor js = (JavascriptExecutor)driver;
	    	js.executeScript("window.scrollTo(0,document.body.scrolheight)");
	    	
	    }
		@Test(priority = 4 )
		  public static void screenshot() throws IOException {
		    	DateTimeFormatter dt = DateTimeFormatter.ofPattern("mmss");
		    	LocalDateTime now = LocalDateTime.now();
		    	String format = dt.format(now);
		    	System.out.println(format);
		    	
		    	System.out.println("After class");
		     TakesScreenshot tk =(TakesScreenshot)driver;
		     File source = tk.getScreenshotAs(OutputType.FILE);
		     File target = new File(".//target//report"+format+".png");
		     FileUtils.copyFile(source, target); 
		}
	    @AfterMethod
	      public void afterMethod() {
	      long endTime = System.currentTimeMillis();
	      long tt =endTime - startTime;
	      System.out.println("Time taken is :"+ tt);
	      }
	    @AfterClass

	     public static void closebrowser() throws IOException {
	    	DateTimeFormatter dt = DateTimeFormatter.ofPattern("HHmm");
	    	LocalDateTime now = LocalDateTime.now();
	    	String format = dt.format(now);
	    	System.out.println(format);
	    	System.out.println("After class");
	     TakesScreenshot tk =(TakesScreenshot)driver;
	     File source = tk.getScreenshotAs(OutputType.FILE);
	     File target = new File(".//target//report"+format+".png");
	     FileUtils.copyFile(source, target);
	     driver.quit();
	    }



	}

