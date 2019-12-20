package common;


import java.util.List;

import gherkin.ast.ScenarioDefinition;

//BINDED TO THREAD GROUP
public class JmeterScenario {

	private ScenarioDefinition scenario;
	
	private List<JmeterStep> steps;

	public JmeterScenario() {
		super();
	}

	public JmeterScenario(ScenarioDefinition scenario, List<JmeterStep> steps) {
		super();
		this.scenario = scenario;
		this.steps = steps;
	}

	public ScenarioDefinition getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioDefinition scenario) {
		this.scenario = scenario;
	}

	public List<JmeterStep> getSteps() {
		return steps;
	}

	public void setSteps(List<JmeterStep> steps) {
		this.steps = steps;
	}
	
	

}

