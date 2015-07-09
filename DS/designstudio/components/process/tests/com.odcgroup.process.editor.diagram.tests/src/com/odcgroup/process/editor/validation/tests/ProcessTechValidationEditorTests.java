package com.odcgroup.process.editor.validation.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.diagram.custom.properties.sections.ProcessGeneralSection;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.editor.tests.setup.AbstractProcessTechValidationTests;
import com.odcgroup.process.editor.tests.setup.ProcessTechnicalValidationTestSetup;

/**
 * @author pkk
 * @author mka
 *
 */
public class ProcessTechValidationEditorTests extends AbstractProcessTechValidationTests {
	
    private static final ProcessTechnicalValidationTestSetup PROCESS_TECHNICAL_VALIDATION_TEST_SETUP = new ProcessTechnicalValidationTestSetup();
	private static final String PROCESS_NAME = "myTechProcess";
    
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
     * test the constraints for the process root element
     */
	@Test
	public void testProcessConstraints() throws Exception {
    	ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ProcessGeneralSection);
    	ProcessGeneralSection ps = (ProcessGeneralSection) section;

        String[] msgs = validateModel(getDiagramEditPart());
        String errorMsg = "At least one Pool should be specified!" ;        		
        // check for error messages
        Assert.assertEquals(errorMsg, msgs[0].trim());              
        ps.dispose();
    
    }   
	
	/**
	 * test the constraints on the process, after adding a pool
	 * @throws Exception
	 */
	@Test
	public void testProcessConstraintsAfterAddingPool() throws Exception {
		EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer(); 
		IElementType pool = ProcessElementTypes.Pool_1001;   
        ShapeEditPart pep = createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
        rootViewer.select(pep);
        flushEventQueue();
        
        rootViewer.select(getDiagramEditPart());
        flushEventQueue();
        String[] msgs = validateModel(getDiagramEditPart());
        
        ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ProcessGeneralSection);
    	ProcessGeneralSection ps = (ProcessGeneralSection) section; 
    	String errorMsg = "|";
        // check for error messages
        Assert.assertEquals(errorMsg, msgs[0].trim()); 
        ps.dispose();
	}
}
