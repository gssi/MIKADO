package it.gssi.cs.mikado.standalone.launcher;

import java.io.Console;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;

/**
 * This example demonstrates using the 
 * Epsilon Transformation Language, the M2M language
 * of Epsilon, in a stand-alone manner 
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class EvaluationStandaloneLauncher {
	
	public static void main(String[] args) throws Exception {
		
		
		
		String kpiMM = "metamodels/kpi.ecore";
		String scMM = "metamodels/smart_city.ecore";

		StringProperties sourceProperties = new StringProperties();
		//kpi metamodel
		sourceProperties.setProperty(EmfModel.PROPERTY_NAME, "Source");
		sourceProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, kpiMM);
		
		// sc metamodel
		StringProperties sourceScProperties = new StringProperties();
		sourceScProperties.setProperty(EmfModel.PROPERTY_NAME, "sc");
		sourceScProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, scMM);
		
		//Scanner sc= new Scanner(System.in);
		//System.out.println("Enter the kpi model relative path");
		
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
				"model/newkpi.flexmi.xmi"
			//sc.nextLine()
		);
		sourceProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		
		//sc= new Scanner(System.in);
		//System.out.println("Enter the smart city model relative path");
		
		
		sourceScProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			//	sc.nextLine()
				"model/aq.flexmi.xmi"
			);
		sourceScProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "Target");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, kpiMM);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
				"model/evaluated.model"
		);
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "false");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "true");
		EmfModel target = new EmfModel();
	
		EtlRunConfiguration runConfig = EtlRunConfiguration.Builder()
			.withScript("etl/kpi2eval.etl")
			.withModel(new EmfModel(), sourceProperties)
			.withModel(new EmfModel(), sourceScProperties)
			.withModel(target, targetProperties)
			.build();
		
	
		runConfig.run();
			
		runConfig.dispose();
		target.dispose();
		
		
		DashboardGenerator gen = new DashboardGenerator();
		gen.genDashboard(target.getModelFile());
		
		
	}
}
