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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.SubPageflowGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowSubPageflowTest extends AbstractPageflowTechnicalValidationTests{

	private static final String PAGEFLOW_NAME = "myTest";
	/**
	 * @param name
	 */
	public PageflowSubPageflowTest() {
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

       // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<subPageflow>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<subPageflow>> must have at least one outgoing flow"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<subPageflow>>'s sub-pageflow must be specified"));
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Abort>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Abort>> must have at least one outgoing flow"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Abort>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Abort>>'s sub-pageflow must be specified"));
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Error>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Error>> must have at least one outgoing flow"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Error>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Error>>'s sub-pageflow must be specified"));
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Initial>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Initial>> must have at least one outgoing flow"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Initial>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<Initial>>'s sub-pageflow must be specified"));
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

        // check for warning messages (disable by DS-6260)
        // Assert.assertTrue("Warning missing", validationMsgs[1].contains("SubPageflowState feature 'Name' of <<SubPage>> should be unique"));
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
        String[] validationMsgs = validateModel(isep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof SubPageflowGeneralPropertySection);
        SubPageflowGeneralPropertySection is = (SubPageflowGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<SubPage>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<SubPage>> must have at least one outgoing flow"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'ID' of <<SubPage>> has to be unique. The value specified [SubPageflow] is already taken"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("SubPageflowState<<SubPage>>'s sub-pageflow must be specified"));
    }
        
}
