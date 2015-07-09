package com.odcgroup.service.ui.editor.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

public class TextEditorTest extends AbstractDSUIUnitTest {
	
	private static final String C_FILE = "test/DS5175/DS5175.c";
	private static final String H_FILE = "test/DS5175/DS5175.h";
	private static final String CPP_FILE = "test/DS5175/DS5175.cpp";
	private static final String CS_FILE = "test/DS5175/DS5175.cs";
	private static final String SVC_FILE = "test/DS5175/DS5175.svc";
	private static final String CONFIG_FILE = "test/DS5175/DS5175.config";
	private static final String B_FILE = "test/DS5175/DS5175.b";
	
	@Test
	public void testDS5175() {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("someProject");
			project.create(null);
			project.open(null);
			new OfsProjectInitializer().updateConfiguration(project, null);
			IOfsProject proj = OfsCore.getOfsProject(project);
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
	        ofsProjects.put("someProject", ofsProject);
			copyResourceInModelsProject(proj, C_FILE, H_FILE, CPP_FILE, CS_FILE, SVC_FILE, CONFIG_FILE, B_FILE);
			
			IFile tempFile = getProject("someProject").getFile(C_FILE);
			IEditorPart part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			
			tempFile = getProject("someProject").getFile(B_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
			
			tempFile = getProject("someProject").getFile(CPP_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
			
			tempFile = getProject("someProject").getFile(CONFIG_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
			
			tempFile = getProject("someProject").getFile(CS_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
			
			tempFile = getProject("someProject").getFile(SVC_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
			
			tempFile = getProject("someProject").getFile(H_FILE);
			part = openDefaultEditor(tempFile);
			Assert.assertNotNull(part);
			part = null;
			tempFile = null;
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
