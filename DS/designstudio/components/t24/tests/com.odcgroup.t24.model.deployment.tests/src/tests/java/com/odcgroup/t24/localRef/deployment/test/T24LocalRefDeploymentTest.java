package com.odcgroup.t24.localRef.deployment.test;



import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Injector;
import com.odcgroup.domain.ui.internal.DomainActivator;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.deployment.tests.T24DeploymentTest;
import com.odcgroup.t24.deployment.tests.T24ExternalTestConsole;
import com.odcgroup.t24.mdf.generator.T24MdfGenerator;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployBuilder;

public class T24LocalRefDeploymentTest extends T24DeploymentTest {
	
	// private IPath localRefapplicationImportPath = new Path("/domain/");
	private static final String LocalFieldsBusinessTypes = "domain/businessTypes/BusinessTypes.domain";
	private static final String LocalFieldsDefinition = "domain/localFieldDefinition/LocalFieldsDefinition.domain";
	private static final String LocalFieldsEnumeration = "domain/localFieldDefinition/LocalFieldsEnumeration.domain";
	private static final String ST_CONFIG = "domain/localFieldDefinition/ST_Config.domain";
	// @Inject private LocalRefImporter localRefImporter;

	protected ApplicationImporter applicationimporter;

	@Before
	public void setUp() throws CoreException,IOException,T24ServerException {
		createModelsProject();
		createAndSetupServerProject();
		createCodeGenModelProject(getProject());
		setAutobuild(false);
		T24ServerUIExternalCore.getDefault().setDeployBuilderConsole(new T24ExternalTestConsole());
		copyResourceInModelsProject(LocalFieldsBusinessTypes);
		copyResourceInModelsProject(LocalFieldsDefinition);
		copyResourceInModelsProject(LocalFieldsEnumeration);
		copyResourceInModelsProject(ST_CONFIG);
		
		Injector injector = DomainActivator.getInstance().getInjector(DomainActivator.COM_ODCGROUP_DOMAIN_DOMAIN);
		applicationimporter = new ApplicationImporter(injector);
	}
  
   @After
	public void tearDown() throws Exception {
		deleteModelsProject("deployment-testTAFC-server");
		deleteModelsProject("deployment-testTAFJ-server");
		deleteModelsProject("default-models-gen");
		deleteModelsProjects();
	}
   
    @Ignore  // Ignore this test case now, and need to implement in UI Test.
    @Test
	public void localRefDeploymentTestToTAFC() throws Exception {
     	getTAFCExternalServer().setState(IDSServerStates.STATE_ACTIVE);
     	getTAFCExternalServer().addDsProject(new DSProject(getProject()));
		getTAFCExternalServer().addDsProject(new DSProject(getGenProject()));
        String errorMessage = deployLocalREf(getTAFCExternalServer());
		if (!errorMessage.isEmpty()) {
			fail(errorMessage);
		}
	}
    
    @Ignore
    @Test
    public void testLocalRefDeploymentInTAFJ() throws Exception {
    	getTAFJExternalServer().setState(IDSServerStates.STATE_ACTIVE);
    	getTAFJExternalServer().addDsProject(new DSProject(getProject()));
		getTAFJExternalServer().addDsProject(new DSProject(getGenProject()));
        String errorMessage = deployLocalREf(getTAFJExternalServer());
		if (!errorMessage.isEmpty()) {
			fail(errorMessage);
		}
    }
    
    private String deployLocalREf(ExternalT24Server server) throws Exception{
    	//import the applications issue with import .so copy the resource .
    	/*localRefImporter.setServer(getTAFCExternalServer());
    	localRefImporter.setContainer(getProject().getFolder(localRefapplicationImportPath));
		List<LocalRefDetail> locaRefDetails = new ArrayList<LocalRefDetail>();
		try {
			locaRefDetails = localRefImporter.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		localRefImporter.getSelectedModels().addAll((locaRefDetails));
		try {
			localRefImporter.importModels(new NullProgressMonitor());
		} catch (T24ServerException e) {
			fail("Import failied : " + e.getMessage());
		}
    	*/
    	
		//force load the domains.
	   Collection<MdfDomain> mdfDomains= DomainRepository.getInstance(getOfsProject()).getDomains();
	   for (MdfDomain domain : mdfDomains) {
		    Resource resource = getOfsProject().getModelResourceSet().getResource(((EObject)domain).eResource().getURI(),true);
			resource.getContents().get(0);
	   }
	    int generatedXmlfiles=0;
//	    Collection<MdfDomain> mdfDomains= DomainRepository.getInstance(getOfsProject()).getDomains();
		for (MdfDomain domain : mdfDomains) {
			 ((EObject)domain).eResource().getContents().get(0);
			 new T24MdfGenerator().generateXML(((EObject)domain).eResource(), null); // FIXME Remove "null" argument and provide valid ModelLoader class object.
			 generatedXmlfiles++;
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
		Assert.assertFalse("Generated XMl Files :"+ generatedXmlfiles+"deployment failure no files deployed ", infoList.isEmpty());
		String errorMessage ="";
		if (errorList !=null && !errorList.isEmpty()) {
			errorMessage = "Deployment Stauts in  "+ server.getName()+ "\n No of LocalRef Deployment failed :"+errorList.size()+"\n"+
					getDeploymentLogMessage(errorList)+"\n No of LocalRef Deployed sucessfully :"+infoList.size()+"\n"+getDeploymentLogMessage(infoList);
		}
		return errorMessage;
    }
/*    
    private void importApplications(ExternalT24Server server) {
		try {
			applicationimporter.setServer(server);
			applicationimporter.setContainer(getProject().getFolder(localRefapplicationImportPath));
			List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
			try {
				details = applicationimporter.getFilteredModels();
			} catch (T24ServerException e) {
				fail("Cannot establish connection with the server " + e.getMessage());
			}
			for (int kx = 0; kx < details.size(); kx++) {
				if(details.get(kx).getComponent().equals("ST_Config")){
				   applicationimporter.getSelectedModels().add(details.get(kx));
				}
			}
			applicationimporter.importModels(new NullProgressMonitor());
		}catch(Exception e){
			
		}
    }
*/ 
}
