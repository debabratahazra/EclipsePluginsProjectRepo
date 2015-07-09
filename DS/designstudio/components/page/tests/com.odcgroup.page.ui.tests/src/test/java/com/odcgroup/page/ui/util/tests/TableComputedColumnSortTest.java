package com.odcgroup.page.ui.util.tests;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.figure.table.TableFigure;
import com.odcgroup.page.ui.tests.util.PageUiAssert;

/**
 * 
 * @author pkk
 *
 */
public class TableComputedColumnSortTest extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/DS4287/DS4287.domain";
	private static final String MODULE_MODEL = "module/widget/tabletree/DS4287.module";
	
    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
        closeWelcomeScreen(); // Otherwise the editor won't get displayed
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }


	/**
	 * @throws PartInitException
	 */
	@Test
	public void testDS3466TableColumnHAlignmentProperty() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL);
		Assert.assertTrue(moduleFile.exists());
		IEditorPart ep = openDefaultEditor(moduleFile);
		PageUiAssert.assertInstanceOfMultiPageEditorPart(ep);
		MultiPageEditorPart mep = (MultiPageEditorPart) ep;
		DesignEditor editor = (DesignEditor) mep.getSelectedPage();
		RootEditPart rep = editor.getViewer().getRootEditPart();
		List<?> list = rep.getChildren();
		if (!list.isEmpty()) {
			WidgetEditPart wep = (WidgetEditPart) list.get(0);
			Assert.assertNotNull("Module WidgetEditPart is not found", wep);
			List<?> children = wep.getChildren();
			Assert.assertFalse("Module Widget has no children", children.isEmpty());
			WidgetEditPart bep = (WidgetEditPart) children.get(0);
			Assert.assertFalse("Box Widget has no children", bep.getChildren().isEmpty());
			WidgetEditPart tableep = (WidgetEditPart) bep.getChildren().get(0);
			IFigure mfig = tableep.getFigure();
			Assert.assertTrue(mfig instanceof TableFigure);
			
			List<?> tableChildren = tableep.getChildren();
			Assert.assertFalse("Table Widget has no columns", tableChildren.isEmpty());
			Assert.assertEquals("Table columns is not equal to 2", 2, tableChildren.size());
			WidgetEditPart tcep = (WidgetEditPart) tableChildren.get(1);
			ITableColumn compcolumn = TableHelper.getTableColumn(tcep.getWidget());
			String colname = compcolumn.getColumnName();
			Assert.assertEquals("comp_L3D0w", colname);

			Document document = fetchGenDocument(getOfsProject(), wep.getWidget());
			NodeList nodes = document.getElementsByTagName("udp:keep");
			Assert.assertTrue(nodes.getLength() > 0);
			Node keep = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				keep = nodes.item(i);
				Assert.assertFalse("<udp:keep> element found for computed column sort ["+colname+"]", colname.equals(keep.getTextContent()));					
			}
		}
	}
}
