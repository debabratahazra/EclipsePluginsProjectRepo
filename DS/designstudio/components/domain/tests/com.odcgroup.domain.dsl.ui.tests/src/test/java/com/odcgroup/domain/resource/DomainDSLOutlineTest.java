package com.odcgroup.domain.resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

@Ignore   // Domain DSL Editor is now removed.
public class DomainDSLOutlineTest extends AbstractDSUnitTest {
	

	private static String DOMAIN_MODEL = "domain/ds6073/DS6073.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL);
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testCollapseAll() {
		try {
			IFile file = getProject().getFile(DOMAIN_MODEL);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file),"com.odcgroup.domain.Domain");
			IViewPart viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("org.eclipse.ui.views.ContentOutline");
			IContributionItem items[] = viewPart.getViewSite().getActionBars().getToolBarManager().getItems();
			boolean status1 = false;
			boolean status2 = false;
			for (IContributionItem item : items) {
				if (item.getId().equals("ExpandAll")) {
					status1 = true;
					continue;
				}
				if (item.getId().equals("CollapseAll")) {
					status2 = true;
					continue;
				}
			}

			if (status1 && status2) {
				Assert.assertTrue("Actions are present", true);
			} else {
				Assert.assertTrue("Expand All and Collapse All Actions are not present", false);
			}
		} catch (PartInitException e) {
			System.out.println(e.getMessage());
		}
	}
}
