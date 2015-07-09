package com.odcgroup.pageflow.editor.validation.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.ViewStateGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowViewStateValidationTest extends AbstractPageflowTechnicalValidationTests{

	private static final String PAGEFLOW_NAME = "myTest";
	
	/**
	 * @param name
	 */
	public PageflowViewStateValidationTest() {
		super(PAGEFLOW_NAME);		
	}
	
    /**
     * test the constraints for the viewstate with view of the pageflow
     */
    @Test
    public void testViewStateWithViewConstraints() throws Exception {
        // create viewState
        IElementType viewstate = PageflowElementTypes.ViewState_1004;
        com.odcgroup.pageflow.model.View view = PageflowFactory.eINSTANCE.createView();
        view.setUrl("url");
        ShapeEditPart vsep = createShapeUsingTool(viewstate,
                new Point(100, 100), getDiagramEditPart());
        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getViewState_View(),
                    view);
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "viewState");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "viewState");
        }
        // Select the viewState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep);

        flushEventQueue();
        String[] validationMsgs = validateModel(vsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<viewState>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<viewState>> must have at least one outgoing transition"));

        // flushEventQueue();
    }

	/**
     * test the constraints for the viewstate without view of the pageflow
     */
    @Test
    public void testViewStateConstraints() throws Exception {
        // create viewState
        // flushEventQueue();
        IElementType viewstate = PageflowElementTypes.ViewState_1004;
        ShapeEditPart vsep = createShapeUsingTool(viewstate,
                new Point(100, 100), getDiagramEditPart());
        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "ViewState");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "ViewState");
        }
        // Select the viewState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep);

        flushEventQueue();
        String[] validationMsgs = validateModel(vsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<ViewState>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<ViewState>> must have at least one outgoing transition"));
    }
    
    /**
     * test the constraints for the view state name of the pageflow
     */
    @Test
    public void testViewStateUniqueName() throws Exception {
        // create viewState
        IElementType viewState1 = PageflowElementTypes.ViewState_1004;
        IElementType viewState2 = PageflowElementTypes.ViewState_1004;

        ShapeEditPart vsep = createShapeUsingTool(viewState1, new Point(150,
                150), getDiagramEditPart());
        ShapeEditPart vsep2 = createShapeUsingTool(viewState2, new Point(150,
                300), getDiagramEditPart());

        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispName");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "names");
        }
        if (vsep2.getModel() instanceof Node) {
            Node node = (Node) vsep2.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispName");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep2);

        flushEventQueue();
        String[] validationMsgs = validateModel(vsep2);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<dispName>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<dispName>> must have at least one outgoing transition"));

        // check for warning messages (disabled by DS-6260)
        // Assert.assertTrue("Warning missing", validationMsgs[1].contains("ViewState feature 'Name' of <<dispName>> should be unique"));
    }
    /**
     * test the constraints for the view state name of the pageflow
     */
    @Test
    public void testViewStateUniqueID() throws Exception {
        // create viewState
        IElementType viewState1 = PageflowElementTypes.ViewState_1004;
        IElementType viewState2 = PageflowElementTypes.ViewState_1004;

        ShapeEditPart vsep = createShapeUsingTool(viewState1, new Point(150,
                150), getDiagramEditPart());
        ShapeEditPart vsep2 = createShapeUsingTool(viewState2, new Point(150,
                300), getDiagramEditPart());

        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispName");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");
        }
        if (vsep2.getModel() instanceof Node) {
            Node node = (Node) vsep2.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispName");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep2);

        flushEventQueue();
        String[] validationMsgs = validateModel(vsep2);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();        

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<dispName>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("View State<<dispName>> must have at least one outgoing transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'ID' of <<dispName>> has to be unique."));
        // Assert.assertTrue("Error missing", validationMsgs[0].contains("The value specified [dispName] is already taken"));
    }
    
    /**
     * test the constraints for the view state of the pageflow
     */
    @Test
    public void testViewStateNamePatternAbort() throws Exception {
        // create viewState
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        IElementType viewState = PageflowElementTypes.ViewState_1004;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;

        ShapeEditPart isep = createShapeUsingTool(initState,
                new Point(200, 100), getDiagramEditPart());
        ShapeEditPart vsep = createShapeUsingTool(viewState,
                new Point(200, 300), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState,
                new Point(200, 500), getDiagramEditPart());
        this.createConnectorUsingTool(isep, vsep, transition1);
        this.createConnectorUsingTool(vsep, esep, transition2);

        com.odcgroup.pageflow.model.View view = PageflowFactory.eINSTANCE.createView();
        view.setUrl("url");
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep);
        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getViewState_View(),
                    view);
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Abort");
            
        }
        flushEventQueue();
        String[] validationMsgs = validateModel(vsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "The feature 'Name' of <<Abort>> must be changed as it matches a reserved value";
        // check for error messages
        Assert.assertEquals("Error ", errorMsg, validationMsgs[0].trim());
    }
    
    /**
     * test the constraints for the view state of the pageflow
     */
    @Test
    public void testViewStateNamePatternInitial() throws Exception {
        // create viewState
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        IElementType viewState = PageflowElementTypes.ViewState_1004;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;

        ShapeEditPart isep = createShapeUsingTool(initState,
                new Point(100, 100), getDiagramEditPart());
        ShapeEditPart vsep = createShapeUsingTool(viewState,
                new Point(100, 200), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState,
                new Point(100, 300), getDiagramEditPart());
        this.createConnectorUsingTool(isep, vsep, transition1);
        this.createConnectorUsingTool(vsep, esep, transition2);

        com.odcgroup.pageflow.model.View view = PageflowFactory.eINSTANCE.createView();
        view.setUrl("url");
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep);
        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getViewState_View(),
                    view);
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Initial");
            
        }
        flushEventQueue();
        String[] validationMsgs = validateModel(vsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "The feature 'Name' of <<Initial>> must be changed as it matches a reserved value";
        // check for error messages
        Assert.assertEquals("Error ", errorMsg, validationMsgs[0].trim());
    }
    
    /**
     * test the constraints for the view state of the pageflow
     */
    @Test
    public void testViewStateNamePatternError() throws Exception {
        // create viewState
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        IElementType viewState = PageflowElementTypes.ViewState_1004;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;

        ShapeEditPart isep = createShapeUsingTool(initState,
                new Point(100, 200), getDiagramEditPart());
        ShapeEditPart vsep = createShapeUsingTool(viewState,
                new Point(100, 300), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState,
                new Point(100, 400), getDiagramEditPart());
        this.createConnectorUsingTool(isep, vsep, transition1);
        this.createConnectorUsingTool(vsep, esep, transition2);

        com.odcgroup.pageflow.model.View view = PageflowFactory.eINSTANCE.createView();
        view.setUrl("url");
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep);
        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getViewState_View(),
                    view);
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Error");
            
        }
        flushEventQueue();
        String[] validationMsgs = validateModel(vsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ViewStateGeneralPropertySection);
        ViewStateGeneralPropertySection is = (ViewStateGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "The feature 'Name' of <<Error>> must be changed as it matches a reserved value";
        // check for error messages
        Assert.assertEquals("Error ", errorMsg, validationMsgs[0].trim());
    }
   
}
