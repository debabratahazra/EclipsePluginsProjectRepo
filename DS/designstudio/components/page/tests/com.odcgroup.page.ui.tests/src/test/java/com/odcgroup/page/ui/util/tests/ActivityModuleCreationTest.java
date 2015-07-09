package com.odcgroup.page.ui.util.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PartInitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ModelUtils;
import com.odcgroup.page.ui.wizard.ModelSpecification;
import com.odcgroup.page.ui.wizard.ModelType;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 * @author phanikumark
 *
 */
public class ActivityModuleCreationTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds6073/DS6073.domain";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL);
		closeWelcomeScreen(); // Otherwise the editor won't get displayed
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testDS6073ActivityModuleCreation() throws PartInitException {
		String location = "module/Default/module/";
		String name = "TestModule";
		IOfsProject ofsProject = getOfsProject();
		try {
			mkdirs(ofsProject.getProject().getFolder(location));
		} catch (CoreException e) {
			throw new RuntimeException("Unable to create the folders", e);
		}
		IFile targetFile = ofsProject.getProject().getFile(location+name+"."+ModelType.MODULE.getTypeName());
		if (targetFile != null) {
	        try {
				targetFile.create(null, true, null);
			} catch (Exception e) {
				throw new RuntimeException("Unable to create the test project because " +
						"a project occurs during the copy of the " +
						"resource to the " + ofsProject + " models project", e);
			}
			ModelSpecification spec = new ModelSpecification(ModelType.MODULE, targetFile.getParent().getFullPath());
			spec.setDomainName(MdfNameFactory.createMdfName("DS6073:DS6073DS"));
			Model model = spec.createModel();
			ModelUtils.saveModel(model, targetFile);
			Widget widget = model.getWidget();
			String beandefine = widget.getPropertyValue("bean-define");
			assertTrue("DSActivityBean.DS6073.DS6073DS".equals(beandefine));
		}	
		
	}
}
