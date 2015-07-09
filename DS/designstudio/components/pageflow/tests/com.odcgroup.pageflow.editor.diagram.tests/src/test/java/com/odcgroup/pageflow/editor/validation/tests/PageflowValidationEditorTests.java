package com.odcgroup.pageflow.editor.validation.tests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.pageflow.editor.diagram.custom.properties.section.InitStatePropertySection;
import com.odcgroup.pageflow.editor.diagram.custom.properties.section.PageflowGeneralPropertySection;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.tests.setup.AbstractPageflowTechnicalValidationTests;

/**
 * @author pkk
 * 
 */
public class PageflowValidationEditorTests extends AbstractPageflowTechnicalValidationTests {

	private static final String PAGEFLOW_NAME = "myPageflow";

	/**
	 * @param name
	 */
	public PageflowValidationEditorTests() {
		super(PAGEFLOW_NAME);
	}

	/**
	 * test the constraints for the pageflow root element
	 */
    @Test
	public void testPageflowConstraints() throws Exception {
		ISection section = getCurrentTab().getSectionAtIndex(0);
		Assert.assertTrue(section instanceof PageflowGeneralPropertySection);

        String[] validationMsgs = validateModel(getDiagramEditPart());

		PageflowGeneralPropertySection ps = (PageflowGeneralPropertySection) section;
        
		String errorMsg = "| InitState is not specified for pageflow<<" + PAGEFLOW_NAME + ">> | "
				+ "EndState is not specified for pageflow<<" + PAGEFLOW_NAME + ">> |";

		// check for error messages
		Assert.assertEquals(errorMsg, validationMsgs[0].trim());
		// check for warning messages
		Assert.assertEquals("| The feature 'Name' of <<" + PAGEFLOW_NAME + ">> must start with an Upper Case character |", 
				validationMsgs[1].trim());

		ps.dispose();
	}

	/**
	 * @throws Exception
	 */
    @Test
	public void testPageflowConstraintsWithInitState() throws Exception {
		// create initState
		IElementType initstate = PageflowElementTypes.InitState_1001;
		ShapeEditPart vsep = createShapeUsingTool(initstate, new Point(50, 100), getDiagramEditPart());

		EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
		rootViewer.select(vsep);
		flushEventQueue();
		
        String[] validationMsgs = validateModel(getDiagramEditPart());
		
		rootViewer.select(getDiagramEditPart());
		flushEventQueue();
		
		ISection section = getCurrentTab().getSectionAtIndex(0);
		Assert.assertTrue(section instanceof PageflowGeneralPropertySection);

		PageflowGeneralPropertySection ps = (PageflowGeneralPropertySection) section;
		ps.refresh();
		
		String errorMsg = "| EndState is not specified for pageflow<<" + PAGEFLOW_NAME + ">> |";
		// check for error messages
		Assert.assertEquals(errorMsg, validationMsgs[0].toString().trim());
		// check for warning messages
		Assert.assertEquals("| The feature 'Name' of <<" + PAGEFLOW_NAME + ">> must start with an Upper Case character |", validationMsgs[1].toString().trim());
		getCommandStack().undo();
		ps.dispose();
	}

	/**
	 * @throws Exception
	 */
    @Test
	public void testPageflowConstraintsWithEndState() throws Exception {
		// create initState
		IElementType endState = PageflowElementTypes.EndState_1003;
		ShapeEditPart esep = createShapeUsingTool(endState, new Point(300, 100), getDiagramEditPart());

		EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
		rootViewer.select(esep);
		flushEventQueue();
		

        String[] validationMsgs = validateModel(getDiagramEditPart());
		
		rootViewer.select(getDiagramEditPart());
		flushEventQueue();

		ISection section = getCurrentTab().getSectionAtIndex(0);
		Assert.assertTrue(section instanceof PageflowGeneralPropertySection);

		PageflowGeneralPropertySection ps = (PageflowGeneralPropertySection) section;
		ps.refresh();
		String errorMsg = "| InitState is not specified for pageflow<<" + PAGEFLOW_NAME + ">> |";
		// check for error messages
		Assert.assertEquals(errorMsg, validationMsgs[0].trim());
		// check for warning messages
		Assert.assertEquals("| The feature 'Name' of <<" + PAGEFLOW_NAME + ">> must start with an Upper Case character |", validationMsgs[1].trim());
		getCommandStack().undo();
		ps.dispose();
	}

	/**
	 * test the constraints for the initstate of the pageflow
	 */
    @Test
	public void testInitStateConstraints() throws Exception {
		// create initState
		IElementType initstate = PageflowElementTypes.InitState_1001;
		ShapeEditPart isep = createShapeUsingTool(initstate, new Point(400, 300), getDiagramEditPart());
		// Select the initstate.
		EditPartViewer rootViewer = getDiagramEditPart().getRoot().getViewer();
		rootViewer.deselectAll();
		rootViewer.select(isep);
		flushEventQueue();

        String[] validationMsgs = validateModel(isep);
        
		ISection section = getCurrentTab().getSectionAtIndex(0);
		Assert.assertTrue(section instanceof InitStatePropertySection);
		InitStatePropertySection is = (InitStatePropertySection) section;
		is.refresh();
		
		String errorMsg = "InitState<<InitState>> must have one out transition";
		// check for error messages
		Assert.assertEquals(errorMsg, validationMsgs[0].toString().trim());
		// check for warning messages
		Assert.assertEquals("|", validationMsgs[1].toString().trim());
	}

}
