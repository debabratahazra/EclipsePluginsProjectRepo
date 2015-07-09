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
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.editor.tests.setup.AbstractProcessSketchValidationTests;
import com.odcgroup.process.editor.tests.setup.ProcessSketchValidationTestSetup;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @author pkk
 * @author mka
 *
 */
public class PoolSketchValidationEditorTests extends AbstractProcessSketchValidationTests {

    private static final ProcessSketchValidationTestSetup PROCESS_SKETCH_VALIDATION_TEST_SETUP = new ProcessSketchValidationTestSetup();
	private static final String PROCESS_NAME = "myProcess";
    
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
     * test the constraints for the pool root element
     */
	@Test
	public void testPoolConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         
         if (pshape.getModel() instanceof Node) {
             Node node = (Node) pshape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "poolName");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "poolId");
         }
         // Select the decisionstate.
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(pshape);

         flushEventQueue();
         String[] msgs = validateModel(pshape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         Assert.assertTrue(section instanceof NamedElementPropertySection);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();
        
         String errorMsg = "Participant Information is required for the Pool<<poolName>>";
         // check for error messages
         Assert.assertEquals("Error ", errorMsg, msgs[0].trim());
        rootViewer.deselectAll();
        pshape.deactivate();
    }
}
