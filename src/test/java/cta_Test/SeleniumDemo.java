
package cta_Test;


import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Injector;

import cta_OcePortal.OcePortal_Parent;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;
import guice_Dependency.CurrentTextContext;

@ScenarioScoped
public class SeleniumDemo {
	

@Inject
private OcePortal_Parent OcePortal_Parent;
 //   private OcePortal_Parent ocePortal_Parent;
    @Test
	@Given("^Existing Customer logs into Swisscom dashboard$")
	
	public void LaunchUrl() {	
    	forceInjection(CurrentTextContext.getContext());
	//OcePortal_Parent Parent = new OcePortal_Parent();
		OcePortal_Parent.Login();
	
	}
@Test	
@Then("^Portal displays customer's dashboard$")
	
	public void Validation() {
	forceInjection(CurrentTextContext.getContext());
		System.out.println("Validation");
	}
private void forceInjection(Injector context) {
	context.injectMembers(this);
}

}



