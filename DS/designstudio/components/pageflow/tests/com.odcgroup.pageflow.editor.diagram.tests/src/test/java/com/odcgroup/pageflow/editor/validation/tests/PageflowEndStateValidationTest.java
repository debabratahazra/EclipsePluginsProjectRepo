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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.EndStateGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowEndStateValidationTest extends AbstractPageflowTechnicalValidationTests{

	private static final String PAGEFLOW_NAME = "myEnd";
	
	public PageflowEndStateValidationTest() {
		super(PAGEFLOW_NAME);		
	}
	
	/**
     * test the constraints for the endstate of the pageflow
     */
	@Test
    public void testEndStateConstraints() throws Exception {
        // create endState
        IElementType endstate = PageflowElementTypes.EndState_1003;
        ShapeEditPart esep = createShapeUsingTool(endstate,
                new Point(200, 100), getDiagramEditPart());
        
        if (esep.getModel() instanceof Node) {
            Node node = (Node) esep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getEndState_ExitUrl(),
                    "someuri");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "EndState");
        }        
        
        // Select the endState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        
        saveDiagram();
        
        rootViewer.select(esep);

        flushEventQueue();
        String[] validationMsgs = validateModel(esep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
        is.refresh();       

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("EndState<<EndState>> must have at least one incoming transition"));
    }
    
    /**
     * test the constraints for the endstate of the pageflow
     */
	@Test
    public void testEndStateNamePatternAbort() throws Exception {
        // create endState
        IElementType endstate = PageflowElementTypes.EndState_1003;
        ShapeEditPart esep = createShapeUsingTool(endstate,
                new Point(200, 100), getDiagramEditPart());
        
        if (esep.getModel() instanceof Node) {
            Node node = (Node) esep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getEndState_ExitUrl(),
                    "someuri");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Abort");
        }
        // Select the endState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(esep);

        flushEventQueue();
        String[] validationMsgs = validateModel(esep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("EndState<<Abort>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Abort>> must be changed as it matches a reserved value"));
    }
    
    /**
     * test the constraints for the endstate of the pageflow
     */
    @Test
    public void testEndStateNamePatternInitial() throws Exception {
        // create endState
        IElementType endstate = PageflowElementTypes.EndState_1003;
        ShapeEditPart esep = createShapeUsingTool(endstate,
                new Point(200, 100), getDiagramEditPart());
        
        if (esep.getModel() instanceof Node) {
            Node node = (Node) esep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getEndState_ExitUrl(),
                    "someuri");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Initial");
        }
        // Select the endState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(esep);

        flushEventQueue();
        String[] validationMsgs = validateModel(esep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("EndState<<Initial>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Initial>> must be changed as it matches a reserved value"));
    }
    
    /**
     * test the constraints for the endstate of the pageflow
     */
	@Test
    public void testEndStateNamePatternError() throws Exception {
        // create endState
        IElementType endstate = PageflowElementTypes.EndState_1003;
        ShapeEditPart esep = createShapeUsingTool(endstate,
                new Point(200, 100), getDiagramEditPart());
        
        if (esep.getModel() instanceof Node) {
            Node node = (Node) esep.getModel();
            EObject obj = node.getElement();
            setProperty(obj, PageflowPackage.eINSTANCE.getEndState_ExitUrl(),
                    "someuri");
            setProperty(obj, PageflowPackage.eINSTANCE.getState_DisplayName(),
                    "Error");
        }
        // Select the endState.
        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
        rootViewer.deselectAll();
        rootViewer.select(esep);

        flushEventQueue();
        String[] validationMsgs = validateModel(esep);
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
        is.refresh();

        // check for error messages
        Assert.assertTrue("Error missing", validationMsgs[0].contains("EndState<<Error>> must have at least one incoming transition"));
        Assert.assertTrue("Error missing", validationMsgs[0].contains("The feature 'Name' of <<Error>> must be changed as it matches a reserved value"));
    }
    
   
}
