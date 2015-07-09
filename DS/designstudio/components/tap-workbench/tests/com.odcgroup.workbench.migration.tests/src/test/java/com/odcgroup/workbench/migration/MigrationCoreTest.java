package com.odcgroup.workbench.migration;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.tap.validation.internal.ValidationInitializer;

public class MigrationCoreTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		(new ValidationInitializer()).updateConfiguration(getProject(), null);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testNeedsMigration_DS3108() throws CoreException, InvalidMetamodelVersionException, UnsupportedEncodingException {
		IFolder modelPackage = getProject().getFolder("module/general");
		OfsCore.createFolder(modelPackage);
		String outDatedModelContent = "# UTF-8\n" +
				"Domain MyDomain\n" + 
				"namespace \"http://www.odcgroup.com/domain\"\n" + 
				"metamodelVersion 4.1.0.20110527\n";
		IFile domainInWrongFolder = modelPackage.getFile("Domain.domain");
		domainInWrongFolder.create(new ByteArrayInputStream(outDatedModelContent.getBytes("UTF-8")), true, null);
		Assert.assertFalse(MigrationCore.needsMigration(getOfsProject()));
		domainInWrongFolder.delete(true, null);
	}
	@Ignore
	@Test
	public void testRemoveReadOnlyFlag_DS3865() throws CoreException, InvalidMetamodelVersionException, MigrationException, UnsupportedEncodingException {
		IFolder modelPackage = getProject().getFolder("domain/general");
		OfsCore.createFolder(modelPackage);
		String outDatedModelContent = "# UTF-8\n" +
				"Domain MyDomain\n" + 
				"namespace \"http://www.odcgroup.com/domain\"\n" + 
				"metamodelVersion 4.1.0.20110527\n"; 
		IFile domainfile = modelPackage.getFile("Domain.domain");
		domainfile.create(new ByteArrayInputStream(outDatedModelContent.getBytes("UTF-8")), true,	null);
		ResourceAttributes attributes = domainfile.getResourceAttributes();
		attributes.setReadOnly(true);
		domainfile.setResourceAttributes(attributes);
		MigrationCore.migrate(getOfsProject(), null);
		Assert.assertFalse(domainfile.getResourceAttributes().isReadOnly());
		domainfile.delete(true, null);
		waitForAutoBuild();
	}

	// deactivated as this approach didn't work for domain DSL files anymore
	public void xtestValidation_DS3228() throws CoreException, MigrationException, ModelNotFoundException, OperationCanceledException, InterruptedException {
		IFolder modelPackage = getProject().getFolder("domain/general");
		OfsCore.createFolder(modelPackage);
		final IFile domainFile = modelPackage.getFile("Domain.mml");
		final IFile dslDomainFile = modelPackage.getFile("Domain.domain");

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				String outDatedModelContent = "<mml:domain name=\"GeneralDomain\" " +
				"namespace=\"http://www.odcgroup.com/generaldomain\" version=\"1.0\" " +
				"metamodelVersion=\"1.0.0\" />";

				// DS-2230: Migrate all outdated resources of this project
				try {
					domainFile.create(new ByteArrayInputStream(outDatedModelContent.getBytes("UTF-8")), true,	null);
					MigrationCore.migrate(getOfsProject(), null);
				} catch (MigrationException e) {
					throw new CoreException(new Status(IStatus.ERROR, MigrationCore.PLUGIN_ID, "Error during model migration", e));
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(IStatus.ERROR, MigrationCore.PLUGIN_ID, "Unable to create an input stream of the string", e));
				}							
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, getProject(), IWorkspace.AVOID_UPDATE, null);
		int maxSeverity = dslDomainFile.findMaxProblemSeverity(IMarker.MARKER, true, IResource.DEPTH_INFINITE);
		Assert.assertEquals(IMarker.SEVERITY_ERROR, maxSeverity);
		
		domainFile.delete(true, null);
	}
	
	// Unit test "disabled" because it randomly failed on Bamboo
	public void xtestMmlFileMigration_DS5295() throws CoreException, InvalidMetamodelVersionException, UnsupportedEncodingException {
		IFolder modelPackage = getProject().getFolder("domain");
		OfsCore.createFolder(modelPackage);
		String outDatedModelContent = "<mml:domain name=\"GeneralDomain\" " +
			"namespace=\"http://www.odcgroup.com/generaldomain\" version=\"1.0\" " +
			"metamodelVersion=\"0.1.0\" />";
		IFile domain = modelPackage.getFile("Domain.mml");
		domain.create(new ByteArrayInputStream(outDatedModelContent.getBytes("UTF-8")), true,	null);
		getOfsProject().refresh();
		Assert.assertTrue(MigrationCore.needsMigration(getOfsProject()));
		domain.delete(true, null);
	}
}
