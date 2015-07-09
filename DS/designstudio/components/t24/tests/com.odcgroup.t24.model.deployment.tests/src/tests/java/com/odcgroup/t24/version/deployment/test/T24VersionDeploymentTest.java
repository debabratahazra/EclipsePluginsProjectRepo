package com.odcgroup.t24.version.deployment.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Injector;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.deployment.tests.T24DeploymentTest;
import com.odcgroup.t24.deployment.tests.T24ExternalTestConsole;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployBuilder;
import com.odcgroup.t24.version.VersionDSLUiInjectorProvider;
import com.odcgroup.t24.version.importer.internal.VersionImporter;
import com.odcgroup.t24.version.xml.generator.VersionXMLGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;

@RunWith(XtextRunner.class)
@InjectWith(VersionDSLUiInjectorProvider.class)
public class T24VersionDeploymentTest extends T24DeploymentTest {
	
	private IPath applicationImportPath = new Path("/domain");
	private IPath versionImportPath = new Path("/version/version");
	
	@Inject private	VersionImporter versionImporter;
	@Inject private	ApplicationImporter applicationImporter;
	@Inject private Injector injector;
	
	@Before
	public void setUp() throws CoreException,IOException,T24ServerException {
		applicationImporter = new ApplicationImporter(injector);
		createModelsProject();
		createCodeGenModelProject(getProject());
		T24ServerUIExternalCore.getDefault().setDeployBuilderConsole(new T24ExternalTestConsole());
		mkdirs(getProject().getFolder(versionImportPath));
		mkdirs(getProject().getFolder(applicationImportPath));
		setAutobuild(false);
	}
  
   @After
	public void tearDown() throws Exception {
	   deleteModelsProject("deployment-testTAFC-server");
		deleteModelsProject("deployment-testTAFJ-server");
		deleteModelsProject("default-models-gen");
		deleteModelsProjects();
		T24ServerUIExternalCore.getDefault().getDeployBuilderConsole().clearConsole();
	}

   @Test
 	public void versionDeploymentTestToTAFC() throws Exception {
	    createAndSetupServerProject();
 	  	getTAFCExternalServer().addDsProject(new DSProject(getProject()));
		getTAFCExternalServer().addDsProject(new DSProject(getGenProject()));
		//import the applications
 	  	importApplications();
 	  	getTAFCExternalServer().setState(IDSServerStates.STATE_ACTIVE);
 	    String errorMessage = deployVesrionModels(getTAFCExternalServer());
 		if (!errorMessage.isEmpty()) {
 			fail(errorMessage);
 		}
     }

    @Test
	public void versionDeploymentTestToTAFJ() throws Exception {
    	createAndSetupServerProject();
    	getTAFJExternalServer().addDsProject(new DSProject(getProject()));
		getTAFJExternalServer().addDsProject(new DSProject(getGenProject()));
    	//import the applications
	  	importApplications();
	  	deployVesrionModels(getTAFJExternalServer());
        String errorMessage = deployVesrionModels(getTAFJExternalServer());
		if (!errorMessage.isEmpty()) {
			fail(errorMessage);
		}
	}
  
    
    private void importApplications(){
    	//import the applications
	  	applicationImporter.setServer(getTAFCExternalServer());
	  	applicationImporter.setContainer(getProject().getFolder(applicationImportPath));
		List<ApplicationDetail> applicationDetails = new ArrayList<ApplicationDetail>();
		try {
			applicationDetails = applicationImporter.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		applicationImporter.getSelectedModels().addAll((applicationDetails.subList(0, 200)));
		try {
			applicationImporter.importModels(new NullProgressMonitor());
		} catch (T24ServerException e) {
			fail("Import failied : " + e.getMessage());
		}
    }
    private String deployVesrionModels(ExternalT24Server server) throws Exception{
	       //import versions.
			versionImporter.setServer(server);
			versionImporter.setContainer(getProject().getFolder(versionImportPath));
			List<VersionDetail> versionDetails = new ArrayList<VersionDetail>();
			try {
				versionDetails = versionImporter.getFilteredModels();
			} catch (T24ServerException e) {
				fail("Cannot establish connection with the server " + e.getMessage());
			}
			versionImporter.getSelectedModels().addAll(versionDetails.subList(0, 100));
			
			try {
				versionImporter.importModels(new NullProgressMonitor());
			} catch (T24ServerException e) {
				fail("Import failied : " + e.getMessage());
			}
			
			//generate the versions xmls
			Collection<IOfsModelResource> modelResources = getOfsProject().getModelFolder("version")
					.getOfsPackage("version").getModels();
			int generatedVerisonXml =0;
			for (IOfsModelResource resource : modelResources) {
				Resource emfResource = getOfsProject().getModelResourceSet().getResource(resource.getURI(), true);
				emfResource.load(Collections.EMPTY_MAP);
				new VersionXMLGenerator().generateXML(emfResource, null); // FIXME Remove "null" argument and provide valid ModelLoader class object.
				generatedVerisonXml++;
			}
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					try {
						new T24DeployBuilder(getGenProject()).build(IncrementalProjectBuilder.FULL_BUILD, null,
								new NullProgressMonitor());
					} catch (Exception e) {
						
					}
				}
			});
			T24ExternalTestConsole deployConsole =(T24ExternalTestConsole) T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
			List<String> errorList =deployConsole.getErrorList();
			List<String> infoList = deployConsole.getInfoList();
			assertFalse("Generated XMl Files :"+generatedVerisonXml +"deployment failure no files deployed ", infoList.isEmpty());
			String errorMessage ="";
			if (errorList !=null && !errorList.isEmpty()&& errorList.size()>65) {
				errorMessage = "Deployment Stauts in  "+ server.getName()+ "\n No Of models Generated : "+generatedVerisonXml +"\n No of Versions Deployment failed :"+errorList.size()+"\n"+
						getDeploymentLogMessage(errorList)+"\n No of Versions Deployed sucessfully :"+infoList.size()+"\n"+getDeploymentLogMessage(infoList);
			}
			return errorMessage;
  }
}
