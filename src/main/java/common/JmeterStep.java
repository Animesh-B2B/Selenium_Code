package common;



import java.lang.reflect.Method;

import cucumber.runtime.model.CucumberFeature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;

//BINDED TO JUNIT SAMPLER
public class JmeterStep {
	
	private Method methodToExecute;
	
	private Step step;
	
	private ScenarioDefinition scenario;
	
	private CucumberFeature cucumberFeature;
	
	public JmeterStep() {
		super();
	}
	
	public JmeterStep(Method methodToExecute, Step step, ScenarioDefinition scenario, CucumberFeature cucumberFeature) {
		super();
		this.methodToExecute = methodToExecute;
		this.step = step;
		this.scenario = scenario;
		this.cucumberFeature = cucumberFeature;
	}

	public Method getMethodToExecute() {
		return methodToExecute;
	}

	public void setMethodToExecute(Method methodToExecute) {
		this.methodToExecute = methodToExecute;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public ScenarioDefinition getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioDefinition scenario) {
		this.scenario = scenario;
	}

	public CucumberFeature getCucumberFeature() {
		return cucumberFeature;
	}

	public void setCucumberFeature(CucumberFeature cucumberFeature) {
		this.cucumberFeature = cucumberFeature;
	}

}
