package com.odcgroup.t24.localRef.deployment.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.t24.application.importer.internal.ApplicationImporter;
import com.odcgroup.t24.application.localrefapplication.importer.LocalRefApplicationImporter;
import com.odcgroup.t24.deployment.tests.T24DeploymentTest;
import com.odcgroup.t24.deployment.tests.T24ExternalTestConsole;
import com.odcgroup.t24.mdf.generator.T24MdfGenerator;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployBuilder;
import com.odcgroup.workbench.core.IOfsModelResource;


@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class T24LocalRefApplicationDeploymentTest extends T24DeploymentTest 
{
	private IPath importPath = new Path("/domain/");
	// @Inject private LocalRefImporter localRefImporter; 
	@Inject	private LocalRefApplicationImporter localRefApplicaitonimporter;
	private static final String LocalFieldsBusinessTypes = "domain/businessTypes/BusinessTypes.domain";
	private static final String LocalFieldsDefinition = "domain/localFieldDefinition/LocalFieldsDefinition.domain";
	private static final String LocalFieldsEnumeration = "domain/localFieldDefinition/LocalFieldsEnumeration.domain";
	@Inject private ApplicationImporter applicationimporter;

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
	}
   
   @After
	public void tearDown() throws Exception {
		deleteModelsProject("deployment-testTAFC-server");
		deleteModelsProject("deployment-testTAFJ-server");
		deleteModelsProject("default-models-gen");
		deleteModelsProjects();
	}
  
   @Test
 	public void localRefApplicaitonDeploymentTestToTAFC() throws Exception {
      	getTAFCExternalServer().setState(IDSServerStates.STATE_ACTIVE);
      	getTAFCExternalServer().addDsProject(new DSProject(getProject()));
 		getTAFCExternalServer().addDsProject(new DSProject(getGenProject()));
        String errorMessage = deployLocalREfApplicaiton(getTAFCExternalServer());
 		if (!errorMessage.isEmpty()) {
 			fail(errorMessage);
 		}
 	}
   
   private String deployLocalREfApplicaiton(ExternalT24Server server) throws Exception{
	   //import applications 
	   class ImportWorkspaceJob extends WorkspaceJob {
		   ExternalT24Server server;
			public ImportWorkspaceJob(ExternalT24Server server) {
				super("import localref Job");
				this.server=server;
			}
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				   importApplications(server);
				   localRefApplicaitonimporter.setServer(getTAFCExternalServer());
				   localRefApplicaitonimporter.setContainer(getProject().getFolder(importPath));
					List<LocalRefApplicationDetail> locaRefDetails = new ArrayList<LocalRefApplicationDetail>();
					try {
						locaRefDetails = localRefApplicaitonimporter.getFilteredModels();
					} catch (T24ServerException e) {
						fail("Cannot establish connection with the server " + e.getMessage());
					}
					localRefApplicaitonimporter.getSelectedModels().addAll((locaRefDetails));
					try {
						localRefApplicaitonimporter.importModels(new NullProgressMonitor());
					} catch (T24ServerException e) {
						fail("Import failied : " + e.getMessage());
					}
				return Status.OK_STATUS;
			}
			
	   }
	   ImportWorkspaceJob job = new ImportWorkspaceJob(server);
		job.setRule(getOfsProject().getProject());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int generatedXmlfiles = 0;
		//generate the LocalRef xmls
	   //Collection<MdfDomain> mdfDomains= DomainRepository.getInstance(getOfsProject()).getDomains();
		Collection<IOfsModelResource> modelResources = getOfsProject().getModelFolder("domain").getOfsPackage("localRefApplication").getModels();
		for (IOfsModelResource resource : modelResources) {
			Resource emfResource = getOfsProject().getModelResourceSet().getResource(resource.getURI(), true);
			emfResource.load(Collections.emptyMap());
			new T24MdfGenerator().generateXML(emfResource, null); // FIXME Remove "null" argument and provide valid ModelLoader class object.
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
		String errorMessage ="";
		Assert.assertFalse("Generated XMl Files :"+generatedXmlfiles +"deployment failure no files deployed ", infoList.isEmpty());
		if (errorList !=null && !errorList.isEmpty() && errorList.size()>9) {
			errorMessage = "Deployment Stauts in  "+ server.getName()+ "\n No of LocalRef Applicaiton Deployment failed :"+errorList.size()+"\n"+
					getDeploymentLogMessage(errorList)+"\n No of Local Ref Applicaiton Deployed sucessfully :"+infoList.size()+"\n"+getDeploymentLogMessage(infoList);
		}
		return errorMessage;
   }
   
   private void importApplications(ExternalT24Server server) {
		try {
			applicationimporter.setServer(server);
			applicationimporter.setContainer(getProject().getFolder(importPath));
			List<ApplicationDetail> details = new ArrayList<ApplicationDetail>();
			try {
				details = applicationimporter.getFilteredModels();
			} catch (T24ServerException e) {
				fail("Cannot establish connection with the server " + e.getMessage());
			}
			int nbModels = 500;
			if (nbModels < 0)
				nbModels = details.size();
			final int MAX = Math.min(nbModels, details.size());
			for (int kx = 0; kx < MAX; kx++) {
				applicationimporter.getSelectedModels().add(details.get(kx));
			}
			applicationimporter.importModels(new NullProgressMonitor());
		}catch(Exception e){
			
		}
   }
   
   
}
