package com.odcgroup.process.editor.validation.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.diagram.custom.properties.sections.NamedElementPropertySection;
import com.odcgroup.process.diagram.custom.properties.sections.StartEventPropertySection;
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.editor.tests.setup.AbstractProcessSketchValidationTests;
import com.odcgroup.process.editor.tests.setup.ProcessSketchValidationTestSetup;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @author pkk
 * @author mka
 *
 */
public class EventSketchValidationEditorTests extends AbstractProcessSketchValidationTests {

    private static final ProcessSketchValidationTestSetup PROCESS_SKETCH_VALIDATION_TEST_SETUP = new ProcessSketchValidationTestSetup();
	private static final String PROCESS_NAME = "myEvent";
    
    @Before
    public void setUp() throws Exception {
    	PROCESS_SKETCH_VALIDATION_TEST_SETUP.setUp();
    	setProcessName(PROCESS_NAME);
    	super.setUp();
    }    
   
    @After
    public void tearDown() throws Exception {
    	super.tearDown();
    	PROCESS_SKETCH_VALIDATION_TEST_SETUP.tearDown();
    }

    /**
     * test the constraints for the StartEvent in pool element
     */
	@Test
	public void testStartEventConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType startEvent = ProcessElementTypes.StartEvent_2007;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart uShape = createShapeUsingTool(startEvent, new Point(100,100), poolEdit);
         
         if (uShape.getModel() instanceof Node) {
             Node node = (Node) uShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "startEvent");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "startEvent");
         }
                 
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(uShape);
         flushEventQueue();
         String[] msgs = validateModel(uShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         StartEventPropertySection is = (StartEventPropertySection) section;
         is.refresh();
         String errorMsg = "StartEvent<<startEvent>> should contain only one outgoing flow";
         // check for error messages
         Assert.assertEquals("Error ", errorMsg, msgs[0].trim());
         rootViewer.deselectAll();        
    }
    
    /**
     * test the constraints for the endEvent in pool element
     */

	@Test
	public void testEndEventConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType endEvent = ProcessElementTypes.EndEvent_2008;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart eShape = createShapeUsingTool(endEvent, new Point(100,100), poolEdit);
         
         if (eShape.getModel() instanceof Node) {
             Node node = (Node) eShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "endEvent");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "endEvent");
         }
        
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(eShape);
         flushEventQueue();
         String[] msgs = validateModel(eShape);
         
         ISection section = getCurrentTab().getSectionAtIndex(0);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();
         String errorMsg = "EndEvent<<endEvent>> should contain atleast one incoming flow";
         // check for error messages
         Assert.assertEquals("Error ", errorMsg, msgs[0].trim());
         rootViewer.deselectAll();        
    }
}
