package cta_OcePortal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cta_OcePortal.Login_Page;

public class OcePortal_Parent {
	
	public void Login() throws Exception  {
		//ChromeOptions chromeOptions = new ChromeOptions(); 
		System.setProperty("webdriver.chrome.driver","C:\\Users\\animesh.kumar.sinha\\Downloads\\chromedriver_win32_79\\chromedriver.exe");
	
		//WebDriver driver = new ChromeDriver(chromeOptions); 
		 WebDriver driver = new ChromeDriver();
		// System.setProperty("webdriver.chrome.driver","C:\\dev\\chromedriver.exe");
		Login_Page Login = new Login_Page(driver);
		Login.Login();
	}
	
	
}




























/*chromeOptions.addArguments("--headless");
chromeOptions.addArguments("--disable-gpu");
chromeOptions.addArguments("--disable-extensions");
chromeOptions.setExperimentalOption("useAutomationExtension", false);		
chromeOptions.addArguments("--proxy-bypass-list=*");
chromeOptions.addArguments("--start-maximized");

//chromeOptions.addArguments("window-size=1366x768");
//chromeOptions.add_argument('window-size=1920x1080');*/