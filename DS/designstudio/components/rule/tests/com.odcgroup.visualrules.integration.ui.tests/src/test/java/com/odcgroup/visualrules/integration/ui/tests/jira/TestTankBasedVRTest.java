package com.odcgroup.visualrules.integration.ui.tests.jira;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.datasync.DataTypeBuilder;
import com.odcgroup.visualrules.integration.generation.VisualRulesCodeGenerator;
import com.odcgroup.visualrules.integration.init.RulesInitializer;
import com.odcgroup.visualrules.integration.init.RulesIntegrationInitializer;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.ProjectNotManagedException;

public class TestTankBasedVRTest extends AbstractDSUIUnitTest {

	private static Logger logger = LoggerFactory.getLogger(TestTankBasedVRTest.class);
	private IOfsModelResource domainModel;

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("rule/rules/rules_484254097.rule", "rule/rules/rules_484254097.vrmodel",
				"rule/rules/rules_484254097/GeneralDomain.vrpackage",
				"rule/rules/rules_484254097/GeneralDomain/ClassWithLongAttributes.vrpackage",
				"rule/rules/rules_484254097/GeneralDomain/ClassWithLongAttributes/Completion.vrpackage",
				"rule/rules/rules_484254097/GeneralDomain/ClassWithLongAttributes/Completion/System.vrrule",
				"domain/general/Domain.domain");
			
		if(!GenerationCore.isTechnical(getProject())) GenerationCore.toggleNature(getProject());
		ProjectPreferences preferences = new ProjectPreferences(getProject(), GenerationCore.PLUGIN_ID);
		preferences.putBoolean(VisualRulesCodeGenerator.CARTRIDGE_ID, true);
		preferences.flush();
		(new RulesInitializer()).updateConfiguration(getProject(), null);
		(new CodeGenInitializer()).updateConfiguration(getProject(), null);
		(new OfsProjectInitializer()).updateConfiguration(getProject(), null);

		domainModel = OfsCore.getOfsProject(getProject()).getOfsModelResource(URI.createURI("resource:///general/Domain.domain"));
		final Set<MdfDomain> domains = Collections.singleton((MdfDomain) domainModel.getEMFModel().get(0));

		IDependencyManager depMgr = OfsCore.getDependencyManager();
		depMgr.resolveDependencies(getProject());
		
		DataTypeBuilder.syncDomainModels(getProject(), domains, true, new NullProgressMonitor());
		IntegrationCore.getDataModelIntegration(getProject()).save("vrpath:/rules_484254097");
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test @Ignore
	public void testDS3690() throws CoreException, IOException, ProjectNotManagedException, IntegrationException {
		// we have two assignments in the completion rule, which cannot fit into an integer and therefore there would be a validation error
		getProject().build(IncrementalProjectBuilder.FULL_BUILD, RulesInitializer.VR_VALIDATION_BUILDER_ID,  Collections.<String,String>emptyMap(), null);
		IMarker[] markers = getProject().findMarkers("de.visualrules.validation.validationmarker", true, IResource.DEPTH_INFINITE);
		
		for(IMarker marker : markers) {
			logger.error((String) marker.getAttribute(IMarker.MESSAGE));
		}
		
		//Assert.assertEquals(0, markers.length);
		
		//Assert.assertEquals(0, IntegrationCore.getDataModelIntegration(getProject()).validateModel("vrpath:/rules_484254097").size());

		VisualRulesCodeGenerator generator = new VisualRulesCodeGenerator();
		IFolder targetFolder = getProject().getFolder("generated");
		targetFolder.create(true, true, null);
		List<IStatus> nonOkStatus = new ArrayList<IStatus>();
		generator.doRun(getProject(), targetFolder, Collections.singleton(domainModel), nonOkStatus);
		ResourcesPlugin.getWorkspace().save(true, null);
		File systemRuleFile = new File(getProject().getLocation().toString() + "/generated/com/odcgroup/rules/internal/rules/generaldomain/classwithlongattributes/completion/system/System.java");
		String systemRuleFileContent = FileUtils.readFileToString(systemRuleFile);
		Assert.assertTrue(systemRuleFileContent.contains("setLongAttribute(10000000000L)"));
		Assert.assertTrue(systemRuleFileContent.contains("setLongObjectAttribute(new java.lang.Long(10000000000L))"));
	}
	
	/**
	 * DS-3879
	 * 
	 * @throws CoreException
	 * @throws IOException
	 * @throws ProjectNotManagedException
	 * @throws IntegrationException
	 */
	@Test
	public void testDS3879() throws CoreException, IOException, ProjectNotManagedException, IntegrationException {
		
		// we first delete the internal package and rebuild to have the error marker
		IntegrationCore.getDataModelIntegration(getProject()).deletePackage(RulesIntegrationPlugin.getVRBasePath(getProject()) + "/" + RulesIntegrationPlugin.DOMAIN_PKG_NAME);
		getProject().build(IncrementalProjectBuilder.FULL_BUILD, null);

		IStatus status = (new RulesIntegrationInitializer()).checkConfiguration(getProject());
		Assert.assertEquals(IStatus.ERROR, status.getSeverity());

		// the data type sync marker id is a subtype of generation marker
		String gen_marker_id = "com.odcgroup.workbench.generation.projectProblem";
		IMarker[] markers = getProject().findMarkers(gen_marker_id, true, IResource.DEPTH_ZERO);
		Assert.assertEquals(1, markers.length);			
		
		IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(markers[0]);
		Assert.assertEquals(1, resolutions.length);
		resolutions[0].run(markers[0]);
		
		status = (new RulesIntegrationInitializer()).checkConfiguration(getProject());
		Assert.assertEquals(IStatus.OK, status.getSeverity());
		
	}

}
