package cta_OcePortal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class TestDataProvider {

	public static  Queue<String> dataSource = new LinkedList<>();
	public static  Queue<String> dataSourcePass = new LinkedList<>();
	
	

	static {
		File reader = new File("C:\\Users\\animesh.kumar.sinha\\Desktop\\TestData.csv");
		try {
			Scanner inputStream = new Scanner(reader);
			inputStream.next();
			while (inputStream.hasNext()) {
				String data =inputStream.next();
				String[] values =data.split(",");				
				dataSource.add(values[0]);
				dataSourcePass.add(values[1]);
				System.out.println("I am here "+ dataSource.toString() );				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//dataSource.add("shyhzmn.udkkz79@flzhk.bnl");
		//dataSource.add("kuimvltg");
	//	dataSourcePass.add("tester11");
		//dataSourcePass.add("tester11");
	//	dataSourcePass.add("tester11");
	}

	public static String getUser() {
		return dataSource.poll();
	}
	public static  String getPass() {
		return dataSourcePass.poll();
	}

}

