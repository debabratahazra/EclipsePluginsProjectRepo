package com.odcgroup.page.ui.properties.sections.tests;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetCopier;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.ui.properties.sections.EventSection;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

public class EventSectionTest extends AbstractDSUIUnitTest {
  
	private IEditorPart pageEditorpart ;
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS3553.module");
		IFile file = getDefaultModuleFolder().getFile("DS3553.module");
		 pageEditorpart = openEditor(file,
				"com.odcgroup.page.ui.editor.pageDesignerEditorModule");
		 pageEditorpart.setFocus();
		

	}

	@After
	public void tearDown() throws Exception {
		//close the editor
//	    pageEditorpart.doSave(new NullProgressMonitor());
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(true);
		deleteModelsProjects();
	}

    /*
     * test the paste action in the event table.
     */
	@Test @Ignore
	public void testhandlePasteEvent() {
		TestEventSection section = new TestEventSection();
		section.createUI();
		MetaModel mm = MetaModelRegistry.getMetaModel();
		WidgetType widgetType = mm.findWidgetType("xgui", "Button");
		WidgetFactory factory = new WidgetFactory();
		Widget button1 = factory.create(widgetType);
		button1.setID("button1");
		Widget button2 = factory.create(widgetType);
		button2.setID("button2");
		Widget button3 = factory.create(widgetType);
		button3.setID("button3");
		Event event = ModelFactory.eINSTANCE.createEvent();
		event.setEventName("OnClick");
		event.setFunctionName("submit");
		//copy the event 
		Clipboard.getDefault().setContents(WidgetCopier.copy(event));
		section.setWidget(button1);
	    //paste the event in the button1	
        section.handlePasteEvent();
		int size = button1.getEvents().size();
		Assert.assertEquals(1, size);
	    //paste the event in the butto2.
		section.setWidget(button2);
		section.handlePasteEvent();
		//paste the event in the button3.
		section.setWidget(button3);
		section.handlePasteEvent();
		//after  paste check  the  events in button2 
		Assert.assertEquals(1, button2.getEvents().size());
		//after paste check the event in button3.
		Assert.assertEquals(1, button3.getEvents().size());
		
		
      
	}
	
    /**
     * EventSection class 
     * @author snn
     *
     */
	class TestEventSection extends EventSection {

		public IWorkbenchPart getPart() {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor();
		}

		public TestEventSection() {
		}

		public void handlePasteEvent() {
			super.handlePasteEvent();
		}

		public void setWidget(Widget widget) {
			super.widget = widget;
		}
		
       /*
        * create ui .table and buttons
        */
		public void createUI() {

			Composite body = new Composite(new Shell(), SWT.NONE);
			Table eventsTbl = new Table(body, SWT.MULTI | SWT.FULL_SELECTION);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.grabExcessHorizontalSpace = true;
			eventsTbl.setLayoutData(gridData);
			eventsTbl.setHeaderVisible(true);
			super.eventsTbl = eventsTbl;
			Button create = new Button(body, SWT.NONE);
			Button modify = new Button(body, SWT.NONE);
			Button delete = new Button(body, SWT.NONE);
			super.createBtn = create;
			super.deleteBtn = delete;
			super.modifyBtn = modify;
		}

	}

}
