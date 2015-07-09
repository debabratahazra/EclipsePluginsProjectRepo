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
import com.odcgroup.process.diagram.edit.parts.PoolProcessItemCompartmentEditPart;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.editor.tests.setup.AbstractProcessTechValidationTests;
import com.odcgroup.process.editor.tests.setup.ProcessTechnicalValidationTestSetup;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @author pkk
 * @author mka
 *
 */
public class GatewayTechValidationEditorTests extends AbstractProcessTechValidationTests {
	
    private static final ProcessTechnicalValidationTestSetup PROCESS_TECHNICAL_VALIDATION_TEST_SETUP = new ProcessTechnicalValidationTestSetup();
	private static final String PROCESS_NAME = "myTechGateway";
    
    @Before
    public void setUp() throws Exception {
    	PROCESS_TECHNICAL_VALIDATION_TEST_SETUP.setUp();
    	setProcessName(PROCESS_NAME);
    	super.setUp();
    }    
    
    @After
    public void tearDown() throws Exception {
    	super.tearDown();
    	PROCESS_TECHNICAL_VALIDATION_TEST_SETUP.tearDown();
    }
   
    /**
     * test the constraints for the ComplexGateway in pool element
     */
	@Test
	public void testComplexGatewayConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType complexGateway = ProcessElementTypes.ComplexGateway_2003;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart uShape = createShapeUsingTool(complexGateway, new Point(100,100), poolEdit);
         
         if (uShape.getModel() instanceof Node) {
             Node node = (Node) uShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "complexGateway");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "complexGateway");
         }
                 
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(uShape);
         flushEventQueue();
         String[] msgs = validateModel(uShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();
         String errorMsg = "| 'Rule Name/Script Language/Service' is required for the ComplexGateway<<complexGateway>> ";
         String errorMsg2 =	"| ComplexGateway<<complexGateway>> should contain atleast one incoming and atleast one outgoing flow |";
         // check for error messages
         String actual = msgs[0];
         Assert.assertTrue("Error ", actual.contains(errorMsg));
         Assert.assertTrue("Error ", actual.contains(errorMsg2));
         rootViewer.deselectAll();        
    }
    
    /**
     * test the constraints for the ExclusiveMerge in pool element
     */
	@Test
	public void testExclusiveMergeConstraints() throws Exception {
    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType exclusiveMerge = ProcessElementTypes.ExclusiveMerge_2004;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart eShape = createShapeUsingTool(exclusiveMerge, new Point(200,100), poolEdit);
         if (eShape.getModel() instanceof Node) {
             Node node = (Node) eShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "ExclusiveMerge");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "ExclusiveMerge");
         }
         
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(eShape);
         flushEventQueue();
         String[] msgs = validateModel(eShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();
         String errorMsg = "| process::ExclusiveMerge<<ExclusiveMerge>> should contain only one outgoing flow ";
         String errorMsg2 =	"| ExclusiveMerge<<ExclusiveMerge>> should contain atleast one incoming flow |";
         // check for error messages
         String actual = msgs[0];
         Assert.assertTrue("Error ", actual.contains(errorMsg));
         Assert.assertTrue("Error ", actual.contains(errorMsg2));
         rootViewer.deselectAll();        
    }
    
    /**
     * test the constraints for the ParallelFork in pool element  
     */
	@Test
	public void testParallelForkConstraints() throws Exception {

    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType parallelFork = ProcessElementTypes.ParallelFork_2005;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart eShape = createShapeUsingTool(parallelFork, new Point(100,100), poolEdit);
         if (eShape.getModel() instanceof Node) {
             Node node = (Node) eShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "ParallelFork");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "ParallelFork");
         }
           
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(eShape);
         flushEventQueue();
         String[] msgs = validateModel(eShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();

         // check for error messages
         Assert.assertTrue("Error missing", msgs[0].contains("ParallelFork<<ParallelFork>> should contain atleast one outgoing flow"));
         Assert.assertTrue("Error missing", msgs[0].contains("ParallelFork<<ParallelFork>> should contain only one incoming flow"));

         rootViewer.deselectAll();        
    }
    
    /**
     * test the constraints for the ParallelMerge in pool element  
     */
	@Test
	public void testParallelMergeConstraints() throws Exception {

    	 IElementType pool = ProcessElementTypes.Pool_1001;   
    	 IElementType parallelMerge = ProcessElementTypes.ParallelMerge_2006;
         ShapeEditPart pshape = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
         PoolProcessItemCompartmentEditPart poolEdit = (PoolProcessItemCompartmentEditPart)pshape.getChildren().get(1);
         ShapeEditPart eShape = createShapeUsingTool(parallelMerge, new Point(100,100), poolEdit);
         if (eShape.getModel() instanceof Node) {
             Node node = (Node) eShape.getModel();
             EObject obj = node.getElement();   
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_Name(),
             "ParallelMerge");
             setProperty(obj, ProcessPackage.eINSTANCE.getNamedElement_ID(),
             "ParallelMerge");
         }  
         EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
         rootViewer.deselectAll();
         rootViewer.select(eShape);
         flushEventQueue();
         String[] msgs = validateModel(eShape);
         ISection section = getCurrentTab().getSectionAtIndex(0);
         NamedElementPropertySection is = (NamedElementPropertySection) section;
         is.refresh();
         String errorMsg = "| process::ParallelMerge<<ParallelMerge>> should contain only one outgoing flow ";
         String errorMsg2 =	"| ParallelMerge<<ParallelMerge>> should contain atleast one incoming flow |";
         // check for error messages
         String actual = msgs[0];
         Assert.assertTrue("Error ", actual.contains(errorMsg));
         Assert.assertTrue("Error ", actual.contains(errorMsg2));
         rootViewer.deselectAll();        
    }
}
