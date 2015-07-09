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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl.SubPageflowGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowSketchValidationTest;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowSubPageflowSketchTest extends AbstractPageflowSketchValidationTest{

	private static final String PAGEFLOW_NAME = "myTest";
	
	/**
	 * @param name
	 */
	public PageflowSubPageflowSketchTest() {
		super(PAGEFLOW_NAME);		
	}
	
	/**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateConstraints() throws Exception{
        // create SubPageFlowState
    	
        IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "subPageflow");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "subPageflow");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| SubPageflowState's <<subPageflow>>'s sub-pageflow must specified | " +
        		"SubPageflow State<<subPageflow>> must have at least one incoming transition | " +
        		"SubPageflow State<<subPageflow>> must have at least one outgoing flow | " +
        		"SubPageflow State<<subPageflow>> must have at least 1 transition-mapping |";
        	        		
        // check for error messages
        Assert.assertEquals(errorMsg, errorMsg, is.getErrorMsg().toString().trim());          	
    }
    
    /**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateNamePatternAbort() throws Exception {
        // create SubPageFlowState
    	IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "subPageflow");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| The feature 'Name' of <<Abort>> must be changed as it matches a reserved value " +
        		"| SubPageflowState's <<Abort>>'s sub-pageflow must specified " +
        		"| SubPageflow State<<Abort>> must have at least one incoming transition " +
        		"| SubPageflow State<<Abort>> must have at least one outgoing flow " +
        		"| SubPageflow State<<Abort>> must have at least 1 transition-mapping |";
               // check for error messages
        Assert.assertEquals("Error ", errorMsg, is.getErrorMsg().toString().trim());       	
    }
    
    /**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateNamePatternError() throws Exception {
        // create SubPageFlowState
    	IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Error");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "subPageflow");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| The feature 'Name' of <<Error>> must be changed as it matches a reserved value " +
        		"| SubPageflowState's <<Error>>'s sub-pageflow must specified " +
        		"| SubPageflow State<<Error>> must have at least one incoming transition " +
        		"| SubPageflow State<<Error>> must have at least one outgoing flow " +
        		"| SubPageflow State<<Error>> must have at least 1 transition-mapping |";
               // check for error messages
        Assert.assertEquals("Error ", errorMsg, is.getErrorMsg().toString().trim());     	
    }
    
    /**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateNamePatternInitial() throws Exception {
        // create SubPageFlowState    	
        IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Initial");            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "subPageflow");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| The feature 'Name' of <<Initial>> must be changed as it matches a reserved value " +
        		"| SubPageflowState's <<Initial>>'s sub-pageflow must specified " +
        		"| SubPageflow State<<Initial>> must have at least one incoming transition " +
        		"| SubPageflow State<<Initial>> must have at least one outgoing flow " +
        		"| SubPageflow State<<Initial>> must have at least 1 transition-mapping |";
               // check for error messages
        Assert.assertEquals("Error ", errorMsg, is.getErrorMsg().toString().trim());     	
    }
    
    /**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateNameUnique() throws Exception {
        // create SubPageFlowState
    	IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        IElementType subpageflowstate1 = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        ShapeEditPart ssep = createShapeUsingTool(subpageflowstate1, new Point(
                500, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "SubPage");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "SubPageflow");
        }
        if (ssep.getModel() instanceof Node) {
            Node node = (Node) ssep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "SubPage");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "SubName");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String warnMsg = "| SubPageflowState feature 'Name' of <<SubPage>> should be unique |";
               // check for error messages
        Assert.assertEquals("Error ", warnMsg, is.getWarnMsg().toString().trim());      	
    }
    
    /**
     * test the constraints for the DecisionState of the pageflow
     */
    @Test
    public void testSubPageflowStateIDUnique() throws Exception {
        // create SubPageFlowState
    	IElementType subpageflowstate = PageflowElementTypes.SubPageflowState_1005;
        IElementType subpageflowstate1 = PageflowElementTypes.SubPageflowState_1005;
        ShapeEditPart isep = createShapeUsingTool(subpageflowstate, new Point(
                100, 300), getDiagramEditPart());
        ShapeEditPart ssep = createShapeUsingTool(subpageflowstate1, new Point(
                500, 300), getDiagramEditPart());
        if (isep.getModel() instanceof Node) {
            Node node = (Node) isep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "SubPage");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "SubPageflow");
        }
        if (ssep.getModel() instanceof Node) {
            Node node = (Node) ssep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "SubPageflow");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "SubPageflow");
        }
        // Select the SubPageFlowstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(isep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String errMsg = "| SubPageflowState's <<SubPage>>'s sub-pageflow must specified " +
        		"| SubPageflow State<<SubPage>> must have at least one incoming transition " +
        		"| SubPageflow State<<SubPage>> must have at least one outgoing flow " +
        		"| SubPageflow State<<SubPage>> must have at least 1 transition-mapping |";
               // check for error messages
        Assert.assertEquals("Error ", errMsg, is.getErrorMsg().toString().trim());     	
    }
    
    /**
     * test the constraints for the view state of the pageflow
     */
    @Test
    public void testSubPageFlowStateOutTransition() throws Exception {
        // create viewState
        IElementType endState = PageflowElementTypes.EndState_1003;
        IElementType subPageflowState = PageflowElementTypes.SubPageflowState_1005;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;
        
        ShapeEditPart ssep = createShapeUsingTool(subPageflowState,
                new Point(200, 300), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState,
                new Point(200, 500), getDiagramEditPart());
        this.createConnectorUsingTool(ssep, esep, transition1);
        this.createConnectorUsingTool(ssep, esep, transition2);
        
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(ssep);
        if (ssep.getModel() instanceof Node) {
            Node node = (Node) ssep.getModel();
            EObject obj = node.getElement();            
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "SubPage");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
            "SubPage");
        }
        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();
        String warnMsg = "| There should not be 2 outgoing transitions pointing to the same destination |";
        // check for warning messages
        Assert.assertEquals(warnMsg, is.getWarnMsg().toString().trim());    	
    }   
  
}
