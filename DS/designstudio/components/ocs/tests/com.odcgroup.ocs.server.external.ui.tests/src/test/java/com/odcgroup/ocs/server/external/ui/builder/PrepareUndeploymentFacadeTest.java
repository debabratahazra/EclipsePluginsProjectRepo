package com.odcgroup.ocs.server.external.ui.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.DSProjectUtil;

public class PrepareUndeploymentFacadeTest  {


	@Test
	public void testListUndeployedProjectsReturnsZeroProjectsWhenAllProjectsAreDeployed() throws Exception {
		List<IDSProject> ocsProjectsList = new ArrayList<IDSProject>();

		IDSProject ocsProject = new DSProject("testProjectName","testProjectLocation");
		ocsProjectsList.add(ocsProject);

		IDSServer server = Mockito.mock(IDSServer.class);
		List<IDSProject> ocsProjects = server.getDsProjects();
		Mockito.when(ocsProjects).thenReturn(ocsProjectsList);
		List<IProject> currentlyDeployed = DSProjectUtil.convertToIProjects(ocsProjects);
		List<IProject> newList = DSProjectUtil.convertToIProjects(ocsProjectsList);

		List<IProject> undeployedProjectsList = PrepareUndeploymentFacade.listUndeployedProjects(currentlyDeployed, newList);

		Assert.assertEquals(0, undeployedProjectsList.size());
	}

	@Test
	public void testListUndeployedProjectsReturnsOneProjectWhenOneProjectHasBeenUndeployed() throws Exception {


		List<IDSProject> oldDeployedProjectsList = new ArrayList<IDSProject>();
		IDSProject ocsProject1 = new DSProject("testProjectName1","testProjectLocation1");
		IDSProject ocsProject2 = new DSProject("testProjectName2","testProjectLocation2");
		oldDeployedProjectsList.add(ocsProject1);
		oldDeployedProjectsList.add(ocsProject2);

		List<IDSProject> newDeployedProjectsList = new ArrayList<IDSProject>();
		newDeployedProjectsList.add(ocsProject1);

		IDSServer server = Mockito.mock(IDSServer.class);
		Mockito.when(server.getDsProjects()).thenReturn(oldDeployedProjectsList);
		List<IProject> currentlyDeployed = DSProjectUtil.convertToIProjects(server.getDsProjects());
		List<IProject> newList = DSProjectUtil.convertToIProjects(newDeployedProjectsList);

		List<IProject> undeployedProjectsList = PrepareUndeploymentFacade.listUndeployedProjects(currentlyDeployed, newList);

		Assert.assertEquals(1, undeployedProjectsList.size());
		Assert.assertEquals(undeployedProjectsList.get(0).getName(), "testProjectName2");
	}
}
