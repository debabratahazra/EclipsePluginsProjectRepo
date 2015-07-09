package com.odcgroup.pageflow.editor.validation.tests;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.pageflow.editor.diagram.custom.actions.DuplicateTransitionActionDelegate;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.TransitionGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowTransitionValidationtest extends AbstractPageflowTechnicalValidationTests{

	private static final String PAGEFLOW_NAME = "myTest"; 
	
	/**
	 * @param name
	 */
	public PageflowTransitionValidationtest() {
		super(PAGEFLOW_NAME);		
	}
	
	/**
	 * DS-3376
	 * @throws Exception
	 */
    @Test
	public void testDS3376_DuplicateTransitionsAction() throws Exception {
		// create transition
        IElementType viewState = PageflowElementTypes.ViewState_1004;
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType transition = PageflowElementTypes.Transition_3001;
        
        ShapeEditPart vsep = createShapeUsingTool(initState, new Point(200,
                150), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(viewState,
                new Point(300, 100), getDiagramEditPart());
        ConnectionEditPart connection = createConnectorUsingTool(vsep, esep, transition);       
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(connection);
        flushEventQueue();
        
        if (connection.getModel() instanceof Edge) {
            Edge node = (Edge) connection.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(), "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(), "Abort");
            EObject root = obj.eContainer();
            Assert.assertTrue(root instanceof Pageflow);
            Pageflow pf = (Pageflow) obj.eContainer();
            Assert.assertTrue(pf.getTransitions().size() == 1);
            // test duplicate action  
            DuplicateTransitionActionDelegate action = new DuplicateTransitionActionDelegate();
            action.setActivePart(null, getDiagramWorkbenchPart());
            action.selectionChanged(null, rootViewer.getSelection());
            action.run(new NullProgressMonitor());
            Assert.assertTrue("pageflow transitions size should be 2", pf.getTransitions().size() == 2);        }
        
        
	}

	/**
	 * DS-1350 (slight changes for transitions coming out of initstate
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testTransitionStateID() throws Exception {
        // create transition
        IElementType viewState = PageflowElementTypes.ViewState_1004;
        IElementType initState = PageflowElementTypes.InitState_1001;
        IElementType transition = PageflowElementTypes.Transition_3001;

        ShapeEditPart vsep = createShapeUsingTool(initState, new Point(200,
                150), getDiagramEditPart());
        ShapeEditPart esep = createShapeUsingTool(viewState,
                new Point(300, 100), getDiagramEditPart());
        ConnectionEditPart connection = createConnectorUsingTool(vsep, esep,
                transition);
        if (connection.getModel() instanceof Edge) {
            Edge node = (Edge) connection.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(), "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(), "Abort");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(connection);
        flushEventQueue();
        String[] validationMsgs = validateModel(connection);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof TransitionGeneralPropertySection);
        String errorMsg = "The feature 'Name' of <<Abort>> must be changed as it matches a reserved value";
        // check for error messages
        Assert.assertEquals(errorMsg, validationMsgs[0].toString().trim());
    }
    
    /**
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testTransitionStateIDUnique() throws Exception {
        // create transition
        IElementType endState1 = PageflowElementTypes.EndState_1003;
        IElementType endState2 = PageflowElementTypes.EndState_1003;
        IElementType ViewState = PageflowElementTypes.ViewState_1004;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;

        ShapeEditPart vsep = createShapeUsingTool(ViewState, new Point(200,
                150), getDiagramEditPart());
        ShapeEditPart esep1 = createShapeUsingTool(endState1,
                new Point(300, 100), getDiagramEditPart());
        ShapeEditPart esep2 = createShapeUsingTool(endState2,
                new Point(300, 300), getDiagramEditPart());
        ConnectionEditPart connection = createConnectorUsingTool(vsep, esep1,
                transition1);
        ConnectionEditPart connection1 = createConnectorUsingTool(vsep, esep2,
                transition2);
        if (connection.getModel() instanceof Edge) {
            Edge node = (Edge) connection.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(),
            "Abort1");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(),
            "Abort");
        }
        if (connection1.getModel() instanceof Edge) {
            Edge node = (Edge) connection1.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(),
            "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(),
            "Abort");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(connection);
        flushEventQueue();
        String[] validationMsgs = validateModel(connection);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof TransitionGeneralPropertySection);
        String errorMsg = "The feature 'ID' of <<Abort1>> has to be unique. The value specified [Abort1] is already taken";
        // check for error messages
        Assert.assertEquals(errorMsg, validationMsgs[0].toString().trim());
    }
    /**
     * test the constraints for the transition state of the pageflow
     */
    @Test
    public void testTransitionStateNameUnique() throws Exception {
        // create transition
        IElementType endState1 = PageflowElementTypes.EndState_1003;
        IElementType endState2 = PageflowElementTypes.EndState_1003;
        IElementType ViewState = PageflowElementTypes.ViewState_1004;
        IElementType transition1 = PageflowElementTypes.Transition_3001;
        IElementType transition2 = PageflowElementTypes.Transition_3001;

        ShapeEditPart vsep = createShapeUsingTool(ViewState, new Point(200,
                150), getDiagramEditPart());
        ShapeEditPart esep1 = createShapeUsingTool(endState1,
                new Point(300, 100), getDiagramEditPart());
        ShapeEditPart esep2 = createShapeUsingTool(endState2,
                new Point(300, 300), getDiagramEditPart());
        ConnectionEditPart connection = createConnectorUsingTool(vsep, esep1,
                transition1);
        ConnectionEditPart connection1 = createConnectorUsingTool(vsep, esep2,
                transition2);
        if (connection.getModel() instanceof Edge) {
            Edge node = (Edge) connection.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(),
            "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(),
            "Abort1");
        }
        if (connection1.getModel() instanceof Edge) {
            Edge node = (Edge) connection1.getModel();
            EObject obj = node.getElement();           
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_DisplayName(),
            "Abort");
            setProperty(obj, PageflowPackage.eINSTANCE.getTransition_Name(),
            "Abort");
        }
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(connection);
        flushEventQueue();
        String[] validationMsgs = validateModel(connection);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof TransitionGeneralPropertySection);
        String errorMsg = 
        	"| The feature 'Name' of <<Abort>> must be changed as it matches a reserved value | " +
        	"The feature 'Name' of <<Abort>> has to be unique. The value specified [Abort] is already taken |";
        // check for error messages
        Assert.assertEquals(errorMsg, validationMsgs[0].toString().trim());
    }
   
}
