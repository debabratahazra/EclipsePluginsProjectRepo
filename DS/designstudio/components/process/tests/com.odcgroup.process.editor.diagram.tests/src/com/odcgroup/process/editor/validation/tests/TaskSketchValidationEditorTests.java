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

import com.odcgroup.process.diagram.custom.properties.sections.ServiceTaskGeneralPropertySection;
import com.odcgroup.process.diagram.custom.properties.sections.UserTaskGeneralPropertySection;
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
public class TaskSketchValidationEditorTests extends AbstractProcessSketchValidationTests {

    private static final ProcessSketchValidationTestSetup PROCESS_SKETCH_VALIDATION_TEST_SETUP = new ProcessSketchValidationTestSetup();
	private static final String PROCESS_NAME = "myTask";
    
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
     * test the constraints for the usertask in pool element
     */
	@Test
	public void testUserTaskConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType userTask = ProcessElementTypes.UserTask_2001;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart uShape = createShapeUsingTool(userTask, new Point(100,100), poolEdit);
         
         if (uShape.getModel() instanceof Node) {
             Node node = (Node) uShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "UserTask");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "UserTask");
         }
                  
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(uShape);
         flushEventQueue();
         String[] msgs = validateModel(uShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         UserTaskGeneralPropertySection is = (UserTaskGeneralPropertySection) section;
         is.refresh();
         
         String errorMsg = "| 'Rule Name/Pageflow URI' is required for the UserTask<<UserTask>> " +
         		"| process::UserTask<<UserTask>> should contain atleast one incoming flow " +
         		"| process::UserTask<<UserTask>> should contain atleast one outgoing flow |";
         // check for error messages
         Assert.assertEquals("Error ", errorMsg, msgs[0].trim());
         rootViewer.deselectAll();        
    }
    
    /**
     * test the constraints for the ServiceTask in pool element
     */

	@Test
	public void testServiceTaskConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType serviceTask = ProcessElementTypes.ServiceTask_2002;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart sShape = createShapeUsingTool(serviceTask, new Point(100,100), poolEdit);
         
         if (sShape.getModel() instanceof Node) {
             Node node = (Node) sShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "ServiceTask");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "ServiceTask");
         }
                  
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(sShape);
         flushEventQueue();
         String[] msgs = validateModel(sShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         ServiceTaskGeneralPropertySection is = (ServiceTaskGeneralPropertySection) section;
         is.refresh();
         String errorMsg = "| process::ServiceTask<<ServiceTask>> should contain atleast one incoming flow " +
         		"| The feature 'Service Name' is required for the ServiceTask<<ServiceTask>> " +
         		"| process::ServiceTask<<ServiceTask>> should contain atleast one outgoing flow |";
         // check for error messages
         Assert.assertEquals("Error ", errorMsg, msgs[0].trim());
         rootViewer.deselectAll();        
    }
}
