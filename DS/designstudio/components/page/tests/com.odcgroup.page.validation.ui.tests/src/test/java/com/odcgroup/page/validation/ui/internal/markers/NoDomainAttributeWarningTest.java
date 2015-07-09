package com.odcgroup.page.validation.ui.internal.markers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.PartInitException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.tap.validation.ValidationMarkerUtil;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

/**
 * DS-3894
 *
 * @author pkk
 *
 */
public class NoDomainAttributeWarningTest extends AbstractDSUnitTest {
	
	private static final String FRAGMENT = "fragment/Default/ds3894/DS3894.fragment";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(FRAGMENT);
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
	
	/**
	 * @throws PartInitException
	 */
	@Test
	public void testDS3894DomainAttributeWarning() throws Exception {
		IFile fragment = getProject().getFile(FRAGMENT);
		Assert.assertTrue(fragment.exists());
		Object adapter = fragment.getAdapter(IOfsElement.class);
		if(adapter instanceof IOfsModelResource) {
			IOfsModelResource modelResource = (IOfsModelResource) adapter;
			Assert.assertFalse(ValidationUtil.validate(modelResource, null, true).isOK());
			IMarker[] markers = fragment.findMarkers(ValidationMarkerUtil.MARKER_ID, false, IResource.DEPTH_ONE);
			Assert.assertTrue(markers.length == 1);
			IMarker marker = markers[0];
			Assert.assertTrue(marker.getAttribute(IMarker.SEVERITY).equals(IMarker.SEVERITY_WARNING));
			String msg = "The domain attribute  is not defined";
			Assert.assertTrue(marker.getAttribute(IMarker.MESSAGE).equals(msg));
		} else {
			Assert.fail("Cannot find test model!");
		}	
	}

}
