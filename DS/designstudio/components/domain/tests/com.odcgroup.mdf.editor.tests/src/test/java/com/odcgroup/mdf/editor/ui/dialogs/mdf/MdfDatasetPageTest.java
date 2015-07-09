package com.odcgroup.mdf.editor.ui.dialogs.mdf;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.editor.ui.dialogs.ModelEditDialog;
import com.odcgroup.mdf.editor.ui.dialogs.doc.MdfDocPage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfDatasetPage.WorkingDataset;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.workbench.core.OfsCore;

public class MdfDatasetPageTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkingDataset_getBaseClass_DS3744() throws CoreException {
		// create a dummy project with the OfsNature.
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = { OfsCore.NATURE_ID };
		description.setNatureIds(natures);
		project.setDescription(description, null);
				
		// first, set up a domain with a dataset without a base class defined
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("TestDomain");
		final MdfDatasetImpl dataset = (MdfDatasetImpl) MdfFactory.eINSTANCE.createMdfDataset();
		dataset.setName("TestDataset");
		domain.getDatasets().add(dataset);
		
		// the use case requires the domain to be inside a resource set, so we create one
		ResourceSet rs = new ResourceSetImpl();
		String path = project.getFullPath().toString()+"domain/TestDomain.mml";
		URI uri = URI.createPlatformResourceURI(path, true);
		Resource resource = rs.createResource(uri);
		resource.getContents().add(domain);

		// now we create the dialog, which holds the class that needs to be 
		// fixed (WorkingDataset, it is an inner class)
		Shell shell = new Shell(Display.getCurrent());
		//DS-8063 adapt test to pass product conflict in build
		class TestModelEditDialog extends ModelEditDialog {
			public TestModelEditDialog(Shell parentShell, MdfModelElement model) {
				super(parentShell, model);
				super.setExplicitPage(new MdfDocPage(dataset));
			}
		}
		TestModelEditDialog dialog = new TestModelEditDialog(shell,dataset);
		dialog.create();
		MdfDatasetPage page = new MdfDatasetPage(dataset);
		page.setContainer(dialog);
		page.createControl(shell);
		WorkingDataset workingDataset = page.new WorkingDataset();
	
		// in DS3744, the method getBaseClass() now throws a NPE instead of returning null
		Assert.assertNull(workingDataset.getBaseClass());
	}
	
}
