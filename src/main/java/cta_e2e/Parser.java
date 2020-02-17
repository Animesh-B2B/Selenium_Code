package cta_e2e;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;
import org.junit.runner.RunWith;

import common.JmeterScenario;
import common.JmeterStep;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.FeaturePathFeatureSupplier;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.java.StepDefAnnotation;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.FeatureLoader;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;


/*@CucumberOptions(
	    features = "C:\\Users\\animesh.kumar.sinha\\eclipse-workspace\\Selenium\\src\\test\\java\\cta_Features",

	    plugin = {
	            "pretty"	            
	    },
	    glue = {
	    		"cta_e2e"
	    }
	)
@RunWith(Cucumber.class)*/

public class Parser {
	
	
private final static String fileName = "C:\\Selenium_Codes\\Selenium\\resource\\LoopExample.jmx";

    
	public static void main(String[] args) throws DocumentException, IOException {
		 List<JmeterScenario> scenariosToExecute = generateJmeterScenarios();
		 generateJmxFile(scenariosToExecute);
		
	}
	
	
	//
	private static  List<JmeterScenario> generateJmeterScenarios() {
	
		Class<Parser> clazz = Parser.class;
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
		RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();
		System.out.println("Runtime printed is: " +runtimeOptions);
		ClassLoader classLoader = clazz.getClassLoader();
		ResourceLoader resourceLoader = new MultiLoader(classLoader);
		System.out.println("resourceLoader printed is: "+resourceLoader);
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		Map<String, Method> methodsAnnoted = scanMethodsForCucumberAnnotations(runtimeOptions, classFinder);
		FeatureLoader featureLoader = new FeatureLoader(resourceLoader);
		FeaturePathFeatureSupplier featureSupplier = new FeaturePathFeatureSupplier(featureLoader, runtimeOptions);
		List<CucumberFeature> features = featureSupplier.get();
		System.out.println("the feature is: "+features);
		
		 //INIZIALIZATION
        int foundMatchCount = 0;
        int notFoundMatchCount = 0;
        List<JmeterScenario> scenariosToExecute = new ArrayList<>();
        
       for (CucumberFeature cucumberFeature : features) {
			List<ScenarioDefinition> childrens = cucumberFeature.getGherkinFeature().getFeature().getChildren();
			System.out.println("---------------------Feature File---------------------"+ cucumberFeature.getUri());
			
			for (ScenarioDefinition scenario : childrens) {
				JmeterScenario jmeterScenario = new JmeterScenario();
				List<JmeterStep> jmeterSteps = new ArrayList<>();
				jmeterScenario.setScenario(scenario);
				List<Step> steps = scenario.getSteps();
				System.out.println("---------------------SCENARIO---------------------"+scenario.getName());
				
				for (Step step : steps) {
					System.out.println("Step "+ step.getText());
					Set<String> annotationTextKeyset = methodsAnnoted.keySet();
					System.out.println(annotationTextKeyset);
					boolean foundMatch = false;
					for (String annotationText : annotationTextKeyset) {
						if(annotationText.contains(step.getText())) {
							foundMatch = true;
							foundMatchCount++;
							System.out.println("FOUND MATCH Step : " + step.getText() + " ----> METHOD :"+methodsAnnoted.get(annotationText) );
							JmeterStep jmeterFeature = new JmeterStep(methodsAnnoted.get(annotationText), step, scenario,  cucumberFeature);
							jmeterSteps.add(jmeterFeature);
						}
					}	
					
					if(!foundMatch) {
						notFoundMatchCount++;
						System.out.println("MATCH NOT FOUND : "+ step.getText());
					}
				}
				jmeterScenario.setSteps(jmeterSteps);
				scenariosToExecute.add(jmeterScenario);
				System.out.println("---------------------END SCENARIO---------------------");
				
			}
			
			System.out.println("---------------------Feature File---------------------");
        }
       
       System.out.println("Match found "+ foundMatchCount+ " Match not found "+notFoundMatchCount);
       return scenariosToExecute;		
		
	}
	
	
	private static Map<String, Method> scanMethodsForCucumberAnnotations(RuntimeOptions runtimeoptions,
			ClassFinder classFinder) {
		List<URI> gluePaths = runtimeoptions.getGlue();
		//System.out.println("I am here"+gluePaths);
		Map<String, Method> methodsAnnoted = new HashMap<>();
		for (URI gluePath : gluePaths) {
            for (Class<?> glueCodeClass : classFinder.getDescendants(Object.class, gluePath)) {
            	 for (Method method : glueCodeClass.getMethods()) {
            		   for (Annotation annotation : method.getAnnotations()) {
            			   Class<? extends Annotation> annotationClass = annotation.annotationType();
            		        if(annotationClass.getAnnotation(StepDefAnnotation.class) != null) {
            		        	System.out.println("Found annotation "+annotation.toString() + " for method "+ method );
            		        	methodsAnnoted.put(annotation.toString(), method);
            		        	//System.out.println("found method : for gluepath : "+ gluePath);
            		        }
            		   }
                 }
            }
        }
		return methodsAnnoted;
		
	}
	
