package com.odcgroup.t24.enquiry.deployment.test;



import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.t24.deployment.tests.T24DeploymentTest;
import com.odcgroup.t24.deployment.tests.T24ExternalTestConsole;
import com.odcgroup.t24.enquiry.importer.internal.EnquiryImporter;
import com.odcgroup.t24.enquiry.xml.generator.EnquiryXMLGenerator;
import com.odcgroup.t24.server.external.builder.delta.AnalysedDelta;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.t24.server.external.ui.builder.T24DeployBuilder;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;


@RunWith(XtextRunner.class)
@InjectWith(EnquiryInjectorProvider.class)
@Ignore
public class T24EnquiryDeploymentTest extends T24DeploymentTest {

	private IPath importPath = new Path("/enquiry/enquiry");
	@Inject
	private EnquiryImporter importer;

	private boolean isTafj = false;
	// ignoring below enquiries due to below errors
	private String[] invalidEnquiryNames = new String[] {
			// DS-8655: tafj deployment failed due to errors - "INVALID FIELD NAME","No-Input if EXPOSE is not set",
			// "INVALID OPERATOR","Column width for AVAILABLE LIMIT not the same as width for F.AVAIL.LIMIT = 125", 
			// "TOO MANY CHARACTERS", "INPUT MISSING", "INVALID SELECTION FIELD", "INVALID DISPLAY COORDS"

			"%SEAT.SCRIPT.ACTIVITY","ADMIN.BL.GROUP.CONDITION","ADMIN.BL.TXN.TYPE.CONDITION","EB.CONTRACT.BALANCES.BALANCE","PVT.LIMIT.OVERDRAWN",
			"AA.LOAN.REPORT.WS.enquiry.xml","AA.OVERVIEW-SUBHEADING.ADDITIONAL.AD","AA.OVERVIEW-SUBHEADING.ADDITIONAL.AL", 
			"AA.OVERVIEW-SUBHEADING.TRANSACTIONS.SIM.AR","AC.ACCOUNT.FIN.SUMMARY.SCV","ACCT.FWD.BALS.TAB","AP.PORTFOLIO.PERF.D.1M",
			"AP.PORTFOLIO.PERF.M","ARC.MO.PRD.AC.LIST","BV.PERF.REP","COMPARE.DIFFERENCES","DE.TRANSLATION.MTN96",
			"ERROR.REPORT","EXCEPTIONS","EXP.LCSUMMARY","LC.SUMMARY","OBTAIN.TASK.INFO","POS.STMT.ENT",
			"PWM.PORT.BUBBLE.CHART","REIMB.LC.SUMMARY","SYS.POS.ACCOUNT.SUMMARY", };
	
	@Before
	public void setUp() throws CoreException, IOException, T24ServerException {
		createModelsProject();
		createCodeGenModelProject(getProject());
		mkdirs(getProject().getFolder(importPath));
		setAutobuild(false);
		T24ServerUIExternalCore.getDefault().setDeployBuilderConsole(new T24ExternalTestConsole());
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProject("deployment-testTAFC-server");
		deleteModelsProject("deployment-testTAFJ-server");
		deleteModelsProject("default-models-gen");
		deleteModelsProjects();
		T24ServerUIExternalCore.getDefault().getDeployBuilderConsole().clearConsole();
		
	}
	@Ignore
	@Test 
	public void enquiryDeploymentTestOnTAFC() throws Exception {
		createAndSetupServerProject();
		getTAFCExternalServer().setState(IDSServerStates.STATE_ACTIVE);
		getTAFCExternalServer().addDsProject(new DSProject(getProject()));
		getTAFCExternalServer().addDsProject(new DSProject(getGenProject()));
		String errorMessage = deployModels(getTAFCExternalServer());
		if (!errorMessage.isEmpty()) {
			fail(errorMessage);
		}

	}

