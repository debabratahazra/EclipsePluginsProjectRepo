package com.odcgroup.page.ui.util.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.action.PasteWidgetAction;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class PasteWidgetActionTest extends AbstractDSUnitTest{

	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}

	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	/* 
	 * check if the widget can be paste into a module.
	 */
	@Test
	public void testDS4682CheckWidgetPasteActionEnabled(){
		copyResourceInModelsProject("fragment/ds4682/DS4682XtoolttipFragment.fragment");
		copyResourceInModelsProject("fragment/widgets/searchField/DS3774.fragment");
	  //get the search filed widget
		 Model searchmodel = null;
			URI uri = URI.createURI("resource:///widgets/searchField/DS3774.fragment");
			try {
				IOfsModelResource ofsResource = getOfsProject()
						.getOfsModelResource(uri);
				searchmodel = (Model) ofsResource.getEMFModel().get(0);
			
			} catch (Exception ex) {
				ex.printStackTrace();
				Assert.fail("Cannot load the model from theresource:///widgets/searchField/DS3774.fragment");
			}
			
		 
		 Widget serachParentFrg=searchmodel.getWidget();
		 Assert.assertNotNull(serachParentFrg);
		 Widget box=serachParentFrg.getContents().get(0);
		final Widget searchFieldwidget=box.getContents().get(0);
		 Assert.assertNotNull(searchFieldwidget);
		 //get the selected parent.
		 Model model = null;
			URI uri1 = URI.createURI("resource:///ds4682/DS4682XtoolttipFragment.fragment");
			try {
				IOfsModelResource ofsResource = getOfsProject()
						.getOfsModelResource(uri1);
				model = (Model) ofsResource.getEMFModel().get(0);
			} catch (Exception ex) {
				ex.printStackTrace();
				Assert.fail("Cannot load the model from the resource:///ds4682/DS4682XtoolttipFragment.fragment");
			}
		
		Widget fragment=model.getWidget();
		Assert.assertNotNull(fragment);
		Widget xtooltip=fragment.getContents().get(0);
		Assert.assertNotNull(xtooltip);
		Widget grid=xtooltip.getContents().get(0);
	    Assert.assertNotNull(grid);
	    Widget cell1=grid.getContents().get(1);
	    //create the paste action
	    List<WidgetEditPart> clipContent=new ArrayList<WidgetEditPart>();
	    
	    WidgetEditPart part=  new WidgetEditPart(){

		
		public Widget getWidget() {
			return searchFieldwidget;
		}
		   
	   };
        clipContent.add(part); 
	    Clipboard.getDefault().setContents(clipContent);
	    TestPasteWidgetAction pasteAction=new TestPasteWidgetAction(null);
	    pasteAction.setSelection(new StructuredSelection(cell1));
	    boolean isallowed=pasteAction.calculateEnabled();
	    Assert.assertFalse(isallowed);
	    
		
		
	}
	
	/* to test paset action extend the pastewidget action.
	 * override to get the selection.
	 * @author snn
	 *
	 */
	class TestPasteWidgetAction extends PasteWidgetAction{

		public TestPasteWidgetAction(IWorkbenchPart part) {
			super(part);
			// TODO Auto-generated constructor stub
		}
		
		protected void setSelection(ISelection selection) {
			// TODO Auto-generated method stub
			super.setSelection(selection);
		}
		
		@Override
		public boolean calculateEnabled() {
			// TODO Auto-generated method stub
			return super.calculateEnabled();
		}
	}

}
