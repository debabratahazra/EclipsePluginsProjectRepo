package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Test class for VersionModelUtil
 *
 * @author vramya
 *
 */
public class VersionModelUtilTest extends AbstractDSUnitTest{
	@Before
	public void setUp() {
		createModelsProject();
		copyFromClasspathToModelsProject(getModelsNeededForTests());
		System.out.println("test");
	}
	
	private String[] getModelsNeededForTests() {
		return new String[] { "/version/AA/AA_Accounting/AA_PERIODIC_ATTRIBUTE,AA_MESSAGES.version",
				"/version/AA/AA_Accounting/AA_SIM_LIMIT,AA_SIMPLE.version",
				"/version/AA/AA_Interest/AA_ACCRUAL_FREQUENCY,AA_AUDIT.version",
				"/version/AA/AA_Interest/AA_ACCRUAL_FREQUENCY,AA.version",
				"/version/EB/EB_Foundation/AA_ACCOUNT_CLOSURE_DETAILS,INPUT.version",
				"/version/EB/EB_Reports/ENQUIRY_REPORT,AUDIT.version",
				"/version/EB/EB_Reports/ENQUIRY_REPORT,COMPOSITE.version" };
	}	
	
	@After
	public void tearDown() throws Exception {
		// Delete of models project was failing as few tasks were not completed,
		// hence code written with in runnable
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				deleteModelsProjects();
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
	
	@Test
	public void testLoadVersionsFromActiveProjects() {
		Map<String, IResource> versionMap = VersionModelUtil.loadVersionsFromActiveProjects();
		Assert.assertEquals("Nubmer of versions returned not equal.", 7, versionMap.size());
	}
}
