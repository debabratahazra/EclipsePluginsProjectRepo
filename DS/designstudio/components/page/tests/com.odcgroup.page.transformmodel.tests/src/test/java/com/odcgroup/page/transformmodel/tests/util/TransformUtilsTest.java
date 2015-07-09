package com.odcgroup.page.transformmodel.tests.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;

/**
 * Tests the XML Transformers.
 *
 * @author Gary Hayes
 */
public class TransformUtilsTest {

	/** Project name. */
	private static String PROJECT_NAME = TransformUtilsTest.class.getName();

	/** The project. */
    IProject project;

    private static final IProject NULL_PROJECT = null;
	private static final Widget NULL_WIDGET = null;
	private IProject mockProject;
	private Widget mockWidget;

	/**
     * Setup.
     *
     * @throws Exception
     */
    @Before
	public void setUp() throws Exception {
        project = createProject();
        mockProject = Mockito.mock(IProject.class);
		mockWidget = Mockito.mock(Widget.class);
    }

    /**
     * tearDown.
     *
     * @throws Exception
     */
    @After
	public void tearDown() throws Exception {
        project.close(null);
        project.delete(true, null);
        
    	IWorkspace workspace = null;
        workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription description = workspace.getDescription();
        description.setAutoBuilding(true);
        workspace.setDescription(description);

    }

	protected IProject createProject() throws Exception {
    	IWorkspace workspace = null;
        workspace = ResourcesPlugin.getWorkspace();

        IWorkspaceDescription description = workspace.getDescription();
        description.setAutoBuilding(false);
        workspace.setDescription(description);

        IWorkspaceRoot wsroot = workspace.getRoot();
        IProject project = wsroot.getProject(PROJECT_NAME);
        project.create(null);
        project.open(null);
        IProjectDescription desc = workspace.newProjectDescription(project.getName());

        new OfsProjectInitializer().updateConfiguration(project, null);
        Assert.assertTrue(OfsCore.isOfsProject(project));

        IPath locationPath = Platform.getLocation();
        locationPath = null;
        desc.setLocation(locationPath);
        if (!project.exists()) project.create(desc, null);
        if (!project.isOpen()) project.open(null);

        return project;
	}

	/**
	 * Test the transformation of an empty Include Widget raises an exception
	 */
	@Test
	public void testTransformIncludeWidget() {

		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);

		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetLibrary wl = mm.findWidgetLibrary(WidgetLibraryConstants.XGUI);
		WidgetTemplate wt = wl.findWidgetTemplate(WidgetTypeConstants.INCLUDE);
		WidgetFactory wf = new WidgetFactory();
		Widget w = wf.create(wt);

		// Most WidgetTransformers do not expect to be the root. We therefore
		// add a root Element which we know exists and transform that.
		WidgetType wty = mm.findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.MODULE);
		Widget root = wf.create(wty);
		root.getContents().add(w);

		ResourceSet rs = ofsProject.getModelResourceSet();
		URI uri = URI.createFileURI("C:/Dummy.txt");
		Resource r = rs.createResource(uri);
		r.getContents().add(root);

		// include nothing
		try {
			TransformUtils t = new TransformUtils();
			t.transformWidgetToW3CXmlDoc(project, root);
			Assert.fail("The transformation of an empty Include widget must raise an exception");
		} catch (CoreException ex) {
			Assert.assertTrue(true);
		}

	}

	@Test
	public void testGetXMLWithANullProjectThrowsAPageException() throws Exception {

		try {
			TransformUtils.transformWidgetToXmlString(NULL_PROJECT, mockWidget);
			Assert.fail("Should have thrown a Core");
		} catch (PageException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testGetXMLWithANullWidgetThrowsAPageException() throws Exception {
		try {
			TransformUtils.transformWidgetToXmlString(mockProject, NULL_WIDGET);
			Assert.fail("Should have thrown a Core");
		} catch (PageException e) {
			Assert.assertTrue(true);
		}
	}
}
