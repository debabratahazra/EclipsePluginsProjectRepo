package com.odcgroup.ocs.server.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.DSProjectUtil;

public class DSProjectUtilTest  {

	private static final String TEST_PROJECT_NAME = "test-project-name";
	private IProject mockProject;
	private IDSProject mockOcsProject;

	@Before
	public void setUp() throws Exception {
		mockProject = Mockito.mock(IProject.class);
		Mockito.when(mockProject.getName()).thenReturn(TEST_PROJECT_NAME);

		mockOcsProject = Mockito.mock(IDSProject.class);
		Mockito.when(mockOcsProject.getName()).thenReturn(TEST_PROJECT_NAME);
	}

	@Test
	public void testConvertToIProjectReturnsAValidIProject() throws Exception {

		IWorkspaceRoot mockWorkspaceRoot = Mockito.mock(IWorkspaceRoot.class);
		Mockito.when(mockWorkspaceRoot.getProject(TEST_PROJECT_NAME)).thenReturn(mockProject);

		IDSProject ocsProject = new DSProject(mockProject);

		IProject actualConvertedIProject = DSProjectUtil.convertToIProject(mockWorkspaceRoot, ocsProject);

		Assert.assertEquals(mockProject, actualConvertedIProject);
	}

	@Test
	public void testConvertToIProjectsWIthIOCSProjectArrayReturnsAZeroLengthListWithIProjects() throws Exception {
		IDSProject[] projectArray = null;

		List<IProject> actualConvertedToIProjects = DSProjectUtil.convertToIProjects(projectArray);

		Assert.assertEquals(0, actualConvertedToIProjects.size());
	}

	@Test
	public void testConvertToIProjectsWIthIOCSProjectListReturnsAZeroLengthListWithIProjects() throws Exception {
		List<IDSProject> projectList = null;

		List<IProject> actualConvertedToIProjects = DSProjectUtil.convertToIProjects(projectList);

		Assert.assertEquals(0, actualConvertedToIProjects.size());
	}

	@Test
	public void testConvertToIProjectsWithIOCSProjectArrayContainingOneProjectReturnsListWithOneIProject() throws Exception {
		IDSProject mockOcsProject = Mockito.mock(IDSProject.class);
		IDSProject[] projectArray = {mockOcsProject};

		IWorkspaceRoot mockWorkspaceRoot = Mockito.mock(IWorkspaceRoot.class);
		Mockito.when(mockWorkspaceRoot.getProject(TEST_PROJECT_NAME)).thenReturn(mockProject);

		List<IProject> actualConvertedToIProjects = DSProjectUtil.convertToIProjects(mockWorkspaceRoot, projectArray);

		Assert.assertEquals(1, actualConvertedToIProjects.size());
	}

	@Test
	public void testConvertToIProjectsWithIOCSProjectListContainingOneProjectReturnsListWithOneIProject() throws Exception {
		List<IDSProject> projectList = new ArrayList<IDSProject>();
		IDSProject mockOcsProject = Mockito.mock(IDSProject.class);
		projectList.add(mockOcsProject);

		IWorkspaceRoot mockWorkspaceRoot = Mockito.mock(IWorkspaceRoot.class);
		Mockito.when(mockWorkspaceRoot.getProject(TEST_PROJECT_NAME)).thenReturn(mockProject);

		List<IProject> actualConvertedToIProjects = DSProjectUtil.convertToIProjects(mockWorkspaceRoot, projectList);

		Assert.assertEquals(1, actualConvertedToIProjects.size());
	}

	@Test
	public void testRemoveProjectByName() {
		List<IDSProject> projectList = new ArrayList<IDSProject>();
		DSProject project1 = new DSProject("project1", "somePathProject1");
		DSProject project2 = new DSProject("project2", "somePathProject2");
		DSProject project3 = new DSProject("project3", "somePathProject3");

		projectList.add(project1);
		projectList.add(project2);
		projectList.add(project3);

		Assert.assertEquals(3, projectList.size());

		projectList = DSProjectUtil.removeProjectByName("project2", projectList);
		Assert.assertEquals(2, projectList.size());
		Assert.assertTrue(projectList.contains(project1));
		Assert.assertFalse(projectList.contains(project2));
		Assert.assertTrue(projectList.contains(project3));

		projectList = DSProjectUtil.removeProjectByName("projectNotInTheList", projectList);
		Assert.assertEquals(2, projectList.size());
		Assert.assertTrue(projectList.contains(project1));
		Assert.assertFalse(projectList.contains(project2));
		Assert.assertTrue(projectList.contains(project3));

		projectList = DSProjectUtil.removeProjectByName("project3", projectList);
		Assert.assertEquals(1, projectList.size());
		Assert.assertTrue(projectList.contains(project1));
		Assert.assertFalse(projectList.contains(project2));
		Assert.assertFalse(projectList.contains(project3));

		projectList = DSProjectUtil.removeProjectByName("project1", projectList);
		Assert.assertEquals(0, projectList.size());

		// No exception should be thrown
		DSProjectUtil.removeProjectByName("projectNotInTheList", projectList);
	}
}
