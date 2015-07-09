package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;

/**
 * @author pkk
 * test DS-4405
 */
public class DomainAssociationIncludeConstraintTest extends
		AbstractPageDesignerUnitTest {
	
	private static final String DOMAIN_MODEL = "domain/ds4405/DS4405.domain";
	private static final String DOM_FRAGMENT = "fragment/ds4405/TestDomainFragment.fragment";
	private static final String DEF_FRAGMENT = "fragment/ds4405/MoneyIdentifier.fragment";
	private static final String OTHER_FRAGMENT = "fragment/ds4405/OtherFragment.fragment";

	@Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, DOM_FRAGMENT, DEF_FRAGMENT, OTHER_FRAGMENT);
        closeWelcomeScreen(); // Otherwise the editor won't get displayed
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }
	
	@Test
	public void testDS4405IncludeDomainAssociationIncludeConstraint() throws PartInitException {
		IFile fragfile = getProject().getFile(DOM_FRAGMENT);
		Assert.assertTrue(fragfile.exists());
		IEditorPart ep = openDefaultEditor(fragfile);
		if (ep instanceof MultiPageEditorPart) {
			MultiPageEditorPart mep = (MultiPageEditorPart) ep;
			DesignEditor editor = (DesignEditor) mep.getSelectedPage();
			RootEditPart rep = editor.getViewer().getRootEditPart();
			List<?> list = rep.getChildren();
			if (!list.isEmpty()) {
				WidgetEditPart wep = (WidgetEditPart) list.get(0);
				Assert.assertNotNull("Fragment WidgetEditPart is not found", wep);
				List<?> children = wep.getChildren();
				Assert.assertFalse("Fragment Widget has no children", children.isEmpty());
				WidgetEditPart bep = (WidgetEditPart) children.get(0);
				Assert.assertFalse("Box Widget has no children", bep.getChildren().isEmpty());
				WidgetEditPart inclep = (WidgetEditPart) bep.getChildren().get(1);
		        
				rep.getViewer().select(inclep);
				
				String expectedErrorMsg = "Included Fragment must be based " +
						"on linked dataset in the Domain Association";
				IStatus status = validateModel(inclep.getModel());
				Assert.assertTrue(!status.isOK());
				Assert.assertEquals(expectedErrorMsg, status.getMessage());
				
			}
		}
	}

}
