package it.gssi.cs.mikado.standalone.launcher;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;

public class ModelsValidator {
	public static void main(String... args) throws Exception {
		ModelsValidator validator = new ModelsValidator();
		
		validator.validate("model/mykpi.flexmi.xmi", "model/aq.flexmi.xmi");
		
	}
	
	public void validate(String kpimodel, String scmodel) {
		
		String kpiMM = "metamodels/kpi.ecore";
		String scMM = "metamodels/smart_city.ecore";

		StringProperties modelProperties = StringProperties.Builder()
			.withProperty(EmfModel.PROPERTY_NAME, "Model")
			.withProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
				kpiMM
			)
			.withProperty(EmfModel.PROPERTY_MODEL_URI,
				kpimodel
			)
			.withProperty(EmfModel.PROPERTY_CACHED, true)
			.withProperty(EmfModel.PROPERTY_CONCURRENT, true)
			.build();
		
		StringProperties scProperties = StringProperties.Builder()
				.withProperty(EmfModel.PROPERTY_NAME, "sc")
				.withProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
					scMM
				)
				.withProperty(EmfModel.PROPERTY_MODEL_URI,
					scmodel
				)
				.withProperty(EmfModel.PROPERTY_CACHED, true)
				.withProperty(EmfModel.PROPERTY_CONCURRENT, true)
				.build();
		
		EvlRunConfiguration runConfig = EvlRunConfiguration.Builder()
			.withScript("evl/validate.evl")
			.withModel(new EmfModel(), modelProperties)
			.withModel(new EmfModel(), scProperties)
			//.withProfiling()
			.withResults()
			.withParallelism()
			.build();
		
		runConfig.run();
	}
}
