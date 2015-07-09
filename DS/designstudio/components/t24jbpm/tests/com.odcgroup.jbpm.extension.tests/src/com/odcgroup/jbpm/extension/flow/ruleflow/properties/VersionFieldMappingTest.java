package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author hdebabrata
 * Test case for - 
 * /jbpm-editor-extension/src/main/java/com/odcgroup/jbpm/extension/flow/ruleflow/properties/VersionFieldMappingDialog.java 
 * class file.
 */
public class VersionFieldMappingTest extends AbstractDSUnitTest{

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyFromClasspathToModelsProject(getModelsNeededForTests());
		waitForAutoBuild(); // this is required for full build before test case execute.
	}
	
	private String[] getModelsNeededForTests() {
		return new String[] { "/version/fieldmapping/AA_ARR_OFFICERS,AA_SIMPLE.version", "/version/fieldmapping/AA_ARR_PAYOFF,AA_NOINPUT.version",
				"/version/fieldmapping/AA_PROPERTY,AA_AUDIT.version", "/version/fieldmapping/AA_PROPERTY,AA.version", 
				"/version/fieldmapping/ACCOUNT,HOT_OPEN.version"};
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
	public void testGetSelectedVersionFields() throws Exception {
		Map<String, IResource> VERSION_MAP = VersionModelUtil.loadVersionsFromActiveProjects();
		HumanTaskCommentDialog.setVERSION_MAP(VERSION_MAP);
		List<String> totalFields = HumanTaskCommentDialog.getSelectedVersionFields("ACCOUNT,HOT_OPEN");
		assertNotNull(totalFields);
		assertFalse("Total fields from Version must not empty.", totalFields.isEmpty());
		assertEquals("Total fileds from Version not matched.", 32, totalFields.size());
	}
}
