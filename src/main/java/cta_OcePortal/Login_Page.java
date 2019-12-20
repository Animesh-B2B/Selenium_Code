package cta_OcePortal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page {
	
	WebDriver driver;
	
	public Login_Page(WebDriver driver) {
		this.driver=driver;
	}
	
	 public void Login() {	        
	      
	        driver.get("https://vega.test2.swisscom.ch/myswisscom");	     
	        driver.findElement(By.xpath("//*[@id='username']"));
	        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("abvcf");
	       
	    }
	

	
}
