/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package it.gssi.cs.mikado.standalone.launcher;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.egl.launch.EgxRunConfiguration;

/**
 * This example demonstrates using the Epsilon Generation Language, the M2T language of Epsilon, in a stand-alone manner 
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class DashboardGenerator {
	
	public static void main(String[] args) throws Exception {
		
		
		StringProperties modelProperties = new StringProperties();
		modelProperties.setProperty(EmfModel.PROPERTY_NAME, "Model");
		modelProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
			"metamodels/kpi.ecore"
		);
		modelProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			"model/evaluated.model"
		);
		modelProperties.setProperty(EmfModel.PROPERTY_CACHED, "true");
		modelProperties.setProperty(EmfModel.PROPERTY_CONCURRENT, "true");
		
		EgxRunConfiguration runConfig = EgxRunConfiguration.Builder()
			.withScript("visualization/picto/kpimodel2picto.egx")
			.withModel(new EmfModel(), modelProperties)
			.withParameter("eglTemplateFileName", "kpimodel2picto.egl")
			.withOutputRoot(new File("model/gen").getAbsolutePath())
			.withProfiling()
			.build();
		
		runConfig.run();
	}

}
