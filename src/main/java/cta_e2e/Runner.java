package cta_e2e;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
		features = "C:\\Selenium_Codes\\Selenium\\src\\main\\java\\cta_Features",
		
		glue= {
			"cta_e2e"
		}
		
		
		)


public class Runner {

}
