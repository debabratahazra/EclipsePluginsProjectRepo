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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl.DecisionStateGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowSketchValidationTest;
import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowDecisionStateSketchValidationTest extends AbstractPageflowSketchValidationTest{

	private static final String PAGEFLOW_NAME = "myDecision";
	
    /**
     * @param name
     */
    public PageflowDecisionStateSketchValidationTest() {
		super(PAGEFLOW_NAME);		
	}

    /**
     * test the constraints for the DecisionState without action of the pageflow
     */
    @Test
    public void testDecisionStateConstraints() throws Exception {
        // create decisionState
        IElementType decisionstate = PageflowElementTypes.DecisionState_1002;
        ShapeEditPart dsep = createShapeUsingTool(decisionstate, new Point(100,
                100), getDiagramEditPart());
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "decisionState");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "decisionState");
        }
        // Select the decisionstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| Decision State<<decisionState>> must have exactly one incoming transition " +
        		"| Decision State<<decisionState>> must have at least two outgoing transitions |";
        // check for error messages
        Assert.assertEquals("Error ", errorMsg, is.getErrorMsg().toString().trim());
       rootViewer.deselectAll();
        dsep.deactivate();
    }

    /**
     * test the constraints for the DecisionState with action of the pageflow
     */
    @Test
    public void testDecisionStateWithActionConstraints() throws Exception {
        // create decisionState
        IElementType decisionstate = PageflowElementTypes.DecisionState_1002;
        // create action
        DecisionAction action = PageflowFactory.eINSTANCE.createDecisionAction();
        action.setName("name");
        action.setUri("uri");
        ShapeEditPart dsep = createShapeUsingTool(decisionstate, new Point(300,
                200), getDiagramEditPart());
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            // add action to decision state
            setProperty(obj,
                    PageflowPackage.eINSTANCE.getDecisionState_Action(), action);
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "DecisionState");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
                    "DecisionState");
        }
        // Select the decisionstate.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        String errorMsg = "| Decision State<<DecisionState>> must have exactly one incoming transition | " +
        		"Decision State<<DecisionState>> must have at least two outgoing transitions |";
        // check for error messages
        Assert.assertEquals("Error ", errorMsg, is.getErrorMsg().toString().trim());
        // check for warning messages
        Assert.assertEquals("|", is.getWarnMsg().toString().trim());
        rootViewer.deselectAll();
        dsep.deactivate();
    }

    /**
     * test the constraints for the view state name of the pageflow
     */
    @Test
    public void testDecisionStateUniqueName() throws Exception {
        // create decisionState
        IElementType decisionState1 = PageflowElementTypes.DecisionState_1002;
        IElementType decisionState2 = PageflowElementTypes.DecisionState_1002;

        ShapeEditPart vsep = createShapeUsingTool(decisionState1, new Point(
                200, 100), getDiagramEditPart());
        ShapeEditPart vsep2 = createShapeUsingTool(decisionState2, new Point(
                200, 200), getDiagramEditPart());

        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispname");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");
        }
        if (vsep2.getModel() instanceof Node) {
            Node node = (Node) vsep2.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispname");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");            
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep2);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        String warning = "| DecisionState feature 'Name' of <<dispname>> should be unique |";
        Assert.assertEquals(warning, is.getWarnMsg().toString().trim());
        rootViewer.deselectAll();
        vsep2.deactivate();
    }
    /**
     * test the constraints for the view state name of the pageflow
     */
    @Test
    public void testDecisionStateUniqueID() throws Exception {
        // create decisionState
        IElementType decisionState1 = PageflowElementTypes.DecisionState_1002;
        IElementType decisionState2 = PageflowElementTypes.DecisionState_1002;

        ShapeEditPart vsep = createShapeUsingTool(decisionState1, new Point(
                200, 100), getDiagramEditPart());
        ShapeEditPart vsep2 = createShapeUsingTool(decisionState2, new Point(
                200, 200), getDiagramEditPart());

        if (vsep.getModel() instanceof Node) {
            Node node = (Node) vsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispname");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");
        }
        if (vsep2.getModel() instanceof Node) {
            Node node = (Node) vsep2.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "dispname");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(), "name");            
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(vsep2);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        String error = "| Decision State<<dispname>> must have exactly one incoming transition | " +
        		"Decision State<<dispname>> must have at least two outgoing transitions |" ;
        		
        Assert.assertEquals(error, is.getErrorMsg().toString().trim());
        rootViewer.deselectAll();
        vsep2.deactivate();
    }
   
   
    /**
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testDecisionStateNamePatternError() throws Exception {
        // create decisionState
        IElementType decisionState = PageflowElementTypes.DecisionState_1002;
        IElementType transition = PageflowElementTypes.Transition_3001;
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        ShapeEditPart dsep = createShapeUsingTool(decisionState, new Point(300,
                100), getDiagramEditPart());
        ShapeEditPart isep = createShapeUsingTool(initState, new Point(100,
                100), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState, new Point(500,
                100), getDiagramEditPart());
        createConnectorUsingTool(dsep, esep,
                transition);
        createConnectorUsingTool(isep, dsep,
                transition);
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Error");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        // check for error messages
        String errorMsg = "| The feature 'Name' of <<Error>> must be changed as it matches a reserved value | " +
        		"Decision State<<Error>> must have at least two outgoing transitions |";
        Assert.assertEquals(errorMsg, is.getErrorMsg().toString().trim());
        rootViewer.deselectAll();
        dsep.deactivate();
    }
    
    /**
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testDecisionStateNamePatternAbort() throws Exception {
        // create decisionState
        IElementType decisionState = PageflowElementTypes.DecisionState_1002;
        IElementType transition = PageflowElementTypes.Transition_3001;
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        ShapeEditPart dsep = createShapeUsingTool(decisionState, new Point(300,
                100), getDiagramEditPart());
        ShapeEditPart isep = createShapeUsingTool(initState, new Point(100,
                100), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState, new Point(500,
                100), getDiagramEditPart());
        createConnectorUsingTool(dsep, esep,
                transition);
        createConnectorUsingTool(isep, dsep,
                transition);
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Abort");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        // check for error messages
        String errorMsg = "| The feature 'Name' of <<Abort>> must be changed as it matches a reserved value | " +
        		"Decision State<<Abort>> must have at least two outgoing transitions |";
       		
        Assert.assertEquals(errorMsg, is.getErrorMsg().toString().trim());
        rootViewer.deselectAll();
        dsep.deactivate();
    }
    
    /**
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testDecisionStateNamePatternInitial() throws Exception {
        // create decisionState
        IElementType decisionState = PageflowElementTypes.DecisionState_1002;
        IElementType transition = PageflowElementTypes.Transition_3001;
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        ShapeEditPart dsep = createShapeUsingTool(decisionState, new Point(300,
                100), getDiagramEditPart());
        ShapeEditPart isep = createShapeUsingTool(initState, new Point(100,
                100), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(endState, new Point(500,
                100), getDiagramEditPart());
		createConnectorUsingTool(dsep, esep,
                transition);
        createConnectorUsingTool(isep, dsep,
                transition);
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Initial");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();
        // check for error messages
        String errorMsg = "| The feature 'Name' of <<Initial>> must be changed as it matches a reserved value | " +
        		"Decision State<<Initial>> must have at least two outgoing transitions |";
        Assert.assertEquals(errorMsg, is.getErrorMsg().toString().trim());
        rootViewer.deselectAll();
        dsep.deactivate();
    }
   
  }
