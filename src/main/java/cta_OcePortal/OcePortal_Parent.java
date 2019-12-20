package cta_OcePortal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import cta_OcePortal.Login_Page;

public class OcePortal_Parent {
	
	public void Login() {
		 System.setProperty("webdriver.chrome.driver","C:\\Users\\animesh.kumar.sinha\\Downloads\\chromedriver_win32\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
		// System.setProperty("webdriver.chrome.driver","C:\\dev\\chromedriver.exe");
		Login_Page Login = new Login_Page(driver);
		Login.Login();
	}
	
	
}
