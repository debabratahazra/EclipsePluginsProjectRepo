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

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl.EndStateGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl.InitStatePropertySection;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.impl.PageflowGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowSketchValidationTest;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @author pkk
 *
 */
public class PageflowEndStateSketchValidationTest extends AbstractPageflowSketchValidationTest{
	
	private static final String PAGEFLOW_NAME = "myPageflow";
	    
	    /**
	     * @param name
	     */
	    public PageflowEndStateSketchValidationTest() {
	        super(PAGEFLOW_NAME);
	    }
	    
	    /**
	     * test the constraints for the pageflow root element
	     */
	    @Test
	    public void testPageflowConstraints() throws Exception {
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof PageflowGeneralPropertySection);
	        if (section instanceof PageflowGeneralPropertySection) {
	            PageflowGeneralPropertySection ps = (PageflowGeneralPropertySection) section;
	            String errorMsg = "| InitState is not specified for pageflow<<"+PAGEFLOW_NAME+">> " +
	            		"| EndState is not specified for pageflow<<"+PAGEFLOW_NAME+">> |" ;
	            	            
	         // check for error messages
	            Assert.assertEquals(errorMsg, ps.getErrorMsg().toString().trim());
	           
	            // create initState
	            IElementType initstate = PageflowElementTypes.InitState_1001;
	            createShapeUsingTool(initstate, new Point(100,
	                    100), getDiagramEditPart());
	            ps.refresh();	           
	            getCommandStack().undo();

	            IElementType endState = PageflowElementTypes.EndState_1003;
	            createShapeUsingTool(endState, new Point(300,
	                    100), getDiagramEditPart());
	            ps.refresh();
	            errorMsg ="| InitState is not specified for pageflow<<"+PAGEFLOW_NAME+">> |";
	            // check for error messages
	            Assert.assertEquals(errorMsg, ps.getErrorMsg().toString().trim());
	            ps.dispose();
	        }
	    }
	    
	    /**
	     * test the constraints for the initstate of the pageflow
	     */
	    @Test
	    public void testInitStateConstraints() throws Exception {
	        // create initState
	    	IElementType initstate = PageflowElementTypes.InitState_1001;
	    	 ShapeEditPart isep = createShapeUsingTool(initstate,
	                new Point(400, 300), getDiagramEditPart());
	    	// Select the initstate.
	        EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
	        rootViewer.deselectAll();
	        rootViewer.select(isep);
	        flushEventQueue();
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof InitStatePropertySection);
	        InitStatePropertySection is = (InitStatePropertySection) section;
	        String errorMsg = "| InitState<<InitState>> must have one out transition |";
	        // check for error messages
	        Assert.assertEquals(errorMsg, is.getErrorMsg().toString().trim());
	        // check for warning messages
	        Assert.assertEquals("|", is.getWarnMsg().toString().trim());
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
	        rootViewer.select(esep);

	        flushEventQueue();
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
	        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
	        is.refresh();
	        String errorMsg = "| EndState<<EndState>> must have at least one incoming transition |";
	        // check for error messages
	        Assert.assertEquals(errorMsg, is.getErrorMsg().toString().trim());
	        // check for warning messages
	        Assert.assertEquals("|", is.getWarnMsg().toString().trim());
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
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
	        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
	        is.refresh();
	        String errMsg = "| The feature 'Name' of <<Abort>> must be changed as it matches a reserved value | EndState<<Abort>> must have at least one incoming transition |";
	               // check for warning messages
	        Assert.assertEquals(errMsg, is.getErrorMsg().toString().trim());
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
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
	        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
	        is.refresh();
	        String errMsg = "| The feature 'Name' of <<Initial>> must be changed as it matches a reserved value | EndState<<Initial>> must have at least one incoming transition |";
	               // check for warning messages
	        Assert.assertEquals(errMsg, is.getErrorMsg().toString().trim());
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
	        ISection section = getCurrentTab().getSectionAtIndex(0);
	        Assert.assertTrue(section instanceof EndStateGeneralPropertySection);
	        EndStateGeneralPropertySection is = (EndStateGeneralPropertySection) section;
	        is.refresh();
	        String errMsg = "| The feature 'Name' of <<Error>> must be changed as it matches a reserved value | EndState<<Error>> must have at least one incoming transition |";
	               // check for warning messages
	        Assert.assertEquals(errMsg, is.getErrorMsg().toString().trim());
	    }
	    
}
