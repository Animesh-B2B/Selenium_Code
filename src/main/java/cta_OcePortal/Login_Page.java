package cta_OcePortal;

import org.openqa.selenium.Dimension;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page {
	
	WebDriver driver;
	
	public Login_Page(WebDriver driver) {
		this.driver=driver;
	}
	
	 public void Login() throws Exception {	        
	      
	       driver.get("https://vega.test2.swisscom.ch/myswisscom");		      
	       Thread.sleep(10000);
	       driver.findElement(By.xpath("//*[@id='username']")).sendKeys(TestDataProvider.getUser());
	       driver.findElement(By.xpath("//input[@id='continueButton']")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//input[@id='password']")).sendKeys(TestDataProvider.getPass());
	       driver.findElement(By.xpath("//button[@id='submitButton']")).click();
	     //  driver.close();
	       
	    }
	

	
}