	@Test
	public void enquiryDeploymentTestOnTAFJ() throws Exception {
		createAndSetupServerProject();
		getTAFJExternalServer().setState(IDSServerStates.STATE_ACTIVE);
		getTAFJExternalServer().addDsProject(new DSProject(getProject()));
		getTAFJExternalServer().addDsProject(new DSProject(getGenProject()));
		isTafj = true;
		String errorMessage = deployModels(getTAFJExternalServer());
		if (!errorMessage.isEmpty()) {
			fail(errorMessage);
		}

	}
	public String deployModels(ExternalT24Server server) throws Exception{
		importer.setServer(server);
		importer.setContainer(getProject().getFolder(importPath));
		List<EnquiryDetail> details = new ArrayList<EnquiryDetail>();
		try {
			details = importer.getFilteredModels();
		} catch (T24ServerException e) {
			fail("Cannot establish connection with the server " + e.getMessage());
		}
		if(isTafj) {
			final int MAX = Math.min(100, details.size());
			for (int kx=0; kx < MAX; kx++ ) {
				EnquiryDetail enquiry = details.get(kx);
				List<String> invalidList = Arrays.asList(invalidEnquiryNames);
				if(valid(invalidList,enquiry)){
					importer.getSelectedModels().add(enquiry);
				}
			}
		} else {
			importer.getSelectedModels().addAll(details.subList(0, 100));
		}
		try {
			importer.importModels(new NullProgressMonitor());
		} catch (T24ServerException e) {
			fail("Import failied : " + e.getMessage());
		}
		Collection<IOfsModelResource> modelResources = getOfsProject().getModelResourceSet().getAllOfsModelResources(
				new String[] { "enquiry" }, IOfsProject.SCOPE_PROJECT);

		int generatedXmlfiles = 0;
		for (IOfsModelResource resource : modelResources) {
			Resource emfResource = getOfsProject().getModelResourceSet().getResource(resource.getURI(), true);
			emfResource.setURI(convertToStandardURI(getOfsProject(),resource.getURI()));
			emfResource.load(Collections.EMPTY_MAP);
			new EnquiryXMLGenerator().generateXML(emfResource, null); // FIXME Remove "null" argument and provide valid ModelLoader class object.
			generatedXmlfiles++;
		}
		
		
		final AnalysedDelta analysedDelta = buildFileList();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					new T24DeployBuilder(getGenProject()) {
						@Override
						protected AnalysedDelta analyseDelta(IResourceDelta delta, IProgressMonitor monitor)
								throws CoreException {
							return analysedDelta;
						}
					}.incrementalBuild(null, new NullProgressMonitor());
				} catch (Exception e) {
					
				}
			}
		});

		T24ExternalTestConsole deployConsole =(T24ExternalTestConsole) T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
		List<String> errorList =deployConsole.getErrorList();
		List<String> infoList = deployConsole.getInfoList();
		Assert.assertFalse("Generated XMl Files :"+generatedXmlfiles +"deployment failure no files deployed ", infoList.isEmpty());
		String errorMessage ="";
		if (errorList !=null && !errorList.isEmpty() && errorList.size()>20) {
			errorMessage = "Deployment Stauts in  "+ server.getName()+ "\n No Of models Generated : "+generatedXmlfiles +"\n No of Enquiry Deployment failed : "+errorList.size()+
					getDeploymentLogMessage(errorList) +" \n Sucessfully Deployed Enquiires : \n "+ infoList.size()+"\n"+getDeploymentLogMessage(infoList);
		}
		return errorMessage;
	}

	private boolean valid(List<String> invalidList, EnquiryDetail enquiry) {
		if( invalidList.contains(enquiry.getName())){
			return false;
		}
		return true;
	}
	
	/**
	 * @return
	 */
	private AnalysedDelta buildFileList() {
		AnalysedDelta analysedDelta = new AnalysedDelta(getGenProject().getLocation().toPortableString());
		buildFileList(analysedDelta, new File(getGenProject().getLocation().toPortableString()));
		return analysedDelta;
	}

	/**
	 * Recursive algorithm which scan all files and folders
	 */
	private void buildFileList(AnalysedDelta analysedDelta, File file) {
		String fileName = file.getPath().replace(File.separatorChar, '/');

		String projectFullPath = getGenProject().getLocation().toPortableString();
		if (fileName.length() >= projectFullPath.length()+1) {
			fileName = fileName.substring(projectFullPath.length()+1);
			if (T24DeployBuilder.isT24XmlFile(fileName)) {
				analysedDelta.addedFile(fileName);
			}
		}

		// Recursively scan the sub folders
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				buildFileList(analysedDelta, child);
			}
		}
	}

}
