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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.DecisionStateGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;
import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowDecisionStateValidationTest extends AbstractPageflowTechnicalValidationTests{

	private static final String PAGEFLOW_NAME = "myDecision";
	
    /**
     * @param name
     */
    public PageflowDecisionStateValidationTest() {
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
        String[] validationMsgs = validateModel(dsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();        

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<decisionState>> must have atleast one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<decisionState>> must have at least two outgoing transitions"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action Name must be specified"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action <<decisionState>>'s URI is empty"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("DecisionState <<decisionState>>'s action must specified"));

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
        String[] validationMsgs = validateModel(dsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();  

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<DecisionState>> must have atleast one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<DecisionState>> must have at least two outgoing transitions"));
        
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
        
        String[] validationMsgs = validateModel(vsep2);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();

        // check for warning messages (disabled by DS-6260)
        // Assert.assertTrue("Warning missing", validationMsgs[1].contains("DecisionState feature 'Name' of <<dispname>> should be unique"));
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
        
        String[] validationMsgs = validateModel(vsep2);

        flushEventQueue();
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<dispname>> must have atleast one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<dispname>> must have at least two outgoing transitions"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'ID' of <<dispname>> has to be unique. The value specified [name] is already taken"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action Name must be specified"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action <<name>>'s URI is empty"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("DecisionState <<dispname>>'s action must specified"));

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
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType endState = PageflowElementTypes.EndState_1003;
        ShapeEditPart dsep = createShapeUsingTool(decisionState, new Point(300,
                100), getDiagramEditPart());
        createShapeUsingTool(initState, new Point(100,
                100), getDiagramEditPart());
        createShapeUsingTool(endState, new Point(500,
                100), getDiagramEditPart());
        if (dsep.getModel() instanceof Node) {
            Node node = (Node) dsep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Error");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
            "Gateway");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        
        String[] validationMsgs = validateModel(dsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<Error>> must have at least two outgoing transitions"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Error>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action Name must be specified"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action <<Gateway>>'s URI is empty"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("DecisionState <<Error>>'s action must specified"));
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
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
            "Gateway");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        
        String[] validationMsgs = validateModel(dsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<Abort>> must have at least two outgoing transitions"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Abort>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action Name must be specified"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action <<Gateway>>'s URI is empty"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("DecisionState <<Abort>>'s action must specified"));
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
            setProperty(obj, PageflowPackage.eINSTANCE.getState_Name(),
            "Gateway");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(dsep);
        flushEventQueue();
        
        String[] validationMsgs = validateModel(dsep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof DecisionStateGeneralPropertySection);
        DecisionStateGeneralPropertySection is = (DecisionStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision State<<Initial>> must have at least two outgoing transitions"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Initial>> must be changed as it matches a reserved value"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action Name must be specified"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("Decision state Action <<Gateway>>'s URI is empty"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("DecisionState <<Initial>>'s action must specified"));
        rootViewer.deselectAll();
        dsep.deactivate();
    }
   
  }
