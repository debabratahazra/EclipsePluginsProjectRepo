package com.odcgroup.process.editor.validation.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.diagram.custom.properties.sections.ProcessGeneralSection;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.editor.tests.setup.AbstractProcessSketchValidationTests;
import com.odcgroup.process.editor.tests.setup.ProcessSketchValidationTestSetup;

/**
 * @author pkk
 * @author mka
 *
 */
public class ProcessSketchValidationEditorTests extends AbstractProcessSketchValidationTests {

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
     * test the constraints for the process root element
     */
	@Test
	public void testProcessConstraints() throws Exception {
    	ISection section = getCurrentTab().getSectionAtIndex(0);
        Assert.assertTrue(section instanceof ProcessGeneralSection);
        ProcessGeneralSection section2 = (ProcessGeneralSection) section;
        section2.getWidgetFactory();

        String[] msgs = validateModel(getDiagramEditPart());
        if (section instanceof ProcessGeneralSection) {
        	ProcessGeneralSection ps = (ProcessGeneralSection) section;
            String errorMsg = "At least one Pool should be specified!" ;
            		
            // check for error messages
            Assert.assertEquals(errorMsg, msgs[0].trim());
            IElementType pool = ProcessElementTypes.Pool_1001;   
            createShapeUsingTool(pool, new Point(100,100), getDiagramEditPart());
            ps.refresh();
            //errorMsg = "|";
            // check for error messages
            Assert.assertEquals(errorMsg, msgs[0].toString().trim());         
            ps.dispose();
        }
    }   
}