	private static void manageThreadGroupUpdate(Element threadGroupContainer, JmeterScenario jmeterScenario, Node testPlanSubNode) {
	
		Element threadGroupCloned = (Element)testPlanSubNode.clone();
		threadGroupCloned.addAttribute("testname", jmeterScenario.getScenario().getName());
		List<Node> threadGroupSubNodes = threadGroupCloned.selectNodes("*");
		
		for (Node threadGroupSubNode : threadGroupSubNodes) {
			Element threadGroupSubNodeCasted = (Element)threadGroupSubNode;
			
			if(threadGroupSubNode.getName().equals("elementProp") && "ThreadGroup.main_controller".equals(threadGroupSubNodeCasted.attributeValue("name"))){
				manageLooCount(threadGroupSubNode);
			}
			
			else if(threadGroupSubNodeCasted.getName().equals("stringProp") && "ThreadGroup.num_threads".equals(threadGroupSubNodeCasted.attributeValue("name"))) {
				threadGroupSubNodeCasted.clearContent();
				// THREAD NUM PARAMETERS
				String threadCount = System.getenv("THREAD_NUM");
				System.out.println("Thred count retrieved "+threadCount);
				threadGroupSubNodeCasted.add(new DefaultText(threadCount));
			}
			
			else if(threadGroupSubNodeCasted.getName().equals("stringProp") && "ThreadGroup.ramp_time".equals(threadGroupSubNodeCasted.attributeValue("name"))) {
				threadGroupSubNodeCasted.clearContent();
				// THREAD RAMP-TIME PARAMETERS
				threadGroupSubNodeCasted.add(new DefaultText("15"));
			}
		}
		
		threadGroupContainer.add(threadGroupCloned);
	}
	
	private static void manageLooCount(Node threadGroupSubNode) {
		List<Node> loopControllerSubnodes = threadGroupSubNode.selectNodes("*");
		for (Node loopControllerSubnode : loopControllerSubnodes) {
			Element loopControllerSubnodeCasted = (Element)loopControllerSubnode;
			
			if("LoopController.loops".equals(loopControllerSubnodeCasted.attributeValue("name"))) {
				loopControllerSubnodeCasted.clearContent();
				// LOOP COUNT PARAMETER
				String loopCount = System.getenv("LOOP_COUNT");
				System.out.println("Loop count retrieved "+loopCount);

				loopControllerSubnodeCasted.add(new DefaultText(loopCount));
			}
		}
		
	}
	
	private static DefaultElement generateJunitSampler(JmeterStep jmeterStep) {
		
		DefaultElement junitSampler = new DefaultElement("JUnitSampler");
		junitSampler.addAttribute("guiclass", "JUnitTestSamplerGui")
		.addAttribute("testclass","JUnitSampler")
		.addAttribute("testname", jmeterStep.getStep().getText().substring(0,10));
		
		junitSampler
		.addElement("stringProp")
		.addAttribute("name", "junitSampler.classname")
		.add(new DefaultText(jmeterStep.getMethodToExecute().getDeclaringClass().getName()));
		
		junitSampler
		.addElement("stringProp")
		.addAttribute("name", "junitsampler.constructorstring");
		
		junitSampler
		.addElement("stringProp")
		.addAttribute("name", "junitsampler.method")
		.add(new DefaultText(jmeterStep.getMethodToExecute().getName()));
		
		junitSampler
		.addElement("stringProp")
		.addAttribute("name", "junitsampler.pkg.filter")
		.add(new DefaultText(""));
		
		junitSampler
		.addElement("stringProp")
		.addAttribute("name", "junitsampler.success")
		.add(new DefaultText("successful"));
		
		
		return junitSampler;
	}
	
	
	 public static void generateJmxFile(List<JmeterScenario> scenariosToExecute) throws DocumentException, IOException {
	    	SAXReader reader = new SAXReader();
	    	//System.out.println("I am here");
	    	Document document = reader.read(new File(fileName));
	    	//System.out.println("I am here1");
	        List<Node> elements = document.selectNodes("//*[name() = 'ThreadGroup']");
	        
	      //SHOULD BE ONE FOR NOW
	        for (Node node : elements) {
	        	//HASHTREE CONTAINING  THREAD GROUPS
	        	Element threadGroupContainer = (Element) node.getParent();
	        	//TEMPLATE NODES (THREAD GROUP + HASHTREE WITH THE LISTENER WITHOU JUNIT SAMPLER
	        	List<Node> allNodes = threadGroupContainer.selectNodes("*");
	        	//CLEANING CONTENT BEFORE REGENERATING 
	        	threadGroupContainer.clearContent();
	        	
	        	for(JmeterScenario jmeterScenario : scenariosToExecute) {
	        		
	        		for (Node testPlanSubNode : allNodes) {
	        			
	        			if(testPlanSubNode.getName().equals("hashTree")) {
	        				
	        				//USE A CLONE TO DON'T MODIFY TEMPLATES
							Element junitContainerCloned = (Element) testPlanSubNode.clone();
							List<Element> junitContainerClonedNodeList = junitContainerCloned.elements();
							int indexJunitContainerElements = 0;
							
							for(JmeterStep jmeterStep : jmeterScenario.getSteps()) {
								
								DefaultElement junitSampler = generateJunitSampler(jmeterStep);
								junitContainerClonedNodeList.add(indexJunitContainerElements, junitSampler);
								indexJunitContainerElements++;
								DefaultElement hashTree = new DefaultElement("hashTree");
								junitContainerClonedNodeList.add(indexJunitContainerElements, hashTree);
								indexJunitContainerElements++;
							}
							threadGroupContainer.add(junitContainerCloned);
	        			}
	        			else if(testPlanSubNode.getName().equals("ThreadGroup")){
							manageThreadGroupUpdate(threadGroupContainer, jmeterScenario, testPlanSubNode);
						}
	        		}
	        	}
	        	
	        }
	        
	        XMLWriter writer = new XMLWriter(new FileWriter(new File("C:\\Selenium_Codes\\Selenium\\resource\\LoopExample_modified.jmx")));
	        writer.write(document);
	        writer.close();
	 }
	

}
