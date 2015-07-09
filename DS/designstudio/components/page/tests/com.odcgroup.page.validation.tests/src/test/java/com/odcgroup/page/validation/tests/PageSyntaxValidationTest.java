package com.odcgroup.page.validation.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.tap.validation.ValidationMarkerUtil;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

/**
 * Tests if we have validation errors if there are syntax errors in DSL files
 *
 * @author Kai Kreuzer
 */
public class PageSyntaxValidationTest extends AbstractDSUnitTest {
	
	@Before
	public void setUp() {
		createModelsProject();		
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS3646() throws CoreException, ModelNotFoundException {
		
		final IFile file = getModuleFolder().getFolder("Default").getFolder("module").getFile("broken.module");

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				String currentMetaModelVersion = OfsCore.getCurrentMetaModelVersion("module");
				String string = "metamodelVersion = "+currentMetaModelVersion+"\n"+
						"This is a totally broken file!";
				InputStream is;
				try {
					is = new ByteArrayInputStream(string.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(Status.ERROR, "", "Unable to get the stream of the string"));
				}
				file.create(is, true, null);
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
		getOfsProject().refresh();
		
		URI uri = URI.createURI("resource:///Default/module/broken.module");
		checkResourceAvailability(uri, false);
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(uri);
		IStatus status = ValidationUtil.validate(modelResource, null, true);
		// do we get erros back?
		Assert.assertEquals(IStatus.ERROR, status.getSeverity());
		
		// do we also have error markers?
		int severity = file.findMaxProblemSeverity(ValidationMarkerUtil.MARKER_ID, true, IResource.DEPTH_ZERO);
		Assert.assertEquals(IMarker.SEVERITY_ERROR, severity);
		
		// does the request for syntax errors return something?
		Collection<Diagnostic> diags = ValidationUtil.validateFileSyntax(modelResource, false, null);
		Assert.assertTrue(diags.size()>0);
	}
	
	@Test
	public void testAutomaticMarkerCreationOnOpen_DS4316() throws CoreException, ModelNotFoundException {

		final IFile file = getModuleFolder().getFolder("Default").getFolder("module").getFile("broken.module");

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				String currentMetaModelVersion = OfsCore.getCurrentMetaModelVersion("module");
				String string = "metamodelVersion = "+currentMetaModelVersion+"\n"+
						"This is a totally broken file!";
				InputStream is;
				try {
					is = new ByteArrayInputStream(string.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(Status.ERROR, "", "Unable to get the stream of the string"));
				}
				file.create(is, true, null);
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
		getOfsProject().refresh();
		file.deleteMarkers(ValidationMarkerUtil.MARKER_ID, true, IResource.DEPTH_ZERO);
		
		URI uri = URI.createURI("resource:///Default/module/broken.module");
		checkResourceAvailability(uri, false);
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(uri);
		ValidationUtil.validate(modelResource, null, false);
		
		// do we have error markers?
		int severity = file.findMaxProblemSeverity(ValidationMarkerUtil.MARKER_ID, true, IResource.DEPTH_ZERO);
		Assert.assertTrue(severity < IMarker.SEVERITY_INFO);
		
		// let's validate again with marker update being turned on
		ValidationUtil.validateFileSyntax(modelResource, true, null);

		// do we now have error markers?
		severity = file.findMaxProblemSeverity(ValidationMarkerUtil.MARKER_ID, true, IResource.DEPTH_ZERO);
		Assert.assertEquals(IMarker.SEVERITY_ERROR, severity);
	}
}
