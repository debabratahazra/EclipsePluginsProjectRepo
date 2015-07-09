package com.odcgroup.visualrules.integration.tests.api;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IImageParameters;
import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.model.Parameter;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class ODYXXTests {
	private IProject ruleProject;
	private IDataModelIntegration dataModelIntegration;
	private IRuleIntegration ruleIntegration;

	@Before
	public void setUp() throws Exception {

		ruleProject = IntegrationCore.createRuleProject("ODYXXTests", null);
		dataModelIntegration = VisualRulesIntegration
				.getDataModelIntegration(ruleProject);
		ruleIntegration = VisualRulesIntegration
				.getRuleIntegration(ruleProject);
		ruleIntegration.createRuleModel(ruleProject, "rules_123", null);
	}

	@After
	public void tearDown() throws Exception {
		ruleProject.delete(true, null);

	}

	@Test
	public void testODY84() throws Exception {
		ruleIntegration.createRule("vrpath:/rules_123/TestRule",
				"Testing ODY-84", new Parameter[0], null, true);
		String ruleDesc = dataModelIntegration
				.getDescription("vrpath:/rules_123/TestRule");
		Assert.assertEquals("Testing ODY-84", ruleDesc);
	}

	@Test
	public void testODY85() throws Exception {
		ruleIntegration.createRule("vrpath:/rules_123/TestODY85",
				"Testing ODY-85", new Parameter[0], null, true);

		IImageParameters parameters = new IImageParameters() {
			public void setWidth(int widthInMilliMeters) {
				Assert.assertTrue(widthInMilliMeters > 0);
			}

			public void setHeight(int heightInMilliMeters) {
				Assert.assertTrue(heightInMilliMeters > 0);
			}

			public boolean isShowStatistics() {
				return false;
			}

			public boolean isShowNotes() {
				return true;
			}

			public boolean isShowErrorHandling() {
				return true;
			}

			public boolean isShowDescriptions() {
				return true;
			}

			public boolean isShowContents() {
				return true;
			}

			public Integer getMaxSliceHeight() {
				return 600;
			}

			public int getFormat() {
				return SWT.IMAGE_JPEG;
			}
		};
		String[] images = ruleIntegration.saveImageSlices(
				"vrpath:/rules_123/TestODY85", ruleProject.getLocation()
						.toFile(), parameters, null);
		Assert.assertTrue("At least one image must be created",
				images.length > 0);
		Assert.assertTrue("Image file extension must be 'jpg'",
				images[0].endsWith(".jpg"));
	}

	@Test
	public void testODY93() throws Exception {
		ruleProject.build(IncrementalProjectBuilder.FULL_BUILD, null);
		dataModelIntegration.validateModel("vrpath:/rules_123");
		Job job = new WorkspaceJob("ODY93 Testjob") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				IMarker marker = ruleProject.createMarker("MyMarkerType");
				marker.setAttribute(IMarker.MESSAGE, "Test message");

				return Status.OK_STATUS;
			}
		};
		job.setRule(ruleProject);
		job.schedule();
		job.join();
	}
}
