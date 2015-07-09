package com.odcgroup.page.validation.ui.internal.markers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.tap.validation.ValidationMarkerUtil;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

public class ValidationProblemResolutionGeneratorTest extends AbstractDSUnitTest {

	private static final String TEST_MODULE = "module/Default/module/DS3583_MissingIdModule.module";
	private static final String MESSAGE_PROPERTY_ID = "The property Id must be set";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGetResolutions() throws CoreException {
		copyResourceInModelsProject(TEST_MODULE);
		IFile file = getProject().getFile(TEST_MODULE);
		Object adapter = file.getAdapter(IOfsElement.class);
		if(adapter instanceof IOfsModelResource) {
			IOfsModelResource modelResource = (IOfsModelResource) adapter;
			Assert.assertFalse(ValidationUtil.validate(modelResource, null, true).isOK());
			IMarker[] markers = file.findMarkers(ValidationMarkerUtil.MARKER_ID, false, IResource.DEPTH_ONE);
			Assert.assertTrue(markers.length>0);
			IMarker selected = null;
			for(IMarker marker : markers) {
				if(marker.getAttribute(IMarker.MESSAGE).equals(MESSAGE_PROPERTY_ID)) {
					selected = marker;
					break;
				}
			}
			IMarkerResolution[] found = IDE.getMarkerHelpRegistry().getResolutions(selected);
			Assert.assertEquals(2, found.length);
			found[0].run(selected);
			Assert.assertTrue(ValidationUtil.validate(modelResource, null, true).isOK());
		} else {
			Assert.fail("Cannot find test model!");
		}
	}

	@Test
	public void testHasResolutions() throws CoreException {
		copyResourceInModelsProject(TEST_MODULE);
		IFile file = getProject().getFile(TEST_MODULE);
		Object adapter = file.getAdapter(IOfsElement.class);
		if(adapter instanceof IOfsModelResource) {
			IOfsModelResource modelResource = (IOfsModelResource) adapter;
			Assert.assertFalse(ValidationUtil.validate(modelResource, null, true).isOK());
			IMarker[] markers = file.findMarkers(ValidationMarkerUtil.MARKER_ID, false, IResource.DEPTH_ONE);
			Assert.assertTrue(markers.length>0);
			IMarker selected = null;
			for(IMarker marker : markers) {
				if(marker.getAttribute(IMarker.MESSAGE).equals(MESSAGE_PROPERTY_ID)) {
					selected = marker;
					break;
				}
			}
			Assert.assertTrue(new ValidationProblemResolutionGenerator().hasResolutions(selected));
			
			// now check that it returns false on other markers
			IMarker marker = file.createMarker("test");
			Assert.assertFalse(new ValidationProblemResolutionGenerator().hasResolutions(marker));
		} else {
			Assert.fail("Cannot find test model!");
		}
	}

}
